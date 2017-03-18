package com.amir.manammiam.services;

import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.services.ManamMiamService;

import java.util.ArrayList;

public final class Services {

    public static class ServicesResponse extends Response {
        private ArrayList<ManamMiamService> services;

        public ServicesResponse(ArrayList<ManamMiamService> services) {
            this.services = services;
        }

        public ArrayList<ManamMiamService> getServices() {
            return services;
        }
    }

    public static class ServicesRequest {
        private final String token;

        public ServicesRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class AddServicesRequest {
        private final String token;
        private final int sourceId;
        private final int destinationId;
        private final int carId;
        private final int price;
        private final int capacity;
        private final String date;

        public AddServicesRequest(String token, int sourceId, int destinationId, int carId, int price, int capacity, String date) {
            this.token = token;
            this.sourceId = sourceId;
            this.destinationId = destinationId;
            this.carId = carId;
            this.price = price;
            this.capacity = capacity;
            this.date = date;
        }

        public String getToken() {
            return token;
        }
    }
}
