package com.amir.manammiam.infrastructure.post;

import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;

public class ManamMiamPost {
    public static final int DRIVER_ASKING_PASSENGER = 0; // results in a prompt to the user whether to accept this service or not
    public static final int PASSENGER_ACCEPTED_A_SERVER = 1; // unclickable
    public static final int LOOKING_FOR_SERVER = 2; // prompt the driver for price and stuff

    private final int who;
    private final ManamMiamLocation source;
    private final ManamMiamLocation destination;
    private final String price;
    private final String senderName;
    private final String text;
    private final String time;
    private final Car car;
    private final long passengerId;
    private final long serverId;

    private boolean isRead;
    private int capacity;

    private boolean activated;
    private boolean expanded;
    private boolean inLoadingState;

    //after a driver tap on a passenger create a server right away and send a notification to the passenger.. passenger can either accept or deny

    //NOTE: if who is 0 then driver asking the passenger
    //             is 1 then passenger has chosen a server and the driver is receiving the notification

    public ManamMiamPost(
            boolean isRead,
            int who,
            ManamMiamLocation source,
            ManamMiamLocation destination,
            String price,
            String senderName,
            String text,
            String time,
            int capacity,
            Car car,
            long passengerId,
            long serverId) {

        this.isRead = isRead;
        this.who = who;
        this.source = source;
        this.destination = destination;
        this.price = price;
        this.senderName = senderName;
        this.text = text;
        this.time = time;
        this.car = car;
        this.capacity = capacity;
        this.passengerId = passengerId;
        expanded = false;
        activated = false;
        inLoadingState = false;
        this.serverId = serverId;
    }

    public long getServerId() {
        return serverId;
    }

    public long getSourceId() {
        return source.getId();
    }

    public long getDestinationId() {
        return destination.getId();
    }

    public Car getCar() {
        return car;
    }

    public ManamMiamLocation getSource() {
        return source;
    }

    public ManamMiamLocation getDestination() {
        return destination;
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

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getWho() {
        return who;
    }

    public String getSourceName() {
        return source.getName();
    }

    public String getDestinationName() {
        return destination.getName();
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
