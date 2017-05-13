package com.amir.manammiam.services;

import com.google.gson.annotations.SerializedName;

public class Repo {

    @SerializedName("location_id")
    private long locationId;

    @SerializedName("location_name")
    private String locationName;

    @SerializedName("location_detailed")
    private String locationDetailed;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDetailed() {
        return locationDetailed;
    }

    public void setLocationDetailed(String locationDetailed) {
        this.locationDetailed = locationDetailed;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
}
