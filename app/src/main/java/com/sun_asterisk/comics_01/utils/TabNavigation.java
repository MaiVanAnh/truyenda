package com.sun_asterisk.comics_01.utils;

import android.support.annotation.IntDef;

@IntDef({
        TabNavigation.HOME, TabNavigation.CATEGORY, TabNavigation.BOOKSHELF, TabNavigation.PROFILE
})
public @interface TabNavigation {
    int HOME = 0;
    int CATEGORY = 1;
    int BOOKSHELF = 2;
    int PROFILE = 3;
}
