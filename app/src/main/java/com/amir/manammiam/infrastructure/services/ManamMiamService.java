package com.amir.manammiam.infrastructure.services;

import com.amir.manammiam.infrastructure.car.Car;
import com.google.gson.annotations.SerializedName;

public class ManamMiamService {
    public static final int NONE = 0;
    public static final int LOADING = 1;
    public static final int RESERVING = 2;

    @SerializedName("server_id")
    private final long serverId;

    @SerializedName("car_id")
    private final long carId;

    @SerializedName("source_name")
    private final String sourceName;

    @SerializedName("destination_name")
    private final String destinationName;

    @SerializedName("price")
    private final String price;

    @SerializedName("capacity")
    private final int capacity;

    @SerializedName("time")
    private final String time;

    @SerializedName("name")
    private final String name;

    private final Car car;

    private int state;

    public ManamMiamService(int serverId, long carId, String sourceName, Car car, String destinationName, String price, int capacity, String time, String name) {
        this.serverId = serverId;
        this.carId = carId;
        this.sourceName = sourceName;
        this.car = car;
        this.destinationName = destinationName;
        this.price = price;
        this.capacity = capacity;
        this.time = time;
        this.name = name;
        state = NONE;
    }

    public long getServerId() {
        return serverId;
    }

    public long getCarId() {
        return carId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public Car getCar() {
        return car;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
