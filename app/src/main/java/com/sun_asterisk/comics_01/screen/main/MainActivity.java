package com.sun_asterisk.comics_01.screen.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Cache;
import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.UserRemoteDataSource;
import com.sun_asterisk.comics_01.screen.bookshelf.BookshelfFragment;
import com.sun_asterisk.comics_01.screen.category.CategoryFragment;
import com.sun_asterisk.comics_01.screen.home.HomeFragment;
import com.sun_asterisk.comics_01.screen.main.adapter.TabFragmentPagerAdapter;
import com.sun_asterisk.comics_01.screen.profile.ProfileFragment;
import com.sun_asterisk.comics_01.utils.Constant;
import com.sun_asterisk.comics_01.utils.StringUtils;
import com.sun_asterisk.comics_01.utils.TabNavigation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, MainContract.View {
    private final int LIMIT_PAGE = 5;
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private MenuItem mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        init();
    }

    private void addEvents() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void addControls() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mViewPager = findViewById(R.id.viewPagerMain);
    }

    private void init() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(CategoryFragment.newInstance());
        fragments.add(BookshelfFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());
        TabFragmentPagerAdapter adapter =
                new TabFragmentPagerAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(LIMIT_PAGE);
        mViewPager.addOnPageChangeListener(this);

        SharedPreferences prefs = getSharedPreferences(Constant.SHARED, MODE_PRIVATE);
        String token = prefs.getString(Constant.TOKEN, null);
        if (token != null) {
            MainPresenter presenter = new MainPresenter(
                    UserRepository.getInstance(null, UserRemoteDataSource.getInstance()));
            presenter.setView(this);
            presenter.getAccount(token);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigationHome:
                mViewPager.setCurrentItem(TabNavigation.HOME);
                return true;
            case R.id.navigationCategory:
                mViewPager.setCurrentItem(TabNavigation.CATEGORY);
                return true;
            case R.id.navigationBookshelf:
                mViewPager.setCurrentItem(TabNavigation.BOOKSHELF);
                return true;
            case R.id.navigationProfile:
                mViewPager.setCurrentItem(TabNavigation.PROFILE);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int index, float v, int i1) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mPrevMenuItem != null) {
            mPrevMenuItem.setChecked(false);
        } else {
            mBottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
        mPrevMenuItem = mBottomNavigationView.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int position) {
    }

    @Override
    public void onGetAccountSuccess(User user) {
        Calendar expired = StringUtils.parseStringToCalendar(user.getExpired());
        boolean isValid;
        Calendar current = Calendar.getInstance();
        if (expired.get(Calendar.YEAR) > current.get(Calendar.YEAR)) {
            isValid = true;
        } else if (expired.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
            if (expired.get(Calendar.MONTH) > current.get(Calendar.MONTH)) {
                isValid = true;
            } else if (expired.get(Calendar.MONTH) == current.get(Calendar.MONTH)) {
                if (expired.get(Calendar.DAY_OF_MONTH) > current.get(Calendar.DAY_OF_MONTH)) {
                    isValid = true;
                } else if (expired.get(Calendar.DAY_OF_MONTH) == current.get(
                        Calendar.DAY_OF_MONTH)) {
                    if (expired.get(Calendar.HOUR_OF_DAY) > current.get(Calendar.HOUR_OF_DAY)) {

                        isValid = true;
                    } else if (expired.get(Calendar.HOUR_OF_DAY) == current.get(
                            Calendar.HOUR_OF_DAY)) {
                        isValid = expired.get(Calendar.MINUTE) > current.get(Calendar.MINUTE);
                    } else {
                        isValid = false;
                    }
                } else {
                    isValid = false;
                }
            } else {
                isValid = false;
            }
        } else {
            isValid = false;
        }

        if (isValid) {
            Cache cache = Cache.getInstance();
            cache.cacheUserName(user.getUserName());
            cache.cachedName(user.getName());
            cache.cachedGender(user.getGender());
            cache.cachedBirthDay(user.getDate());
            cache.cachedEmail(user.getEmail());
            cache.cachedToken(user.getToken());
            cache.isLogin = true;
            // goi fragment update ui
            ProfileFragment.newInstance().setViewLogin();
        }
    }

    @Override
    public void onGetAccountFail(Exception exception) {
        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
