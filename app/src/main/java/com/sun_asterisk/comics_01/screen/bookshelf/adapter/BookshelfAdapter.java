package com.sun_asterisk.comics_01.screen.bookshelf.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.sun_asterisk.comics_01.utils.TabBookShelfNavigation;
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.follow.FollowComicFragment;
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory.ReadHistoryFragment;
import java.util.List;

public class BookshelfAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public BookshelfAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int index) {
        return mFragments.get(index);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TabBookShelfNavigation.HISTORY:
                return TabBookShelfNavigation.HISTORY_NAME;
            case TabBookShelfNavigation.FOLLOW:
                return TabBookShelfNavigation.FOLLOW_NAME;
            default:
                return "";
        }
    }
}
