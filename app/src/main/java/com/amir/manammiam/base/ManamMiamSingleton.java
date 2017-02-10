package com.amir.manammiam.base;

import android.app.Application;

import com.amir.manammiam.infrastructure.User;

public class ManamMiamSingleton extends Application {
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
    }

    public User getUser() {
        return user;
    }
}
