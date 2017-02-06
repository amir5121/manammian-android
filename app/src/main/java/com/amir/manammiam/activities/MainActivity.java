package com.amir.manammiam.activities;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amir.manammiam.R;
import com.amir.manammiam.base.BaseActivity;
import com.amir.manammiam.navdrawer.MainNavDrawer;

public class MainActivity extends BaseActivity {

    //TODO: use a custom textView so you could set fonts more easily

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavDrawer(new MainNavDrawer(this));

        Log.e(getClass().getName(), "MADE IT HERE");
    }
}
