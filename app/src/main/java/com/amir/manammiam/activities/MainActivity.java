package com.amir.manammiam.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseAuthenticatedActivity;
import com.amir.manammiam.infrastructure.ViewPagerAdapter;
import com.amir.manammiam.infrastructure.customView.bottomBar.BottomBarItem;
import com.amir.manammiam.infrastructure.customView.bottomBar.BottomBarItemClicked;
import com.amir.manammiam.infrastructure.customView.bottomBar.BottomBarManager;
import com.viewpagerindicator.PageIndicator;

public class MainActivity extends BaseAuthenticatedActivity implements BottomBarItemClicked {

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;

    //TODO: https://github.com/rengwuxian/MaterialEditText
    //TODO: adding new passenger (should it be more like a request)
    //TODO: adding new service
    //TODO: list of passengers
    //TODO: list cars in profile fragment
    //TODO: make the user cars editable (should i?)
    //TODO: Mark as read after pressing Yes or No.. remove the post or service after the time was passed
    //TODO: might not be a bad idea to hand errors in the main activity.. post to the bus in fragment and receive it here
    //TODO: should i show the number of people with the same source and destination
    //TODO: should i make the order request cancelable
    //TODO: order trips by their time
    //TODO: order services by their time
    //TODO: order inbox by their read unread status and time
    //TODO: font license

    @Override
    protected void onManamMiamCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
//        setNavDrawer(new MainNavDrawer(this));
        setUpView();

    }

    private void setUpView() {
        viewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        viewPager.setCurrentItem(0);

        PageIndicator mIndicator = (PageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);


        final BottomBarManager manager = new BottomBarManager(this);
        //order matters
        manager.addItem((BottomBarItem) findViewById(R.id.activity_main_trips));
        manager.addItem((BottomBarItem) findViewById(R.id.activity_main_services));
        manager.addItem((BottomBarItem) findViewById(R.id.activity_main_inbox));
        manager.addItem((BottomBarItem) findViewById(R.id.activity_main_profile));
        manager.setItemSelected(R.id.activity_main_trips);


        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                manager.setItemSelectedByPos(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void itemClicked(int id) {
        if (id == R.id.activity_main_services) {
            viewPager.setCurrentItem(1, true);
        } else if (id == R.id.activity_main_inbox) {
            viewPager.setCurrentItem(2, true);
        } else if (id == R.id.activity_main_profile) {
            viewPager.setCurrentItem(3, true);
        } else if (id == R.id.activity_main_trips) {
            viewPager.setCurrentItem(0, true);
        }
    }

}