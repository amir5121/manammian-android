package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class Location {
    private Location() {
    }

    public static class LocationRequest {
        private final String sequence;

        public LocationRequest(String sequence) {
            this.sequence = sequence;
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
