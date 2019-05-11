package com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory.adapter;

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
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory.OnDeleteItemListener;
import com.sun_asterisk.comics_01.utils.ImageUtils;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class ComicBookShelfAdapter
        extends RecyclerView.Adapter<ComicBookShelfAdapter.ReadHistoryViewHolder> {
    private List<Comic> mComics = new ArrayList<>();
    private int mSize;
    private OnItemRecyclerViewClickListener<Comic> mOnItemRecyclerViewClickListener;
    private OnDeleteItemListener mDeleteItemListener;

    public void setComics(List<Comic> comics) {
        mComics.addAll(comics);
        mSize = mComics.size();
        notifyItemRangeInserted(0, mSize);
    }

    public void clear() {
        mComics.clear();
        notifyItemRangeRemoved(0, mSize);
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Comic> onItemRecyclerViewClickListener) {
        mOnItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    public void setDeleteItemListener(OnDeleteItemListener deleteItemListener) {
        mDeleteItemListener = deleteItemListener;
    }

    @NonNull
    @Override
    public ReadHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_read_history, viewGroup, false);
        return new ReadHistoryViewHolder(view, mComics, mOnItemRecyclerViewClickListener,
                mDeleteItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadHistoryViewHolder readHistoryViewHolder,
            int position) {
        readHistoryViewHolder.bind(mComics.get(position));
    }

    @Override
    public int getItemCount() {
        return mComics != null ? mComics.size() : 0;
    }

    public void deleteItemAt(int position) {
        mComics.remove(mComics.get(position));
        notifyItemRemoved(position);
    }

    static class ReadHistoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView mImgThumbnail;
        private TextView mNameComic;
        private TextView mNameChapter;
        private ProgressBar mProgressBarReadHistory;
        private ImageView mImgDelete;
        private List<Comic> mComics;
        private OnItemRecyclerViewClickListener<Comic> mItemRecyclerViewClickListener;
        private OnDeleteItemListener mDeleteItemListener;

        ReadHistoryViewHolder(@NonNull View itemView, List<Comic> comics,
                OnItemRecyclerViewClickListener<Comic> itemRecyclerViewClickListener,
                OnDeleteItemListener deleteItemListener) {
            super(itemView);
            mImgThumbnail = itemView.findViewById(R.id.imgThumbnailItemReadHistory);
            mNameComic = itemView.findViewById(R.id.tvComicName);
            mNameChapter = itemView.findViewById(R.id.tvNameChapter);
            mProgressBarReadHistory = itemView.findViewById(R.id.progressItemBarReadHistory);
            mImgDelete = itemView.findViewById(R.id.imgDelete);
            mComics = comics;
            mItemRecyclerViewClickListener = itemRecyclerViewClickListener;
            mDeleteItemListener = deleteItemListener;
            mImgDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void bind(Comic comic) {
            ImageUtils.bindImage(itemView.getContext(), comic.getThumbnail(),
                    mProgressBarReadHistory, mImgThumbnail);
            mNameComic.setText(comic.getName());
            if (comic.getIdChapterCurrent() != 0) {
                mNameChapter.setText(comic.getNameChapterCurrent());
            } else {
                mNameChapter.setText("Chưa đọc");
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    mDeleteItemListener.deleteItemComic(mComics.get(getAdapterPosition()).getId(),
                            getAdapterPosition());
                    break;

                default:
                    if (mItemRecyclerViewClickListener != null) {
                        mItemRecyclerViewClickListener.onItemClickListener(
                                mComics.get(getAdapterPosition()));
                    }
            }
        }
    }
}
