package com.sun_asterisk.comics_01.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.utils.ImageUtils;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {
    private Context mContext;
    private List<Comic> mComics = new ArrayList<>();
    private OnItemRecyclerViewClickListener<Comic> mItemRecyclerViewClickListener;
    private int mSize;

    public ComicAdapter(@NonNull Context context) {
        mContext = context;
    }

    public void setData(List<Comic> comics) {
        mComics.clear();
        mComics.addAll(comics);
        mSize = comics.size();
        notifyDataSetChanged();
    }

    public void clear() {
        mComics.clear();
        notifyItemRangeRemoved(0, mSize);
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Comic> itemRecyclerViewClickListener) {
        mItemRecyclerViewClickListener = itemRecyclerViewClickListener;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comic, viewGroup, false);
        return new ComicViewHolder(view, mComics, mItemRecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder comicViewHolder, int position) {
        comicViewHolder.bindComic(mComics.get(position));
    }

    @Override
    public int getItemCount() {
        return mComics != null ? mComics.size() : 0;
    }

    static class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private List<Comic> mComics;
        private TextView mTvName;
        private ImageView mImgThumbnail;
        private ProgressBar mProgressBar;
        private OnItemRecyclerViewClickListener<Comic> mListener;

        ComicViewHolder(@NonNull View itemView, List<Comic> comics,
                OnItemRecyclerViewClickListener<Comic> listener) {
            super(itemView);
            mComics = comics;
            mListener = listener;
            mTvName = itemView.findViewById(R.id.tvName);
            mImgThumbnail = itemView.findViewById(R.id.imgThumbComicDetail);
            mProgressBar = itemView.findViewById(R.id.progressBarItemComic);
            itemView.setOnClickListener(this);
        }

        void bindComic(Comic comic) {
            mTvName.setText(comic.getName());
            ImageUtils.bindImage(itemView.getContext(), comic.getThumbnail(), mProgressBar,
                    mImgThumbnail);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) mListener.onItemClickListener(mComics.get(getAdapterPosition()));
        }
    }
}
