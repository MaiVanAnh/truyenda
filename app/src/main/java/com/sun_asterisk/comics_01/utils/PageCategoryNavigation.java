package com.sun_asterisk.comics_01.utils;

import android.support.annotation.IntDef;

@IntDef({
        PageCategoryNavigation.ACTION_PAGE, PageCategoryNavigation.ADULT_PAGE,
        PageCategoryNavigation.ADVENTURE_PAGE, PageCategoryNavigation.LOVE_PAGE,
        PageCategoryNavigation.AFFAIR_PAGE
})

public @interface PageCategoryNavigation {
    int ACTION_PAGE = 0;
    int ADULT_PAGE = 1;
    int ADVENTURE_PAGE = 2;
    int LOVE_PAGE = 3;
    int AFFAIR_PAGE = 4;
}
