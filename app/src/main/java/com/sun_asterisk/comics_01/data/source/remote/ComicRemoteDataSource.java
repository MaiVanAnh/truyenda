package com.sun_asterisk.comics_01.data.source.remote;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.ComicDataSource;
import com.sun_asterisk.comics_01.data.source.remote.fetchjson.GetComicJson;
import com.sun_asterisk.comics_01.data.source.remote.request.GetRequest;

public class ComicRemoteDataSource implements ComicDataSource.RemoteDataSource {

    private static ComicRemoteDataSource sInstance;

    public static ComicRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new ComicRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getComics(OnFetchDataJsonListener<Comic> listener) {
        GetComicJson getComicJson = new GetComicJson(listener);
        getComicJson.getComics();
    }

    @Override
    public void getComicByName(OnFetchDataJsonListener<Comic> listener, String comicName) {
        new GetComicJson(listener).getComicByName(comicName);
    }

    @Override
    public void getComicByCategory(int idCategory, OnFetchDataJsonListener<Comic> listener) {
        new GetComicJson(listener).getComicByCategory(idCategory);
    }

    @Override
    public void getComicFollow(OnFetchDataJsonListener<Comic> mListener) {
        new GetComicJson(mListener).getComicFollow();
    }

    @Override
    public void deleteComicFollow(int idComic, OnListener onListener) {
        new GetRequest(onListener).unFollow(idComic);
    }
}
