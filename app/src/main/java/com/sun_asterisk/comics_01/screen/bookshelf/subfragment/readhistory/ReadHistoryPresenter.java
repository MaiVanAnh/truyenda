package com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import java.util.List;

public class ReadHistoryPresenter implements ReadHistoryContract.Presenter {

    private ComicRepository mComicRepository;

    public ReadHistoryPresenter(ComicRepository comicRepository) {
        mComicRepository = comicRepository;
    }

    @Override
    public List<Comic> getReadHistory() {
        return mComicRepository.getAllComicCurrent();
    }

    @Override
    public void deleteReadHistory(int idComic) {
        mComicRepository.removeComicCurrent(idComic);
    }
}
