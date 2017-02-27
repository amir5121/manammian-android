package com.amir.manammiam.infrastructure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.amir.manammiam.fragments.InboxFragment;
import com.amir.manammiam.fragments.ProfileFragment;
import com.amir.manammiam.fragments.ServicesFragment;
import com.amir.manammiam.fragments.TripFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_NUM = 4;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TripFragment.newInstance(3, "Page # 4");
            case 1: // Fragment # 0 - This will show FirstFragment
                return ServicesFragment.newInstance(0, "Page # 1");
            case 2:
                return InboxFragment.newInstance(1, "Page # 2");
            case 3:
                return ProfileFragment.newInstance(2, "Page # 3");
            default:
                Log.e(getClass().getSimpleName(), "case Default is happening... MUST NOT");
                throw new RuntimeException("asking for none existing fragment in " + getClass().getSimpleName());
//                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES_NUM;
    }
}
