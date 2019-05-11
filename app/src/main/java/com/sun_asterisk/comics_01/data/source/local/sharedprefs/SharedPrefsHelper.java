package com.sun_asterisk.comics_01.data.source.local.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class SharedPrefsHelper {
    private static SharedPrefsHelper sInstance;
    private static final String PREFS_NAME = "HISTORY_PREFERENCE";
    private static final String PREFS_SEARCH_HISTORY = "PREFS_SEARCH_HISTORY";
    private static final int MODE = 0;
    private SharedPreferences mSharedPreferences;

    private SharedPrefsHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE);
    }

    public static SharedPrefsHelper getInstance(Context context) {
        if (sInstance == null) sInstance = new SharedPrefsHelper(context);
        return sInstance;
    }

    public Set<String> getSearchHistory() {
        return mSharedPreferences.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>());
    }

    public void saveSearchHistory(Set<String> historySet) {
        mSharedPreferences.edit().clear().putStringSet(PREFS_SEARCH_HISTORY, historySet).apply();
    }
}
