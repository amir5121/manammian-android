package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.trip.TripItem;

public class MMTime {
    public static class Request {
        private final String token;
        private final String time;
        private final View cancelContainer;
        private final View responseContainer;
        private final TripItem tripItem;
        private final View loadingContainer;

        public Request(String token, String time, View cancelContainer, View responseContainer, View loadingContainer, TripItem tripItem) {
            this.token = token;
            this.time = time;
            this.cancelContainer = cancelContainer;
            this.responseContainer = responseContainer;
            this.tripItem = tripItem;
            this.loadingContainer = loadingContainer;
        }

        public TripItem getTripItem() {
            return tripItem;
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

    public static class TimeResponse extends Response {
        private final boolean isInTheFuture;
        private final View cancelContainer;
        private final View responseContainer;
        private final TripItem tripItem;
        private View loadingContainer;

        public TimeResponse(TripItem tripItem, View responseContainer, View cancelContainer, boolean isInTheFuture, View loadingContainer) {
            this.tripItem = tripItem;
            this.responseContainer = responseContainer;
            this.cancelContainer = cancelContainer;
            this.isInTheFuture = isInTheFuture;
            this.loadingContainer = loadingContainer;
        }

        public TripItem getTripItem() {
            return tripItem;
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
