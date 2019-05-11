package com.sun_asterisk.comics_01.screen.bookshelf;

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
import com.sun_asterisk.comics_01.screen.bookshelf.adapter.BookshelfAdapter;
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.follow.FollowComicFragment;
import com.sun_asterisk.comics_01.screen.bookshelf.subfragment.readhistory.ReadHistoryFragment;
import java.util.ArrayList;
import java.util.List;

public class BookshelfFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private BookshelfAdapter mAdapter;

    public static BookshelfFragment newInstance() {
        return new BookshelfFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutBookshelf);
        ViewPager viewPager = view.findViewById(R.id.viewPagerBookshelf);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ReadHistoryFragment.getInstance());
        fragments.add(FollowComicFragment.getInstance());
        BookshelfAdapter adapter = new BookshelfAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
