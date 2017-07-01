package com.amir.manammiam.infrastructure;


public class User {

    public static final int GENDER_BLOCKED = 0;
    public static final int MALE = 1;
    public static final int FEMALE = 2;

    public static final int UNVERIFIED = 0;
    public static final int VERIFIED = 1;
    public static final int BLOCKED = 2;
    private static final String TAG = "User";

    private final String name;
    private final int gender;
    private final String phoneNumber;
    private final String telegramID;
    private final int permission;
    private final String token;
    private final boolean isDriver;

    public User(String phoneNumber, String name, int gender, String telegramID, int permission, String token, boolean isDriver) {
        this.phoneNumber  = phoneNumber;
        this.name = name;
        this.gender = gender;
        this.telegramID = telegramID;
        this.permission = permission;
        this.token = token;
        this.isDriver = isDriver;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTelegramID() {
        return telegramID;
    }

    public int getPermission() {
        return permission;
    }

    public boolean hasToken() {
        return !(token == null || token.isEmpty());
    }

}
