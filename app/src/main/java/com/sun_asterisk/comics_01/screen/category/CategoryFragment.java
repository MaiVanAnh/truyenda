package com.sun_asterisk.comics_01.screen.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.screen.category.adapter.CategoryAdapter;
import com.sun_asterisk.comics_01.screen.category.subfragment.SubCategoryFragment;
import com.sun_asterisk.comics_01.utils.CategoryNavigation;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<SubCategoryFragment> mSubCategoryFragments;
    private CategoryAdapter mAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        addFragment();
        initView(view);
        return view;
    }

    private void addFragment() {
        mSubCategoryFragments = new ArrayList<>();
        mSubCategoryFragments.add(SubCategoryFragment.
                newInstance(CategoryNavigation.ACTION_ID));
        mSubCategoryFragments.add(SubCategoryFragment.
                newInstance(CategoryNavigation.ADULT_ID));
        mSubCategoryFragments.add(SubCategoryFragment.
                newInstance(CategoryNavigation.ADVENTURE_ID));
        mSubCategoryFragments.add(SubCategoryFragment.
                newInstance(CategoryNavigation.LOVE_ID));
        mSubCategoryFragments.add(SubCategoryFragment.
                newInstance(CategoryNavigation.AFFAIR_ID));
    }

    private void initView(View view) {
        mAdapter = new CategoryAdapter(getFragmentManager(), mSubCategoryFragments);
        mTabLayout = view.findViewById(R.id.tabLayoutCategory);
        mViewPager = view.findViewById(R.id.viewPagerCategory);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
