package com.amir.manammiam.base;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.amir.manammiam.R;
import com.amir.manammiam.navdrawer.NavDrawer;
import com.amir.manammiam.services.Module;
import com.squareup.otto.Bus;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    protected ManamMiamApplication application;
    private Toolbar toolbar;
    protected NavDrawer navDrawer;
    protected Bus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ManamMiamApplication) getApplication();
        bus = application.getBus();

    }


    @Override
    protected void onStart() {
        super.onStart();
        Module.register(application);
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

//        setLocale();

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

    public void setLocale() {
//        String language = null;
        Locale locale = new Locale("fa");

        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        //todo: ??? need to use updateConfiguration replacement
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
