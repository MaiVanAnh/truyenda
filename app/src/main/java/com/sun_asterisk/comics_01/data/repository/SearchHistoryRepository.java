package com.sun_asterisk.comics_01.data.repository;

import com.sun_asterisk.comics_01.data.source.local.SearchHistoryDataSource;
import java.util.Set;

public class SearchHistoryRepository {
    private static SearchHistoryRepository sInstance;
    private SearchHistoryDataSource mSearchHistoryDataSource;

    private SearchHistoryRepository(SearchHistoryDataSource searchHistoryDataSource) {
        mSearchHistoryDataSource = searchHistoryDataSource;
    }

    public static SearchHistoryRepository getInstance(
            SearchHistoryDataSource searchHistoryDataSource) {
        if (sInstance == null) sInstance = new SearchHistoryRepository(searchHistoryDataSource);
        return sInstance;
    }

    public Set<String> getSearchHistory() {
        return mSearchHistoryDataSource.getSearchHistory();
    }

    public void saveSearchHistory(Set<String> searchHistory) {
        mSearchHistoryDataSource.saveSearchHistory(searchHistory);
    }
}
