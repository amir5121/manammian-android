package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.location.ManamMiamLocation;

import java.util.ArrayList;

public final class Location {
    private Location() {
    }

    public static class LocationRequest {
        private final String token;
        private final String sequence;

        public LocationRequest(String token, String sequence) {
            this.token = token;
            this.sequence = sequence;
        }

        public String getToken() {
            return token;
        }

        public String getSequence() {
            return sequence;
        }
    }

    public static class LocationResponse extends ManamMiamResponse {
        ArrayList<ManamMiamLocation> locations;

        public LocationResponse(ArrayList<ManamMiamLocation> locations) {
            this.locations = locations;
        }

        public ArrayList<ManamMiamLocation> getLocations() {
            return locations;
        }
    }
}
