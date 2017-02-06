package com.amir.manammiam.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amir.manammiam.R;
import com.amir.manammiam.navdrawer.NavDrawer;

public class BaseActivity extends AppCompatActivity {
    ManamMyamSingelton application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ManamMyamSingelton) getApplication();
    }


    private Toolbar toolbar;
    protected NavDrawer navDrawer;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);


        toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            if (ab != null)
                ab.setTitle(getString(R.string.app_name));
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setNavDrawer(NavDrawer navDrawer) {
        this.navDrawer = navDrawer;
        navDrawer.create();
    }
}
