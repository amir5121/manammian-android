package com.amir.manammiam.base;

import android.app.Application;
import android.util.Log;

import com.amir.manammiam.infrastructure.Database;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.services.Module;
import com.squareup.otto.Bus;

public class ManamMiamApplication extends Application {
    private User user;
    private Bus bus;

    public ManamMiamApplication() {
        bus = new Bus();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Module.register(this);
        Database database = new Database(this);
        user = database.getUser();
    }


    public User getUser() {
        return user;
    }

    public Bus getBus() {
        return bus;
    }
}
