package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class Account {
    private Account() {
    }

    public static class LoginRequest {
        private static final int ANDROID_APP = 1;
        private final String username;
        private final String password;
        private final int sourceType = ANDROID_APP;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;

        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getSourceType() {
            return sourceType;
        }
    }

    public static class LoginResponse extends ManamMiamResponse {

        @Expose
        @SerializedName("token")
        private String token = null;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
    public static class LogoutRequest {

        @SerializedName("token")
        private final String token;

        public LogoutRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class LogoutResponse extends ManamMiamResponse {
        @SerializedName("getResponseResult")
        private final boolean result;

        public LogoutResponse(boolean result) {
            this.result = result;
        }

        public boolean getResult() {
            return result;
        }
    }

    public static class ProfileRequest {
        private final String token;

        public ProfileRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class ProfileResponse extends ManamMiamResponse {
        @SerializedName("name")
        private String name;

        @SerializedName("username")
        private String username;

        @SerializedName("gender")
        private int gender;

        @SerializedName("is_driver")
        private boolean isDriver;

        @SerializedName("email")
        private String mail;

        @SerializedName("permission")
        private int permission;

        @SerializedName("phone_number")
        private String phoneNumber;

        @SerializedName("telegram_id")
        private String telegram_id;


        public ProfileResponse(String name, String username, int gender, boolean isDriver, String mail, int permission, String phoneNumber, String telegram_id) {
            this.name = name;
            this.username = username;
            this.gender = gender;
            this.isDriver = isDriver;
            this.mail = mail;
            this.permission = permission;
            this.phoneNumber = phoneNumber;
            this.telegram_id = telegram_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getTelegram_id() {
            return telegram_id;
        }

        public void setTelegram_id(String telegram_id) {
            this.telegram_id = telegram_id;
        }

        public boolean isDriver() {
            return isDriver;
        }

        public void setDriver(boolean driver) {
            isDriver = driver;
        }
    }

    public static class CarsRequest {
        String token;

        public CarsRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class CarsResponse extends ManamMiamResponse {
        private final ArrayList<Car> cars;


        public CarsResponse(ArrayList<Car> cars) {
            this.cars = cars;
        }

        public ArrayList<Car> getCars() {
            return cars;
        }
    }

    public static class AddCarRequest {
        private final String token;
        private final String carCode;
        private final String carColor;
        private final String carType;
        private final boolean isTaxi;

        public AddCarRequest(String token, String carCode, String carColor, String carType, boolean isTaxi) {
            this.token = token;
            this.carCode = carCode;
            this.carColor = carColor;
            this.carType = carType;
            this.isTaxi = isTaxi;
        }

        public String getToken() {
            return token;
        }

        public String getCarCode() {
            return carCode;
        }

        public String getCarColor() {
            return carColor;
        }

        public String getCarType() {
            return carType;
        }

        public boolean isTaxi() {
            return isTaxi;
        }
    }

    public static class AddCarResponse extends ManamMiamResponse {
        @SerializedName("result")
        private boolean result;

        public boolean getResult() {
            return result;
        }
    }


    public static class EnrollRequest {
        private final String email;
        private final String username;
        private final String name;
        private final String password;
        private final boolean gender;

        public EnrollRequest(String email, String username, String name, String password, boolean gender) {
            this.email = email;
            this.username = username;
            this.name = name;
            this.password = password;
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public boolean isGender() {
            return gender;
        }
    }

    public static class EnrollResponse extends ManamMiamResponse {
        private final long result;

        public EnrollResponse(long result) {
            this.result = result;
        }

        public long getResult() {
            return result;
        }
    }

}
