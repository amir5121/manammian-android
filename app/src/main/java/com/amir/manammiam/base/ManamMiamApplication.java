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
    private Database database;

    public ManamMiamApplication() {
        bus = new Bus();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(getClass().getName(), "onCreateCalled");
        Module.register(this);
        database = new Database(this);
        user = database.getUser();
        if (user == null) {
            //TODO: should this be null?
            user = new User(null, null, User.MALE, null, User.BLOCKED, null, false);
            //this will be used temporary
        }
    }


    public User getUser() {
        return user;
    }

    public Bus getBus() {
        return bus;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
