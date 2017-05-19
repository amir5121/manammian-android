package com.amir.manammiam.base;

import android.app.Application;

import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.Database;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.services.Module;
import com.squareup.otto.Bus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManamMiamApplication extends Application {
    private static final String TAG = "ManmamiamApplication";
    private User user;
    private Bus bus;
    private Database database;
    private Retrofit retrofit;

    public ManamMiamApplication() {
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = new Database(this);
        user = database.getUser();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Module.register(this);

    }

    public User getUser() {
        return user;
    }

    public Bus getBus() {
        return bus;
    }

    public void setUser(User user) {
//        Log.e(TAG, "setUser: " + user.getToken() );
        this.user = user;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Database getDatabase() {
        return database;
    }
}
