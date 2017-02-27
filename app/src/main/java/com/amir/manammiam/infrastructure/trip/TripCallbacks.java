package com.amir.manammiam.infrastructure.trip;

import android.view.View;

public interface TripCallbacks {
    void reportService(TripItem tripItem);

    void rateSubmitted(float rating, View loading, View responseContainer, TripItem tripItem);
}
