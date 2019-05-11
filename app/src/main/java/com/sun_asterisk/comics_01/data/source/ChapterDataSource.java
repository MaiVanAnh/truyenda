package com.sun_asterisk.comics_01.data.source;

import com.sun_asterisk.comics_01.data.model.BookMark;
import com.sun_asterisk.comics_01.data.model.Chapter;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.comics_01.data.source.remote.OnListener;
import com.sun_asterisk.comics_01.data.source.remote.OnObjectListener;

public interface ChapterDataSource {
    interface RemoteDataSource {
        void getChapters(int idComic, OnFetchDataJsonListener<Chapter> listener);

        void getContentChapter(int idChapter, OnFetchDataJsonListener<String> listener);

        void checkFollow(int idComic, OnObjectListener<BookMark> onListener);

        void bookmark(int idChapter, int idComic);

        void follow(int idComic, OnListener onListener);

        void unFollow(int idComic, OnListener onListener);
    }
}
