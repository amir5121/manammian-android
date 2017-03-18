package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.location.ManamMiamLocation;

import java.util.ArrayList;

public class Location {
    private Location() {
    }

    public static class LocationRequest {
        String token;
        String name;

        public LocationRequest(String token, String name) {
            this.token = token;
            this.name = name;
        }

        public String getToken() {
            return token;
        }
    }

    public static class LocationResponse extends Response {
        ArrayList<ManamMiamLocation> locations;

        public LocationResponse(ArrayList<ManamMiamLocation> locations) {
            this.locations = locations;
        }

        public ArrayList<ManamMiamLocation> getLocations() {
            return locations;
        }
    }
}
