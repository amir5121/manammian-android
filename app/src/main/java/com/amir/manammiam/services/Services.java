package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.services.ManamMiamService;

import java.util.ArrayList;

public final class Services {

    public static class ServicesResponse extends ManamMiamResponse {
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
        private final boolean gender;


        public ServicesRequest(String token, boolean gender) {
            this.token = token;
            this.gender = gender;
        }

        public boolean isGender() {
            return gender;
        }

        public String getToken() {
            return token;
        }
    }

    public static class ServicesSpecificResponse extends ManamMiamResponse {
        private ArrayList<ManamMiamService> services;

        public ServicesSpecificResponse(ArrayList<ManamMiamService> services) {
            this.services = services;
        }

        public ArrayList<ManamMiamService> getServices() {
            return services;
        }
    }

    public static class ServicesSpecificRequest {
        private final String token;
        private final long sourceId;
        private final long destinationId;
        private final boolean gender;

        public ServicesSpecificRequest(String token, long sourceId, long destinationId, boolean gender) {
            this.token = token;
            this.sourceId = sourceId;
            this.destinationId = destinationId;
            this.gender = gender;
        }

        public boolean isGender() {
            return gender;
        }

        public String getToken() {
            return token;
        }

        public long getSourceId() {
            return sourceId;
        }

        public long getDestinationId() {
            return destinationId;
        }
    }

    public static class ReportServiceResponse extends ManamMiamResponse {
        public static final int SUCCESSFUL = 1;

        private final int result;

        public ReportServiceResponse(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }
    }

    public static class ReportServiceRequest {
        private final String token;
        private final String text;
        private final long serviceId;

        public ReportServiceRequest(String token, String text, long serviceId) {
            this.token = token;
            this.text = text;
            this.serviceId = serviceId;
        }

        public String getToken() {
            return token;
        }

        public String getText() {
            return text;
        }

        public long getServiceId() {
            return serviceId;
        }
    }

    public static class AddServicesRequest {
        private final String token;
        private final long sourceId;
        private final long destinationId;
        private final long passengerId;
        private final long carId;
        private final int price;
        private final int capacity;
        private final String time;

        public AddServicesRequest(String token, long sourceId, long destinationId, long carId, int price, int capacity, String time, long passengerId) {
            this.token = token;
            this.sourceId = sourceId;
            this.destinationId = destinationId;
            this.carId = carId;
            this.price = price;
            this.capacity = capacity;
            this.time = time;
            this.passengerId = passengerId;

            if (passengerId == 0) {
                //create just a server
            } else {
                //create a server and send a notification to the passenger (as his request for a service has been responded)
            }
        }

        public long getPassengerId() {
            return passengerId;
        }

        public String getToken() {
            return token;
        }

        public long getSourceId() {
            return sourceId;
        }

        public long getDestinationId() {
            return destinationId;
        }

        public long getCarId() {
            return carId;
        }

        public int getPrice() {
            return price;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getTime() {
            return time;
        }
    }

    public static class AddServicesResponse extends ManamMiamResponse {
        public static final int SUCCESSFUL = 1;

        private final int result;

        public AddServicesResponse(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }
    }

    public static class ReserveRequest {
        private final ManamMiamService service;
        private final View loading;
        private final String token;

        public ReserveRequest(ManamMiamService service, View loading, String token) {
            this.service = service;
            this.loading = loading;
            this.token = token;
        }

        public ManamMiamService getService() {
            return service;
        }

        public View getLoading() {
            return loading;
        }

        public String getToken() {
            return token;
        }
    }

    public static class ReserveResponse extends ManamMiamResponse {
        public static final int SUCCESSFUL = 1;

        private final int result;
        private final View loading;
        private final ManamMiamService service;

        public ReserveResponse(int result, View loading, ManamMiamService service) {
            this.result = result;
            this.loading = loading;
            this.service = service;
        }

        public int getResult() {
            return result;
        }

        public View getLoading() {
            return loading;
        }

        public ManamMiamService getService() {
            return service;
        }
    }

}
