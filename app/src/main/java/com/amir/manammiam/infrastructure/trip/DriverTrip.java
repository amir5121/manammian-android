package com.amir.manammiam.infrastructure.trip;

import com.amir.manammiam.infrastructure.car.Car;
import com.google.gson.annotations.SerializedName;

public final class DriverTrip extends Trip {

    @SerializedName("capacity")
    private final int capacity;

    @SerializedName("number_of_passenger")
    private final int numberOfPassenger;//passenger count

    public DriverTrip(String time, String sourceName, String destinationName, Car car, String price, int capacity, int numberOfPassenger, long serverID) {
        super(time, sourceName, destinationName, car, price, serverID);
        this.capacity = capacity;
        this.numberOfPassenger = numberOfPassenger;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfPassenger() {
        return numberOfPassenger;
    }
}
