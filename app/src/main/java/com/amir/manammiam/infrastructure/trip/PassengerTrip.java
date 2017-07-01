package com.amir.manammiam.infrastructure.trip;

import com.amir.manammiam.infrastructure.car.Car;
import com.google.gson.annotations.SerializedName;

public final class PassengerTrip extends Trip {

    @SerializedName("telegram_id")
    private final String driverTelegramId;

    @SerializedName("phone_number")
    private final String driverPhoneNumber;

    @SerializedName("driver_name")
    private final String driverName;

    public PassengerTrip(String time, String sourceName, String destinationName, Car car, String price, String driverName, long serverID, String driverTelegramId, String driverPhoneNumber, long passengerId) {
        super(time, sourceName, destinationName, car, price, serverID, passengerId);
        this.driverName = driverName;
        this.driverTelegramId = driverTelegramId;
        this.driverPhoneNumber = driverPhoneNumber;
    }


    public String getDestinationName() {
        return destinationName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverTelegramId() {
        return driverTelegramId;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }
}

