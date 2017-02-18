package com.amir.manammiam.activities;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseAuthenticatedActivity;
import com.amir.manammiam.infrastructure.ViewPagerAdapter;

public class MainActivity extends BaseAuthenticatedActivity {

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;

    //TODO: https://github.com/rengwuxian/MaterialEditText
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


        ((BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_bottom_navigation_favorite) {
                    viewPager.setCurrentItem(0, true);
                } else if (itemId == R.id.menu_bottom_navigation_inbox) {
                    viewPager.setCurrentItem(1, true);
                } else if (itemId == R.id.menu_bottom_navigation_profile) {
                    Log.e(TAG, "profile on button nav was clicked");
                    viewPager.setCurrentItem(2, true);
                }
                return false;
            }
        });


    }
}
