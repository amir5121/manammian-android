package com.amir.manammiam.services;

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
}
