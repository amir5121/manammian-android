package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.User;

import java.util.ArrayList;

public final class Account {
    private Account() {
    }

    public static class LoginRequest {
        private String username;
        private String password;

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
    }

    public static class LoginResponse extends Response {

        private String token = null;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class ProfileRequest {
        String token;

        public ProfileRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class ProfileResponse extends Response {
        private User user;

        public ProfileResponse(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
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

    public static class CarsResponse extends Response {
        ArrayList<Car> cars;

        public CarsResponse(ArrayList<Car> cars) {
            this.cars = cars;
        }

        public ArrayList<Car> getCars() {
            return cars;
        }
    }


}
