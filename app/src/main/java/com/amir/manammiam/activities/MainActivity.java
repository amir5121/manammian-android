package com.amir.manammiam.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseAuthenticatedActivity;
import com.amir.manammiam.dialogFragment.NewRequestDialogFragment;
import com.amir.manammiam.infrastructure.ScrollCallback;
import com.amir.manammiam.infrastructure.ViewPagerAdapter;
import com.amir.manammiam.infrastructure.customView.MViewPager;
import com.amir.manammiam.infrastructure.customView.bottomBar.BottomBarItem;
import com.amir.manammiam.infrastructure.customView.bottomBar.BottomBarItemClicked;
import com.amir.manammiam.infrastructure.customView.bottomBar.BottomBarManager;
import com.amir.manammiam.services.ServiceFailure;
import com.squareup.otto.Subscribe;
import com.viewpagerindicator.PageIndicator;

public class MainActivity extends BaseAuthenticatedActivity implements BottomBarItemClicked, ScrollCallback, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private MViewPager viewPager;
    private FloatingActionButton fab;

    //TODO: list of passengers
    //TODO: make the user cars editable (should i?)
    //TODO: Mark as read after pressing Yes or No.. remove the post or service after the time was passed
    //TODO: might not be a bad idea to hand errors in the main activity.. post to the bus in fragment and receive it here
    //TODO: should i show the number of people with the same source and destination
    //TODO: order trips by their time
    //TODO: order services by their time
    //TODO: order inbox by their read unread status and time
    //TODO: font license
    //TODO: need to get phone number and verify it
    //TODO: anyone whom want to create a service we need to have their phone number
    //TODO: get server query's all the necessary info at once
    //TODO: rate... update view after rate.. manage it better on the server-side
    //TODO: sql-injection
    //TODO: add location
    //TODO: maybe implement show more detail for DriverTrip
    //TODO: put a limit on the report text length
    //TODO: intercept response on retrofit and check for errors returned by the server http://stackoverflow.com/questions/32294557/retrofit-intercept-responses-globally

    @Override
    protected void onManamMiamCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        setUpView();
    }
    private void setUpView() {
        fab = (FloatingActionButton) findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(this);
        viewPager = (MViewPager) findViewById(R.id.activity_main_view_pager);
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

    @Override
    public void onScrolled(boolean isUpScroll) {
        // called by the ViewPager
        if (isUpScroll) fab.hide();
        else fab.show();

    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.activity_main_fab) {
            NewRequestDialogFragment requestDialog = new NewRequestDialogFragment();
            requestDialog.show(getSupportFragmentManager(), "THE NOT SO USEFUL TAG");
        }
    }


    @Subscribe
    public void onRequestFailureRecevied(ServiceFailure failure){

    }
}