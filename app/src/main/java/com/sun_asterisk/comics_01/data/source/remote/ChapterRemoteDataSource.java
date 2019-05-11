package com.sun_asterisk.comics_01.data.source.remote;

import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.source.ChapterDataSource;
import com.sun_asterisk.comics_01.data.source.remote.fetchjson.GetChapterJson;
import com.sun_asterisk.comics_01.data.source.remote.fetchjson.GetContentChapterJson;
import com.sun_asterisk.comics_01.data.source.remote.request.GetRequest;

public class ChapterRemoteDataSource implements ChapterDataSource.RemoteDataSource {

    private static ChapterRemoteDataSource sInstance;

    private ChapterRemoteDataSource() {
    }

    public static ChapterRemoteDataSource getInstance() {
        if (sInstance == null) return sInstance = new ChapterRemoteDataSource();
        return sInstance;
    }

    @Override
    public void getChapters(int idComic, OnFetchDataJsonListener<Chapter> listener) {
        GetChapterJson getChapterJson = new GetChapterJson(listener);
        getChapterJson.getChapter(idComic);
    }

    @Override
    public void getContentChapter(int idChapter, OnFetchDataJsonListener<String> listener) {
        GetContentChapterJson getContentChapterJson = new GetContentChapterJson(listener);
        getContentChapterJson.getContentChapter(idChapter);

    }

    @Override
    public void bookmark(int idChapter, int idComic) {
        new GetRequest().bookmark(idChapter, idComic);
    }

    @Override
    public void follow(int idComic, OnListener onListener) {
        new GetRequest(onListener).follow(idComic);
    }

    @Override
    public void unFollow(int idComic, OnListener onListener) {
        new GetRequest(onListener).unFollow(idComic);
    }

    @Override
    public void checkFollow(int idComic, OnObjectListener onListener) {
        new GetRequest(onListener).checkFollow(idComic);
    }
}
