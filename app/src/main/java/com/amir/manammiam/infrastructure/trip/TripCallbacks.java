package com.amir.manammiam.infrastructure.trip;

import android.view.View;

public interface TripCallbacks {
    void reportService(PassengerTrip passengerTrip);

    void rateSubmitted(float rating, View loading, View responseContainer, PassengerTrip passengerTrip);

    void cancelService(Trip item, View loading, View cancelContainer);
}
