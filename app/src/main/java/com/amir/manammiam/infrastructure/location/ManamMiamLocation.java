package com.amir.manammiam.infrastructure.location;

public class ManamMiamLocation {
    private final String name;
    private final String detailed;
    private final long id;
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
