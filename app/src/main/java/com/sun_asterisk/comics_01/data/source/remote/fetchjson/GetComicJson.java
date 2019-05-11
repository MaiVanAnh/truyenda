package com.sun_asterisk.comics_01.data.source.remote.fetchjson;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.utils.Constant;

public class GetComicJson {
    private OnFetchDataJsonListener<Comic> mListener;

    public GetComicJson(OnFetchDataJsonListener<Comic> listener) {
        mListener = listener;
    }

    public void getComics() {
        String url = Constant.BASE_URL + Constant.STORY + Constant.STORE_ALL;
        new GetComicAsyncTask(mListener).execute(url);
    }

    public void getComicByName(String name) {
        String url = Constant.BASE_URL + Constant.STORY + Constant.SEARCH + name + Constant.PAGE;
        new GetComicSearchAsyncTask(mListener).execute(url);
    }

    public void getComicByCategory(int idCategory) {
        String url = Constant.BASE_URL + Constant.STORY + Constant.CATEGORY + idCategory;
        new GetComicAsyncTask(mListener).execute(url);
    }

    public void getComicFollow() {
        String url = Constant.BASE_URL + Constant.BOOKMARKS + Constant.MY;
        new GetComicFollowAsync(mListener).execute(url);
    }
}
