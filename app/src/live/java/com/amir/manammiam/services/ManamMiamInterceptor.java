package com.amir.manammiam.services;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ManamMiamInterceptor implements Interceptor {
    private static final String TAG = "ManamMiamInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        int tryCount = 0;
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && tryCount < 3) {

            Log.e("intercept",  "Request is not successful - " + tryCount);

            tryCount++;

            // retry the request
            response = chain.proceed(request);
        }

        Log.e(TAG, "intercept: " + response.isSuccessful() + " response.code(): " + response.code());
        if (response.code() == 403) {

        }
        return response;
    }


}
