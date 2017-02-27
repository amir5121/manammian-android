package com.amir.manammiam.infrastructure.post;

import android.util.Log;

import com.amir.manammiam.infrastructure.Car;

public class ManamMiamPost {
    //TODO: replace with personToken
    private String person_id;

    private boolean isRead;
    private boolean who;
    private String sourceName;
    private String destinationName;
    private String price;
    private String senderName;
    private String text;
    private String time;
    private Car car;
    private int capacity;
    private boolean activated;
    private boolean expanded;

    //after a driver tap on a passenger create a server right away and send a notification to the passenger.. passenger can either accept or deny

    //TODO: if who is 0 prompt the driver for the price, capacity and make a service

    //NOTE: if who is 0 then driver asking the passenger
    //             is 1 then passenger has chosen a server and the driver is receiving the notification

    public ManamMiamPost(String person_id, boolean isRead, boolean who, String sourceName, String destinationName, String price, String senderName, String text, String time, String carType, String carColor, float rate, int capacity, String car_code, int rateCount, boolean isTaxi, long carId) {
        this.person_id = person_id;
        this.isRead = isRead;
        this.who = who;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.price = price;
        this.senderName = senderName;
        this.text = text;
        this.time = time;
        car = new Car(carType, carColor, car_code, rate, Car.UNKNOWN, rateCount, isTaxi, carId);
        this.capacity = capacity;
    }

    public Car getCar() {
        return car;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean getWho() {
        return who;
    }

    public void setWho(boolean who) {
        this.who = who;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setExpanded(boolean expanded) {
        if (expanded && who) Log.e(getClass().getName(), "What the fuck is happening");
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
