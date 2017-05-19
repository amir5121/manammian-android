package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.trip.Trip;
import com.google.gson.annotations.SerializedName;

public final class MMTime {
    public static class Request {
        private final String token;

        @SerializedName("date")
        private final String time;

        private final View cancelContainer;
        private final View responseContainer;
        private final Trip trip;
        private final View loadingContainer;

        public Request(String token, String time, View cancelContainer, View responseContainer, View loadingContainer, Trip trip) {
            this.token = token;
            this.time = time;
            this.cancelContainer = cancelContainer;
            this.responseContainer = responseContainer;
            this.trip = trip;
            this.loadingContainer = loadingContainer;
        }

        public Trip getTrip() {
            return trip;
        }

        public View getCancelContainer() {
            return cancelContainer;
        }

        public View getResponseContainer() {
            return responseContainer;
        }

        public String getToken() {
            return token;
        }

        public String getTime() {
            return time;
        }

        public View getLoadingContainer() {
            return loadingContainer;
        }
    }

    public static class TimeResponse extends ManamMiamResponse {

        @SerializedName("is_in_the_future")
        private final boolean isInTheFuture;

        private final View cancelContainer;
        private final View responseContainer;
        private final Trip trip;
        private View loadingContainer;

        public TimeResponse(Trip trip, View responseContainer, View cancelContainer, boolean isInTheFuture, View loadingContainer) {
            this.trip = trip;
            this.responseContainer = responseContainer;
            this.cancelContainer = cancelContainer;
            this.isInTheFuture = isInTheFuture;
            this.loadingContainer = loadingContainer;
        }

        public Trip getTrip() {
            return trip;
        }

        public boolean isInTheFuture() {
            return isInTheFuture;
        }

        public View getCancelContainer() {
            return cancelContainer;
        }

        public View getResponseContainer() {
            return responseContainer;
        }

        public View getLoadingContainer() {
            return loadingContainer;
        }
    }
}
