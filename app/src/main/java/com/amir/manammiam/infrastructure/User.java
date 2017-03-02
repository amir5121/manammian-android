package com.amir.manammiam.infrastructure;

public class User {
    public static final boolean MALE = true;
    public static final int MALE_INT = 0;
    public static final boolean FEMALE = false;

    public static final int UNVERIFIED = 0;
    public static final int VERIFIED = 1;
    public static final int BLOCKED = 2;

    public static final int LOGGED_IN_INT = 1;


    //todo: update these values every time the app loads up
    //todo: after getting the token by logging in get these information by logging in via token
    //todo: set token to null after logging out
    private String name;
    private String username;
    private boolean gender;
    private String mail;
    private int permission;
    private String token;
    boolean isLoggedIn;
    boolean isDriver;

    public User(String username, String name, boolean gender, String mail, int permission, String token, boolean isDriver) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.mail = mail;
        this.permission = permission;
        this.token = token;
        isLoggedIn = false;
        this.isDriver = isDriver;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        //TODO: This method must be removed
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

    public boolean isLoggedIn() {
//        return !(token == null || token.isEmpty());
        return isLoggedIn;
//        return false;
    }

}
