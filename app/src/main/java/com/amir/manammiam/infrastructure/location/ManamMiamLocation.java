package com.amir.manammiam.infrastructure.location;

import com.google.gson.annotations.SerializedName;

public class ManamMiamLocation {

    @SerializedName("location_name")
    private final String name;

    @SerializedName("location_id")
    private final long id;

    @SerializedName("location_detailed")
    private final String detailed;


    private boolean isSelected;

    public ManamMiamLocation(String name, String detailed, long id) {
        this.name = name;
        this.detailed = detailed;
        this.id = id;
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public String getDetailed() {
        return detailed;
    }

    public long getId() {
        return id;
    }
}
