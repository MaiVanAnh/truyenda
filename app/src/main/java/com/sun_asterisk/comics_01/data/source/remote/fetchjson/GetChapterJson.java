package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.utils.Constant;

public class GetChapterJson {
    private OnFetchDataJsonListener<Chapter> mChapterListener;

    public GetChapterJson(OnFetchDataJsonListener<Chapter> listener) {
        mChapterListener = listener;
    }

    public void getChapter(int idComic) {
        String url = Constant.BASE_URL + Constant.STORY + idComic + Constant.CHAPTERS;
        new GetChapterAsyncTask(mChapterListener).execute(url);
    }
}
