package com.amir.manammiam.services;

import android.os.Handler;
import android.util.Log;

import com.amir.manammiam.base.ManamMiamApplication;
import com.squareup.otto.Bus;

import java.util.Random;

public abstract class BaseInMemoryService {
    protected final Bus bus;
    protected final ManamMiamApplication application;
    protected final Handler handler;
    protected final Random random;

    public BaseInMemoryService(ManamMiamApplication application) {
        this.application = application;
        bus = application.getBus();
        bus.register(this);
        handler = new Handler();
        random = new Random();
    }

    protected void invokeDelay(Runnable runnable, long milliSecondMin, long milliSecondMax) {
        if (milliSecondMin > milliSecondMax)
            throw new IllegalArgumentException("Min Must be smaller than Max");

        long delay = (long) (random.nextDouble() * (milliSecondMax - milliSecondMin) + milliSecondMin);
        handler.postDelayed(runnable, delay);
    }

    protected void postEvent(final Object event, long milliSecondMin, long milliSecondMax) {
        invokeDelay(new Runnable() {
            @Override
            public void run() {
                bus.post(event);
            }
        }, milliSecondMin, milliSecondMax);
    }

    protected void postEvent(Object event, long milliSecond) {
        postEvent(event, milliSecond, milliSecond);
    }

    protected void postEvent(Object event) {
        postEvent(event, 600, 1200);
    }
}
