package com.amir.manammiam.infrastructure.trip;

import com.amir.manammiam.infrastructure.car.Car;

public class TripItem {
    private String time;
    private String sourceName;
    private String destinationName;
    private Car car;
    private String price;
    private String driverName;
    private int state;

    public static final int NONE = 0;
    public static final int LOADING = 1;
    public static final int CANCELING = 2;
    public static final int REPORT_RATE = 3;

    public TripItem(String time, String sourceName, String destinationName, Car car, String price, String driverName) {
        this.time = time;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.car = car;
        this.price = price;
        this.driverName = driverName;
        state = NONE;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}

