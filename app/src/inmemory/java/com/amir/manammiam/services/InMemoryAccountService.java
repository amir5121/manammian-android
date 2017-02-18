package com.amir.manammiam.services;

import android.util.Log;

import com.amir.manammiam.base.ManamMiamApplication;
import com.amir.manammiam.infrastructure.User;
import com.squareup.otto.Subscribe;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(ManamMiamApplication application) {
        super(application);
    }

    @Subscribe
    public void login(Account.LoginRequest request) {
        Log.e(getClass().getSimpleName(), "New response for LogIn");

        Account.LoginResponse response = new Account.LoginResponse();
//        response.setOperationError("Failing for no reason what so ever!");
        response.setToken("GET TOKEN FROM SERVER");
        postEvent(response);
    }

    @Subscribe
    public void getProfile(Account.ProfileRequest request) {
        request.getToken();

        postEvent(new Account.ProfileResponse("amir5121", "Amir Hosein", User.MALE, true, "amirhoseinheshmati@gmail.com", User.VERIFIED));
    }
}

