package com.amir.manammiam.infrastructure.trip;

import com.amir.manammiam.infrastructure.car.Car;

public final class DriverTrip extends Trip {
    private final int capacity;
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
