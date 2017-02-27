package com.amir.manammiam.infrastructure;

public class Car {
    public static final int ACCEPT_ALL = 3;
    public static final int NO_VERIFIED_CAR = 0;
    public static final int FEMALE = 2;
    public static final int MALE = 1;
    public static final int BLOCKED = 4;
    public static final int UNKNOWN = 5;
    private String carType;
    private String carColor;
    private String carCode;
    private float rate;
    private int genderAccepted;
    private int rateCount;
    private boolean isTaxi;
    private long carId;

    public Car(String carType, String carColor, String carCode, float rate, int genderAccepted, int rateCount, boolean isTaxi, long carId) {
        this.carType = carType;
        this.carColor = carColor;
        this.carCode = carCode;
        this.rate = rate;
        this.genderAccepted = genderAccepted;
        this.rateCount = rateCount;
        this.isTaxi = isTaxi;
        this.carId = carId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getGenderAccepted() {
        return genderAccepted;
    }

    public void setGenderAccepted(int genderAccepted) {
        this.genderAccepted = genderAccepted;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public boolean isTaxi() {
        return isTaxi;
    }

    public void setIsTaxi(boolean taxi) {
        isTaxi = taxi;
    }

    public long getCarId() {
        return carId;
    }
}
