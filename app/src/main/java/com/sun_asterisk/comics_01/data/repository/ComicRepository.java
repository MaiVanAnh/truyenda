package com.sun_asterisk.comics_01.data.repository;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.ComicDataSource;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import java.util.List;

public class ComicRepository {

    private static ComicRepository sInstance;
    private ComicDataSource.RemoteDataSource mRemoteDataSource;
    private ComicDataSource.LocalDataSource mLocalDataSource;

    private ComicRepository(ComicDataSource.RemoteDataSource remoteDataSource,
            ComicDataSource.LocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static ComicRepository getInstance(ComicDataSource.RemoteDataSource remoteDataSource,
            ComicDataSource.LocalDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new ComicRepository(remoteDataSource, localDataSource);
        }
        return sInstance;
    }

    public void getComics(OnFetchDataJsonListener<Comic> listener) {
        mRemoteDataSource.getComics(listener);
    }

    public void getComicByName(String comicName, OnFetchDataJsonListener<Comic> listener) {
        mRemoteDataSource.getComicByName(listener, comicName);
    }

    public void getComicByCategory(int idCategory, OnFetchDataJsonListener<Comic> listener) {
        mRemoteDataSource.getComicByCategory(idCategory, listener);
    }

    public void saveComicCurrent(Comic comic) {
        mLocalDataSource.saveComicCurrent(comic);
    }

    public List<Comic> getAllComicCurrent() {
        return mLocalDataSource.getAllComicCurrent();
    }

    public void removeComicCurrent(int idComic) {
        mLocalDataSource.removeComicCurrent(idComic);
    }

    public void getComicFollow(OnFetchDataJsonListener<Comic> mListener) {
        mRemoteDataSource.getComicFollow(mListener);
    }

    public void deleteComicFollow(int idComic, OnListener onListener) {
        mRemoteDataSource.deleteComicFollow(idComic, onListener);
    }
}
