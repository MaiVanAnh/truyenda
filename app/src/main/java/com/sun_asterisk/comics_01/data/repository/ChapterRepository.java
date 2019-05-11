package com.sun_asterisk.comics_01.data.repository;

import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.source.ChapterDataSource;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;

public class ChapterRepository {
    private static ChapterRepository sInstance;
    private ChapterDataSource.RemoteDataSource mChapterRemoteDataSource;

    private ChapterRepository(ChapterDataSource.RemoteDataSource chapterRemoteDataSource) {
        mChapterRemoteDataSource = chapterRemoteDataSource;
    }

    public static ChapterRepository getInstance(
            ChapterDataSource.RemoteDataSource chapterRemoteDataSource) {
        if (sInstance == null) return sInstance = new ChapterRepository(chapterRemoteDataSource);
        return sInstance;
    }

    public void getChapters(int idComic, OnFetchDataJsonListener<Chapter> listener) {
        mChapterRemoteDataSource.getChapters(idComic, listener);
    }

    public void getContentChapter(int idChapter, OnFetchDataJsonListener<String> listener) {
        mChapterRemoteDataSource.getContentChapter(idChapter, listener);
    }

    public void checkFollow(int idComic, OnObjectListener onListener) {
        mChapterRemoteDataSource.checkFollow(idComic, onListener);
    }

    public void bookmark(int idChapter, int idComic) {
        mChapterRemoteDataSource.bookmark(idChapter, idComic);
    }

    public void follow(int idComic, OnListener onListener) {
        mChapterRemoteDataSource.follow(idComic, onListener);
    }

    public void unFollow(int idComic, OnListener onListener) {
        mChapterRemoteDataSource.unFollow(idComic, onListener);
    }
}
