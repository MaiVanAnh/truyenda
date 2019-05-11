package com.sun_asterisk.comics_01.screen.search;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.data.repository.ComicRepository;
import com.sun_asterisk.comics_01.data.repository.SearchHistoryRepository;
import com.sun_asterisk.comics_01.data.source.remote.OnFetchDataJsonListener;
import java.util.List;
import java.util.Set;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private ComicRepository mComicRepository;
    private SearchHistoryRepository mSearchHistoryRepository;

    SearchPresenter(ComicRepository comicRepository,
            SearchHistoryRepository searchHistoryRepository) {
        mComicRepository = comicRepository;
        mSearchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public void searchComic(String comicName) {
        mComicRepository.getComicByName(comicName, new OnFetchDataJsonListener<Comic>() {
            @Override
            public void onSuccess(List<Comic> comics) {
                if (!comics.isEmpty()) {
                    mView.onSearchComicSuccess(comics);
                } else {
                    mView.onNoDataAvailable();
                }
            }

            @Override
            public void onError(Exception exception) {
                mView.onSearchComicError(exception);
            }
        });
    }

    @Override
    public Set<String> getSearchHistoryLocal() {
        return mSearchHistoryRepository.getSearchHistory();
    }

    @Override
    public void saveSearchHistoryLocal(Set<String> searchHistory) {
        mSearchHistoryRepository.saveSearchHistory(searchHistory);
    }

    @Override
    public void setView(SearchContract.View view) {
        mView = view;
    }
}
