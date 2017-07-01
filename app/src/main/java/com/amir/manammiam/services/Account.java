package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.car.Car;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class Account {
    private Account() {
    }

    public static class LoginRequest {
        private static final int ANDROID_APP = 1;
        private final String phoneNumber;
        private final String password;
        private final int sourceType = ANDROID_APP;

        public LoginRequest(String phoneNumber, String password) {
            this.phoneNumber = phoneNumber;
            this.password = password;

        }


        public String getPassword() {
            return password;
        }

        public int getSourceType() {
            return sourceType;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    public static class LoginResponse extends ManamMiamResponse {

        public static final int SUCCESSFUL = 1;
        public static final int UNVERIFIED = 0;
        public static final int BLOCKED = 2;
        public static final int FAILED = 4;
        public static final int SOMETHING_WENT_WRONG = 5;

        @Expose
        @SerializedName("token")
        private String token = null;

        @SerializedName("result")
        private int result;

        public String getToken() {
            return token;
        }

        public int getResult() {
            return result;
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

//        @SerializedName("username")
//        private String username;

        @SerializedName("gender")
        private int gender;

        @SerializedName("is_driver")
        private boolean isDriver;
//
        @SerializedName("permission")
        private int permission;

        @SerializedName("phone_number")
        private String phoneNumber;

        @SerializedName("telegram_id")
        private String telegram_id;


        public ProfileResponse(String name, int gender, boolean isDriver, int permission, String phoneNumber, String telegram_id) {
            this.name = name;
            this.gender = gender;
            this.isDriver = isDriver;
//            this.mail = mail;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
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

        public String getTelegramId() {
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
        private final String phoneNumber;
        private final String telegramID;
        private final String name;
        private final String password;
        private final int gender;

        public EnrollRequest(String phoneNumber, String telegramID, String name, String password, int gender) {
            this.phoneNumber = phoneNumber;
            this.telegramID = telegramID;
            this.name = name;
            this.password = password;
            this.gender = gender;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getTelegramID() {
            return telegramID;
        }

        public int getGender() {
            return gender;
        }
    }

    public static class EnrollResponse extends ManamMiamResponse {
        private final boolean result;

        public EnrollResponse(boolean result) {
            this.result = result;
        }

        public boolean isResult() {
            return result;
        }
    }

}
