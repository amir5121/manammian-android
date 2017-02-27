package com.amir.manammiam.services;

import com.amir.manammiam.base.ManamMiamApplication;
import com.amir.manammiam.infrastructure.Car;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.trip.TripItem;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(ManamMiamApplication application) {
        super(application);
    }

    @Subscribe
    public void login(Account.LoginRequest request) {

        Account.LoginResponse response = new Account.LoginResponse();
//        response.setOperationError("Failing for no reason what so ever!");
        response.setToken("GET TOKEN FROM SERVER");
        postEvent(response);
    }

    @Subscribe
    public void getCars(Account.CarsRequest request) {
        request.getToken();

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car("Aventador", "Orange", "123 45 P", 4.5f, Car.ACCEPT_ALL, 120, false, 45));
        cars.add(new Car("GTR", "Dark Blue", "123 45 P", 5, Car.NO_VERIFIED_CAR, 520, false, 45));
        cars.add(new Car("One:1", "White", "123 45 P", 2, Car.BLOCKED, 10, false, 45));

        Account.CarsResponse response = new Account.CarsResponse(cars);
        postEvent(response);
    }


    @Subscribe
    public void getProfile(Account.ProfileRequest request) {
        request.getToken();

        Account.ProfileResponse response = new Account.ProfileResponse("amir5121", "Amir Hosein", User.MALE, true, "amirhoseinheshmati@gmail.com", User.VERIFIED);
//        response.setOperationError("Invalid token");
        postEvent(response);
    }

    @Subscribe
    public void getTrips(Trips.TripRequest request) {
        request.getToken();

        ArrayList<TripItem> trips = new ArrayList<>();

        trips.add(new TripItem("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas"));
        trips.add(new TripItem("1396/09/29 20:30", "پردیس", "سپاه", null, null, null));
        trips.add(new TripItem("1396/09/29 20:30", "Amir", "h", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas"));
        trips.add(new TripItem("1396/09/29 20:30", "پردیس", "سپاه", null, null, null));
        trips.add(new TripItem("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas"));
        trips.add(new TripItem("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas"));

        postEvent(new Trips.TripResponse(trips));
    }

    @Subscribe
    public void getPosts(Posts.PostsRequest request) {
        request.getToken();

        ArrayList<ManamMiamPost> posts = new ArrayList<>();

        posts.add(new ManamMiamPost("2", false, true, "bolvar sepah", "pardis", "5000", "AmirHosein", "i want to join", "1396/09/29 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, false, "bolvar sepah", "pardis", "5000", "AmirHosein", "wanna come with?", "1396/09/19 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, true, "bolvar sepah", "pardis", "5000", "AmirHosein", "i want to join", "1396/09/29 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, false, "bolvar sepah", "pardis", "5000", "AmirHosein", "wanna come with?", "1396/09/19 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", true, true, "bolvar sepah", "pardis", "5000", "AmirHosein", "i want to join", "1396/09/29 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, false, "bolvar sepah", "pardis", "5000", "AmirHosein", "wanna come with?", "1396/09/19 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", true, true, "bolvar sepah", "pardis", "5000", "AmirHosein", "i want to join", "1396/09/29 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, false, "bolvar sepah", "pardis", "5000", "AmirHosein", "wanna come with?", "1396/09/19 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", true, true, "bolvar sepah", "pardis", "5000", "AmirHosein", "i want to join", "1396/09/29 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, false, "bolvar sepah", "pardis", "5000", "AmirHosein", "wanna come with?", "1396/09/19 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", true, true, "bolvar sepah", "pardis", "5000", "AmirHosein", "i want to join", "1396/09/29 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));
        posts.add(new ManamMiamPost("2", false, false, "bolvar sepah", "pardis", "5000", "AmirHosein", "wanna come with?", "1396/09/19 20:30", "Pride", "blue", 3.6f, 5, "۷۸ ح ۳۴۵ ۳۴", 15, false, 12005));

        postEvent(new Posts.PostResponse(posts));
    }

    @Subscribe
    public void onTimeRequestReceived(MMTime.Request request) {
        MMTime.TimeResponse response;
        if (request.getTripItem().getSourceName().equals("Amir")) {
            response = new MMTime.TimeResponse(request.getTripItem(), request.getResponseContainer(), request.getCancelContainer(), false, request.getLoadingContainer());
        } else {
            response = new MMTime.TimeResponse(request.getTripItem(), request.getResponseContainer(), request.getCancelContainer(), true, request.getLoadingContainer());
        }
        postEvent(response);
    }

    @Subscribe
    public void onServicesRequestReceived(Services.ServicesRequest request) {
        ArrayList<ManamMiamService> services = new ArrayList<>();

        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService("1", "1", "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));

        Services.ServicesResponse response = new Services.ServicesResponse(services);

        postEvent(response);
    }

    @Subscribe
    public void onRateRequestReceived(Rate.RateRequest request) {
        Rate.RateResponse response = new Rate.RateResponse(request.getTripItem(), request.getLoading(), request.getResponseContainer());

        postEvent(response);
    }
}

