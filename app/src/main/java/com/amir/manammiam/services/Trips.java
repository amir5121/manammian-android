package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.trip.TripItem;

import java.util.ArrayList;

public final class Trips {
    public static final class TripRequest {
        String token;

        public TripRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static final class TripResponse extends Response{
        private ArrayList<TripItem> trips;

        public TripResponse(ArrayList<TripItem> trips) {
            this.trips = trips;
        }

        public ArrayList<TripItem> getTrips() {
            return trips;
        }
    }
}
