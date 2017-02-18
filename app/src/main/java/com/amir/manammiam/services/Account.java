package com.amir.manammiam.services;

import android.util.Log;

public final class Account {
    private Account() {
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
            Log.e(getClass().getSimpleName(), "New Account.LoginRequest was created");

        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class LoginResponse extends ServiceResponse {

        public LoginResponse() {
            Log.e(getClass().getSimpleName(), "New login Response was created");
        }

        private String token = null;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class ProfileRequest{
        String token;

        public ProfileRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class ProfileResponse extends ServiceResponse{
        public String username;
        public String name;
        public boolean gender;
        public boolean isTaxi; //whatever the fuck it represent
        public String mail;
        public int permission;

        public ProfileResponse(String username, String name, boolean gender, boolean isTaxi, String mail, int permission) {
            this.username = username;
            this.name = name;
            this.gender = gender;
            this.isTaxi = isTaxi;
            this.mail = mail;
            this.permission = permission;
        }
    }

}
