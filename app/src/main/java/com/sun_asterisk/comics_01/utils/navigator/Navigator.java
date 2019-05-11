package com.sun_asterisk.comics_01.utils.navigator;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class Navigator {

    @NonNull
    private FragmentManager mFragmentManager;

    public Navigator(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void showFragment(Fragment fragment) {
        if (fragment != null) {
            mFragmentManager.beginTransaction().show(fragment).commit();
        }
    }

    public void changeFragment(Fragment hideFragment, Fragment showFragment) {
        if (hideFragment != null && showFragment != null) {
            mFragmentManager.beginTransaction().hide(hideFragment).show(showFragment).commit();
        }
    }
}
