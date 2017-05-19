package com.amir.manammiam.services;

import android.util.Log;

import com.squareup.otto.Bus;

import java.io.IOException;

import retrofit2.*;

public abstract class RetrofitCallback<T> implements Callback<T> {

    private static final String TAG = "RetrofitCallback";
    private final Bus bus;

    public RetrofitCallback(Bus bus) {
        this.bus = bus;
    }

    protected abstract void onResolve(T body);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null) {
            onResolve(response.body());
            Log.e(TAG, "onResponse: success " + response.code());
        } else {
            Log.e(TAG, "onResponse: with black object");
            bus.post(new ServiceFailure());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {


        String errorType;
        String errorDesc;
        if (throwable instanceof IOException) {
            errorType = " Timeout ";
            errorDesc = String.valueOf(throwable.getCause());

        } else if (throwable instanceof IllegalStateException) {
            errorType = " ConversionError ";
            errorDesc = String.valueOf(throwable.getCause());

        } else {
            errorType = " Other Error ";
            errorDesc = String.valueOf(throwable.getLocalizedMessage());
        }

        Log.e(TAG, "onFailure called " + errorDesc + " " + errorType);

//        throwable.printStackTrace();

        bus.post(new ServiceFailure());

    }
}
