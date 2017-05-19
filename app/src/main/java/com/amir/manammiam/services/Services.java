package com.amir.manammiam.services;

import android.view.View;

import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
        @SerializedName("token")
        private final String token;

        @SerializedName("gender")
        private final int gender;

        public ServicesRequest(String token, int gender) {
            this.token = token;
            this.gender = gender;
        }

        public int getGender() {
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

        private final long sourceId;
        private final long destinationId;
        private final int gender;

        public ServicesSpecificRequest(long sourceId, long destinationId, int gender) {
            this.sourceId = sourceId;
            this.destinationId = destinationId;
            this.gender = gender;
        }

        public int getGender() {
            return gender;
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
        private final long carId;
        private final int price;
        private final int capacity;
        private final String time;
        private final long passengerId;

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
        public static final int NO_VALID_CAR_OR_DOSEN_T_OWN_THE_CAR = 2;
        public static final int SERVERS_TOO_CLOSE_TO_EACHOTHER = 3;
        public static final int SOMTHING_WENT_WRONG = 4;
        public static final int SERVICE_IS_NOT_IN_THE_FUTURE = 5;
        
        @SerializedName("result")
        private int result;

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

    public class ReserveResponsePOJO {
        @Expose
        @SerializedName("result")
        public Integer result;

    }

    public static class ReserveResponse extends ManamMiamResponse {
        public static final int NO_SUCH_A_SERVER_EXISTS = 0;
        public static final int SUCCESSFUL = 1;
        public static final int FAILED = 2;
        public static final int ALREADY_A_PASSENGER = 4;

        @Expose
        @SerializedName("result")
        private Integer responseResult;

        private View loading;
        private ManamMiamService service;

        public ReserveResponse(int result) {
            this.responseResult = result;
        }

        public int getResponseResult() {
            return responseResult;
        }

        public View getLoading() {
            return loading;
        }

        public ManamMiamService getService() {
            return service;
        }

        public void setService(ManamMiamService service) {
            this.service = service;
        }

        public void setLoading(View loading) {
            this.loading = loading;
        }
    }

}
