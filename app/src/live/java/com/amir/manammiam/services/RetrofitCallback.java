package com.amir.manammiam.services;

import android.util.Log;

import com.squareup.otto.Bus;

import retrofit2.*;

public abstract class RetrofitCallback<T extends ManamMiamResponse> implements Callback<T> {

    private static final String TAG = "RetrofitCallback";


    protected abstract void onResolve(T body);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null) {
            onResolve(response.body());
            Log.e(TAG, "onResponse: success");
        } else {
            Log.e(TAG, "onResponse: with black object");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        Log.e(TAG, "onFailure called" + call.toString() + " ------- " + throwable.toString() + " " + throwable.getMessage());

    }
}
