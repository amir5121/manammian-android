package com.amir.manammiam.infrastructure;


public class User {
    public static final boolean MALE = true;
    public static final int MALE_INT = 0;
    public static final boolean FEMALE = false;

    public static final int UNVERIFIED = 0;
    public static final int VERIFIED = 1;
    public static final int BLOCKED = 2;
    private static final String TAG = "User";

    private String name;
    private String username;
    private boolean gender;
    private String mail;
    private int permission;
    private String token;
    private boolean isDriver;

    public User(String username, String name, boolean gender, String mail, int permission, String token, boolean isDriver) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public boolean hasToken() {
        return !(token == null || token.isEmpty());
    }

}
