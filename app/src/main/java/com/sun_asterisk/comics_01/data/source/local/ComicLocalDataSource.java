package com.sun_asterisk.comics_01.data.source.local;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.source.ComicDataSource;
import com.sun_asterisk.comics_01.data.source.local.sqlite.ComicLocalHandler;
import java.util.List;

public class ComicLocalDataSource implements ComicDataSource.LocalDataSource {
    private static ComicLocalDataSource sInstance;
    private ComicLocalHandler mComicLocalHandler;

    private ComicLocalDataSource(ComicLocalHandler comicLocalHandler) {
        mComicLocalHandler = comicLocalHandler;
    }

    public static ComicLocalDataSource getsInstance(ComicLocalHandler comicLocalHandler) {
        if (sInstance == null) {
            sInstance = new ComicLocalDataSource(comicLocalHandler);
        }
        return sInstance;
    }

    @Override
    public void saveComicCurrent(Comic comic) {
        mComicLocalHandler.addComic(comic);
    }

    @Override
    public List<Comic> getAllComicCurrent() {
        return mComicLocalHandler.getAllComic();
    }

    @Override
    public void removeComicCurrent(int idComic) {
        mComicLocalHandler.removeComic(idComic);
    }
}
