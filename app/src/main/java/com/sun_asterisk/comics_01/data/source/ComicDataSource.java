package com.sun_asterisk.comics_01.data.source;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import java.util.List;

public interface ComicDataSource {

    interface LocalDataSource {
        void saveComicCurrent(Comic comic);

        List<Comic> getAllComicCurrent();

        void removeComicCurrent(int idComic);
    }

    interface RemoteDataSource {
        void getComics(OnFetchDataJsonListener<Comic> listener);

        void getComicByName(OnFetchDataJsonListener<Comic> listener, String comicName);

        void getComicByCategory(int idCategory, OnFetchDataJsonListener<Comic> listener);

        void getComicFollow(OnFetchDataJsonListener<Comic> mListener);

        void deleteComicFollow(int idComic, OnListener onListener);
    }
}
