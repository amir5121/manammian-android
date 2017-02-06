package com.amir.manammiam.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amir.manammiam.activities.LoginActivity;

public abstract class BaseAuthenticatedActivity extends BaseActivity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!application.getUser().isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        onManamMiamCreate(savedInstanceState);
    }

    protected abstract void onManamMiamCreate(Bundle savedInstanceState);
}
