package com.sun_asterisk.comics_01.screen.comic.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.utils.OnItemRecyclerViewClickListener;
import com.sun_asterisk.comics_01.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterHolder> {
    private static final String SEPARATE = ". ";
    private List<Chapter> mChapters;
    private OnItemRecyclerViewClickListener<Chapter> mListener;
    private int mIdChapterCurrent;

    public ChapterAdapter() {
        mChapters = new ArrayList<>();
    }

    public void setData(List<Chapter> chapters) {
        mChapters.clear();
        mChapters = chapters;
        notifyItemRangeInserted(0, mChapters.size());
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Chapter> listener) {
        mListener = listener;
    }

    public void setIdChapterCurrent(int idChapterCurrent) {
        mIdChapterCurrent = idChapterCurrent;
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chapter, viewGroup, false);
        return new ChapterHolder(view, mChapters, mListener, mIdChapterCurrent);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder chapterHolder, int position) {
        chapterHolder.bind(mChapters.get(position));
    }

    @Override
    public int getItemCount() {
        return mChapters != null ? mChapters.size() : 0;
    }

    static class ChapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private List<Chapter> mChapters;
        private OnItemRecyclerViewClickListener<Chapter> mListener;
        private TextView mTvNameChapter;
        private TextView mTvDateCreated;
        private TextView mTvView;
        private int mIdChapterCurrent;

        ChapterHolder(@NonNull View itemView, List<Chapter> chapters,
                OnItemRecyclerViewClickListener<Chapter> listener, int idChapterCurrent) {
            super(itemView);
            mChapters = chapters;
            mListener = listener;
            mTvNameChapter = itemView.findViewById(R.id.tvChapterName);
            mTvDateCreated = itemView.findViewById(R.id.tvDateCreated);
            mTvView = itemView.findViewById(R.id.tvView);
            mIdChapterCurrent = idChapterCurrent;
            itemView.setOnClickListener(this);
        }

        void bind(Chapter chapter) {
            if (mIdChapterCurrent == chapter.getId())
                mTvNameChapter.setTextColor(Color.RED);
            mTvNameChapter.setText(chapter.getSerial() + SEPARATE + chapter.getName());
            mTvDateCreated.setText(StringUtils.formatDate(chapter.getDateCreated()));
            mTvView.setText(Long.toString(chapter.getView()));
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(mChapters.get(getAdapterPosition()));
            }
        }
    }
}

