package com.sun_asterisk.comics_01.data.source.local;

import com.sun_asterisk.comics_01.data.source.local.sharedprefs.SharedPrefsHelper;
import java.util.Set;

public class SearchHistoryDataSource {
    private static SearchHistoryDataSource mInstance;
    private SharedPrefsHelper mSharedPrefsHelper;

    private SearchHistoryDataSource(SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public static SearchHistoryDataSource getInstance(SharedPrefsHelper sharedPrefsHelper) {
        if (mInstance == null) mInstance = new SearchHistoryDataSource(sharedPrefsHelper);
        return mInstance;
    }

    public Set<String> getSearchHistory() {
        return mSharedPrefsHelper.getSearchHistory();
    }

    public void saveSearchHistory(Set<String> historySet) {
        mSharedPrefsHelper.saveSearchHistory(historySet);
    }
}
