package com.amir.manammiam.infrastructure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.amir.manammiam.fragments.InboxFragment;
import com.amir.manammiam.fragments.ProfileFragment;
import com.amir.manammiam.fragments.ServicesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_NUM = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return ServicesFragment.newInstance(0, "Page # 1");
            case 1:
                return InboxFragment.newInstance(1, "Page # 2");
            case 2:
                Log.e(getClass().getSimpleName(), "asking for profile");
                return ProfileFragment.newInstance(2, "Page # 3");
            default:
                Log.e(getClass().getSimpleName(), "Default is happening");
                throw new RuntimeException("asking for none existing fragment in " + getClass().getSimpleName());
//                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES_NUM;
    }
}
