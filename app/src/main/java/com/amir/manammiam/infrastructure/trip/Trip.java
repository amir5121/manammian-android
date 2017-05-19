package com.amir.manammiam.infrastructure.trip;

import com.amir.manammiam.infrastructure.car.Car;
import com.google.gson.annotations.SerializedName;

public class Trip {
    public static final int NONE = 0;
    public static final int LOADING = 1;
    public static final int CANCELING = 2;
    public static final int REPORT_RATE = 3;

    @SerializedName("server_id")
    private final long serverID;

    @SerializedName("time")
    protected final String time;

    @SerializedName("source_name")
    protected final String sourceName;

    @SerializedName("destination_name")
    protected final String destinationName;

    @SerializedName("car")
    protected final Car car;

    @SerializedName("price")
    protected final String price;

    private int state;

    public Trip(String time, String sourceName, String destinationName, Car car, String price, long serverID) {
        this.time = time;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.car = car;
        this.price = price;
        this.serverID = serverID;
        state = NONE;
    }

    public String getTime() {
        return time;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public Car getCar() {
        return car;
    }

    public String getPrice() {
        return price;
    }

    public int getState() {
        return state;
    }

    public long getServerID() {
        return serverID;
    }
}
