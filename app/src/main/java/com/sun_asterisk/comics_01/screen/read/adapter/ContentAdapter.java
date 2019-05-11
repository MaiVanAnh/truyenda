package com.sun_asterisk.comics_01.screen.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.utils.ImageUtils;
import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private List<String> mLinks;

    public ContentAdapter() {
        mLinks = new ArrayList<>();
    }

    public void setData(List<String> links) {
        if (links != null) {
            mLinks.clear();
            mLinks.addAll(links);
            notifyItemRangeInserted(0, mLinks.size());
        }
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_content, viewGroup, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder contentViewHolder, int position) {
        contentViewHolder.bind(mLinks.get(position));
    }

    @Override
    public int getItemCount() {
        return mLinks != null ? mLinks.size() : 0;
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgContent;
        private ProgressBar mProgressBarContent;

        ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgContent = itemView.findViewById(R.id.imgContent);
            mProgressBarContent = itemView.findViewById(R.id.progressBarContent);
        }

        void bind(String link) {
            if (mImgContent != null) {
                ImageUtils.bindImage(itemView.getContext(), link, mProgressBarContent, mImgContent);
            }
        }
    }
}
