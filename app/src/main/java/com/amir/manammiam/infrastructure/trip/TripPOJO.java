package com.amir.manammiam.infrastructure.trip;

import com.amir.manammiam.infrastructure.car.Car;
import com.google.gson.annotations.SerializedName;

public class TripPOJO {


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

    @SerializedName("passenger_id")
    private final long passengerId;

    @SerializedName("telegram_id")
    private final String driverTelegramId;

    @SerializedName("phone_number")
    private final String driverPhoneNumber;

    @SerializedName("driver_name")
    private final String driverName;

    @SerializedName("capacity")
    private final int capacity;

    @SerializedName("number_of_passenger")
    private final int numberOfPassenger;//passenger count

    public TripPOJO(long serverID, String time, String sourceName, String destinationName, Car car, String price, long passengerId, String driverTelegramId, String driverPhoneNumber, String driverName, int capacity, int numberOfPassenger) {
        this.serverID = serverID;
        this.time = time;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.car = car;
        this.price = price;
        this.passengerId = passengerId;
        this.driverTelegramId = driverTelegramId;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverName = driverName;
        this.capacity = capacity;
        this.numberOfPassenger = numberOfPassenger;
    }

    public long getServerID() {
        return serverID;
    }

    public String getTime() {
        return time;
    }

    public String getSourceName() {
        return sourceName;
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

    public long getPassengerId() {
        return passengerId;
    }

    public String getDriverTelegramId() {
        return driverTelegramId;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfPassenger() {
        return numberOfPassenger;
    }
}
