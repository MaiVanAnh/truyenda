package com.sun_asterisk.comics_01.screen.category.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.sun_asterisk.comics_01.screen.category.subfragment.SubCategoryFragment;
import com.sun_asterisk.comics_01.utils.CategoryNavigation;
import com.sun_asterisk.comics_01.utils.PageCategoryNavigation;
import java.util.List;

public class CategoryAdapter extends FragmentStatePagerAdapter {

    private List<SubCategoryFragment> mFragments;

    public CategoryAdapter(FragmentManager fm, List<SubCategoryFragment> subCategoryFragments) {
        super(fm);
        mFragments = subCategoryFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PageCategoryNavigation.ACTION_PAGE:
                return CategoryNavigation.ACTION_NAME;
            case PageCategoryNavigation.ADULT_PAGE:
                return CategoryNavigation.ADULT_NAME;
            case PageCategoryNavigation.ADVENTURE_PAGE:
                return CategoryNavigation.ADVENTURE_NAME;
            case PageCategoryNavigation.LOVE_PAGE:
                return CategoryNavigation.LOVE_NAME;
            case PageCategoryNavigation.AFFAIR_PAGE:
                return CategoryNavigation.AFFAIR_NAME;
            default:
                return "";
        }
    }
}
