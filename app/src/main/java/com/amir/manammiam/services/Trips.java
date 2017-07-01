package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.trip.Trip;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class Trips {
    public static final class TripRequest {
        @SerializedName("token")
        String token;

        public TripRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static final class TripResponse extends ManamMiamResponse {
        private ArrayList<Trip> trips;

        public TripResponse(ArrayList<Trip> trips) {
            this.trips = trips;
        }

        public ArrayList<Trip> getTrips() {
            return trips;
        }
    }


    public static class CancelRequest {
        private final Trip trip;
        private final View loading;
        private final View responseContainer;
        private final String token;

        public CancelRequest(Trip trip, View loading, View responseContainer, String token) {
            this.trip = trip;
            this.loading = loading;
            this.responseContainer = responseContainer;
            this.token = token;
        }

        public Trip getTrip() {
            return trip;
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
    }

    public static class CancelResponse extends ManamMiamResponse {
        public static final int SUCCESSFUL = 1;
        public static final int FAILED = 0;

        private final int result;

        private final View loading;
        private final View cancelContainer;
        private final Trip trip;

        public CancelResponse(View loading, View cancelContainer, int result, Trip trip) {
            this.loading = loading;
            this.cancelContainer = cancelContainer;
            this.result = result;
            this.trip = trip;
        }

        public int getResult() {
            return result;
        }

        public View getLoading() {
            return loading;
        }

        public View getCancelContainer() {
            return cancelContainer;
        }

        public Trip getTrip() {
            return trip;
        }
    }

    public static class CreateRequest {
        private final long sourceId;
        private final long destinationId;
        private final String time;
        private final String token;

        public CreateRequest(long sourceId, long destinationId, String time, String token) {
            this.sourceId = sourceId;
            this.destinationId = destinationId;
            this.time = time;
            this.token = token;
        }

        public long getSourceId() {
            return sourceId;
        }

        public long getDestinationId() {
            return destinationId;
        }

        public String getTime() {
            return time;
        }

        public String getToken() {
            return token;
        }
    }

    public static class CreateResponse extends ManamMiamResponse {
        public static final int SUCCESSFUL = 1;
        public static final int DUPLICATE_PASSENGER = 2;
        public static final int SOMETHING_WENT_WRONG = 3;

        @Expose
        @SerializedName("result")
        private int result;

        public CreateResponse(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }
    }

    public class CancelResponsePOJO extends ManamMiamResponse{

        @Expose
        @SerializedName("result")
        private final int result;

        public CancelResponsePOJO(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }
    }
}
