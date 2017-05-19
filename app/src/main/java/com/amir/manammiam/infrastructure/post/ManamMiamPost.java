package com.amir.manammiam.infrastructure.post;

import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.google.gson.annotations.SerializedName;

public class ManamMiamPost {
    public static final int DRIVER_ASKING_PASSENGER = 0; // results in a prompt to the user whether to accept this service or not
    public static final int PASSENGER_ACCEPTED_A_SERVER = 1; // unclickable
    public static final int LOOKING_FOR_SERVER = 2; // prompt the driver for price and stuff

    @SerializedName("time")
    private final String time;

    @SerializedName("price")
    private final String price;

    @SerializedName("source_name")
    private final String sourceName;

    @SerializedName("source_id")
    private final long sourceId;

    @SerializedName("destination_name")
    private final String destinationName;

    @SerializedName("destination_id")
    private final long destinationId;

    @SerializedName("sender_name")
    private final String senderName;

    @SerializedName("text")
    private final String text;

    @SerializedName("is_read")
    private final boolean isRead;

    @SerializedName("who")
    private final int who;

    @SerializedName("car")
    private final Car car;

    @SerializedName("capacity")
    private int capacity;

    @SerializedName("passenger_id")
    private final long passengerId;

    @SerializedName("server_id")
    private final long serverId;

    private boolean activated;
    private boolean expanded;
    private boolean inLoadingState;

    //after a driver tap on a passenger create a server right away and send a notification to the passenger.. passenger can either accept or deny

    //NOTE: if who is 0 then driver asking the passenger
    //             is 1 then passenger has chosen a server and the driver is receiving the notification

    public ManamMiamPost(String time, String price, String sourceName, long sourceId, String destinationName, long destinationId, String senderName, String text, boolean isRead, int who, Car car, int capacity, long passengerId, long serverId) {
        this.time = time;
        this.price = price;
        this.sourceName = sourceName;
        this.sourceId = sourceId;
        this.destinationName = destinationName;
        this.destinationId = destinationId;
        this.senderName = senderName;
        this.text = text;
        this.isRead = isRead;
        this.who = who;
        this.car = car;
        this.capacity = capacity;
        this.passengerId = passengerId;
        this.serverId = serverId;
    }

    public long getServerId() {
        return serverId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public Car getCar() {
        return car;
    }

    public ManamMiamLocation getSource() {
        return new ManamMiamLocation(sourceName, null, sourceId);
    }

    public ManamMiamLocation getDestination() {
        return new ManamMiamLocation(destinationName, null, destinationId);

    }

    public long getPassengerId() {
        return passengerId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isRead() {
        return isRead;
    }

    public int getWho() {
        return who;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getPrice() {
        return price;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setExpanded(boolean expanded) {
//        if (expanded && who == PASSENGER_ACCEPTED_A_SERVER) Log.e(getClass().getSequence(), "What the fuck is happening");
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isInLoadingState() {
        return inLoadingState;
    }

    public void setLoadingState(boolean loadingState) {
        this.inLoadingState = loadingState;
    }
}
