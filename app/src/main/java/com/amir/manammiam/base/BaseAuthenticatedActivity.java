package com.amir.manammiam.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amir.manammiam.activities.TokenLoginActivity;

public abstract class BaseAuthenticatedActivity extends BaseActivity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getName(), "onCreateCalled");

        if (!application.getUser().isLoggedIn()) {

            startActivity(new Intent(this, TokenLoginActivity.class));
            finish();
            return;
        }

        onManamMiamCreate(savedInstanceState);
    }

    protected abstract void onManamMiamCreate(Bundle savedInstanceState);
}
