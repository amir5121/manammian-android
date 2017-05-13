package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.trip.PassengerTrip;

public final class Rate {

    public static class RateRequest {
        private final PassengerTrip passengerTrip;
        private final View loading;
        private final View responseContainer;
        private String token;
        private float rate;

        public RateRequest(String token, float rate, View loading, View responseContainer, PassengerTrip passengerTrip) {
            this.token = token;
            this.rate = rate;
            this.passengerTrip = passengerTrip;
            this.loading = loading;
            this.responseContainer = responseContainer;
        }

        public float getRate() {
            return rate;
        }

        public PassengerTrip getPassengerTrip() {
            return passengerTrip;
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
            return passengerTrip.getCar().getCarId();
        }
    }

    public static class RateResponse extends ManamMiamResponse {
        private final PassengerTrip passengerTrip;
        private final View loading;
        private final View responseContainer;

        public RateResponse(PassengerTrip passengerTrip, View loading, View responseContainer) {
            this.passengerTrip = passengerTrip;
            this.loading = loading;
            this.responseContainer = responseContainer;
        }

        public PassengerTrip getPassengerTrip() {
            return passengerTrip;
        }

        public View getLoading() {
            return loading;
        }

        public View getResponseContainer() {
            return responseContainer;
        }
    }
}
