package com.amir.manammiam.infrastructure.services;

public class ManamMiamService {
    private String server_id;
    private String car_id;
    private double rate;
    private String source;
    private String destination;
    private String price;
    private int capacity;
    private String carType;
    private String time;
    private String name;
    private String carCode;
    private String carColor;
    private boolean isActivated;

    public ManamMiamService(String server_id, String car_id, double rate, String source, String destination, String price, int capacity, String carType, String time, String name, String carCode, String carColor) {
        this.server_id = server_id;
        this.car_id = car_id;
        this.rate = rate;
        this.source = source;
        this.destination = destination;
        this.price = price;
        this.capacity = capacity;
        this.carType = carType;
        this.time = time;
        this.name = name;
        this.carCode = carCode;
        this.carColor = carColor;
        isActivated = false;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
