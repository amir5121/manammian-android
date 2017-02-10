package com.amir.manammiam.infrastructure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.amir.manammiam.fragments.ServicesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_NUM = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return ServicesFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return ServicesFragment.newInstance(1, "Page # 2");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES_NUM;
    }
}
