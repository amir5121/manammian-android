package com.amir.manammiam.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amir.manammiam.activities.LoginActivity;

public abstract class BaseAuthenticatedActivity extends BaseActivity {

    private static final String TAG = "BaseAuthenticatedAct";

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (true) {
//            //TODO: remove this block
//            if (application.getUser() == null || !application.getUser().hasToken()) {
//                application.setUser(new User(null, null, User.MALE, null, User.BLOCKED, "Random bullshit", false));
//                startActivity(new Intent(this, TokenLoginActivity.class));
//                finish();
//                return;
//            }
//        }

        if (application.getUser() == null || !application.getUser().hasToken()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        onManamMiamCreate(savedInstanceState);
    }

    protected abstract void onManamMiamCreate(Bundle savedInstanceState);
}
