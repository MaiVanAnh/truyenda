package com.sun_asterisk.comics_01.screen.search;

import com.sun_asterisk.comics_01.data.model.Comic;
import com.sun_asterisk.comics_01.screen.BasePresenter;
import java.util.List;
import java.util.Set;

public interface SearchContract {
    interface View {
        void onSearchComicSuccess(List<Comic> comics);

        void onSearchComicError(Exception exception);

        void onNoDataAvailable();
    }

    interface Presenter extends BasePresenter<View> {
        void searchComic(String comicName);

        Set<String> getSearchHistoryLocal();

        void saveSearchHistoryLocal(Set<String> searchHistory);
    }
}
