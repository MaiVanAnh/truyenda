package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.utils.Constant;

/**
 * Created by Mai Van Anh on 10/05/2019.
 * Sun-asterisk
 * majanhqn@gmail.com
 */
public class GetContentChapterJson {
    private OnFetchDataJsonListener<String> mContentChapterListener;

    public GetContentChapterJson(OnFetchDataJsonListener<String> listener) {
        mContentChapterListener = listener;
    }

    public void getContentChapter(int idChapter) {
        String url = Constant.BASE_URL + Constant.CHAPTERS + "/" + idChapter + Constant.READ;
        new GetContentChapterAsyncTask(mContentChapterListener).execute(url);
    }
}
