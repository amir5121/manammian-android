package com.amir.manammiam.base;

import android.app.Application;

import com.amir.manammiam.infrastructure.User;

public class ManamMyamSingelton extends Application {
    private User user;

    public User getUser() {
        return user;
    }
}
