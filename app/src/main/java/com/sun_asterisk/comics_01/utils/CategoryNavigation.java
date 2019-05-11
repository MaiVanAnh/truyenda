package com.sun_asterisk.comics_01.utils;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

@IntDef({
        CategoryNavigation.ACTION_ID, CategoryNavigation.ADULT_ID, CategoryNavigation.ADVENTURE_ID,
        CategoryNavigation.LOVE_ID, CategoryNavigation.AFFAIR_ID
})

@StringDef({
        CategoryNavigation.ACTION_NAME, CategoryNavigation.ADULT_NAME,
        CategoryNavigation.ADVENTURE_NAME, CategoryNavigation.LOVE_NAME,
        CategoryNavigation.AFFAIR_NAME
})

public @interface CategoryNavigation {

    int ACTION_ID = 1;
    int ADULT_ID = 2;
    int ADVENTURE_ID = 3;
    int LOVE_ID = 6;
    int AFFAIR_ID = 5;

    String ACTION_NAME = "Action";
    String ADULT_NAME = "18+";
    String ADVENTURE_NAME = "Adventure";
    String LOVE_NAME = "Drama";
    String AFFAIR_NAME = "Affair";
}
