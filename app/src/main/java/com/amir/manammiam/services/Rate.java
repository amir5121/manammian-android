package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.trip.TripItem;

public class Rate {

    public static class RateRequest {
        private final TripItem tripItem;
        private final View loading;
        private final View responseContainer;
        private String token;
        private long carId;
        private float rate;

        public RateRequest(String token, long carId, float rate, View loading, View responseContainer, TripItem tripItem) {
            this.token = token;
            this.carId = carId;
            this.rate = rate;
            this.tripItem = tripItem;
            this.loading = loading;
            this.responseContainer = responseContainer;
        }

        public float getRate() {
            return rate;
        }

        public TripItem getTripItem() {
            return tripItem;
        }

        public View getLoading() {
            return loading;
        }

        public View getResponseContainer() {
            return responseContainer;
        }

        public String getToken() {
            return token;
        }

        public long getCarId() {
            return carId;
        }
    }

    public static class RateResponse extends Response{
        private final TripItem tripItem;
        private final View loading;
        private final View responseContainer;

        public RateResponse(TripItem tripItem, View loading, View responseContainer) {
            this.tripItem = tripItem;
            this.loading = loading;
            this.responseContainer = responseContainer;
        }

        public TripItem getTripItem() {
            return tripItem;
        }

        public View getLoading() {
            return loading;
        }

        public View getResponseContainer() {
            return responseContainer;
        }
    }
}
