package com.amir.manammiam.services;

import android.util.Log;

import com.squareup.otto.Bus;

import java.io.EOFException;
import java.io.IOException;

import retrofit2.*;

public abstract class RetrofitCallback<T> implements Callback<T> {

    private static final String TAG = "RetrofitCallback";
    private static Bus bus;
    private static ManamMiamWebServices services;
    //    private final Call<T> call;
    private int tryCount;

//    public RetrofitCallback(Call<T> call) {
//        this.call = call;
//    }

    public static void setUp(ManamMiamWebServices services, Bus bus) {
        if (RetrofitCallback.services == null)
            RetrofitCallback.services = services;
        if (RetrofitCallback.bus == null)
            RetrofitCallback.bus = bus;
    }

    protected abstract void onResolve(T body);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null && response.body() != null) {
            onResolve(response.body());
//            Log.e(TAG, "onResponse: success " + response.code());
        } else {

            if (retry(call)) return;

            Log.e(TAG, "onResponse: with black object");
            bus.post(new ServiceFailure());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {

        if (retry(call)) return;

        String errorType;
        String errorDesc;
        if (throwable instanceof IOException) {
//            if (throwable.getCause() instanceof EOFException) {
//
//            }
            errorType = " Timeout-- ";
            errorDesc = String.valueOf(throwable.getCause());

        } else if (throwable instanceof IllegalStateException) {
            errorType = " ConversionError ";
            errorDesc = String.valueOf(throwable.getCause());


        } else {
            errorType = " Other Error ";
            errorDesc = String.valueOf(throwable.getLocalizedMessage());
        }

        Log.e(TAG, "onFailure called " + errorDesc + errorType);

//        throwable.printStackTrace();

        bus.post(new ServiceFailure());

    }

    private boolean retry(Call<T> call) {
        if (tryCount++ < 3) {
            Log.e(TAG, "onFailure: trying again " + tryCount);
            call.clone().enqueue(this);
//            call.enqueue(this);
            return true;
        }
        return false;
    }
}
