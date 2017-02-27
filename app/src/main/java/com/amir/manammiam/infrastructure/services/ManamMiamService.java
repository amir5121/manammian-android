package com.amir.manammiam.infrastructure.services;

import com.amir.manammiam.infrastructure.Car;

public class ManamMiamService {
    private String server_id;
    private String car_id;
    private String source;
    private Car car;
    private String destination;
    private String price;
    private int capacity;
    private String time;
    private String name;
    private boolean isActivated;

    public ManamMiamService(String server_id, String car_id, String source, Car car, String destination, String price, int capacity, String time, String name) {
        this.server_id = server_id;
        this.car_id = car_id;
        this.source = source;
        this.car = car;
        this.destination = destination;
        this.price = price;
        this.capacity = capacity;
        this.time = time;
        this.name = name;
        isActivated = false;
    }

    public String getServer_id() {
        return server_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public String getSource() {
        return source;
    }

    public Car getCar() {
        return car;
    }

    public String getDestination() {
        return destination;
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

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
