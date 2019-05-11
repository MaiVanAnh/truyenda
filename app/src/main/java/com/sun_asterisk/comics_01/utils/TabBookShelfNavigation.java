package com.sun_asterisk.comics_01.utils;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

@IntDef({ TabBookShelfNavigation.HISTORY, TabBookShelfNavigation.FOLLOW })
@StringDef({ TabBookShelfNavigation.HISTORY_NAME, TabBookShelfNavigation.FOLLOW_NAME })
public @interface TabBookShelfNavigation {
    int HISTORY = 0;
    int FOLLOW = 1;

    String HISTORY_NAME = "History";
    String FOLLOW_NAME = "Follow";
}
