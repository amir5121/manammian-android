package com.amir.manammiam.services;

import com.amir.manammiam.base.ManamMiamApplication;
import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.User;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.trip.DriverTrip;
import com.amir.manammiam.infrastructure.trip.Trip;
import com.amir.manammiam.infrastructure.trip.PassengerTrip;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class InMemoryAccountService extends BaseInMemoryService {

    private static final String TAG = "InMemoryAccountService";

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
        cars.add(new Car("Aventador", "Orange", "123 45 P", 4.5f, Car.FEMALE, 120, false, 45));
        cars.add(new Car("Aventador", "Orange", "123 45 P", 4.5f, Car.MALE, 120, false, 45));
        cars.add(new Car("GTR", "Dark Blue", "123 45 P", 5, Car.NO_VERIFIED_CAR, 520, false, 45));
        cars.add(new Car("One:1", "White", "123 45 P", 2, Car.BLOCKED, 10, false, 45));

        Account.CarsResponse response = new Account.CarsResponse(cars);
        postEvent(response);
    }


    //this is basically login_with_token
    @Subscribe
    public void getProfile(Account.ProfileRequest request) {
        request.getToken();

        //todo: update cache database.user
        Account.ProfileResponse response = new Account.ProfileResponse("amirH", "amir5121", User.MALE, true, "amirhoseinheshmati@gmail.com", User.VERIFIED, "09178947604", "amir5121");
//        response.setOperationError("Invalid token");
        postEvent(response);
    }

    @Subscribe
    public void getPosts(Posts.PostsRequest request) {
        request.getToken();

        ArrayList<ManamMiamPost> posts = new ArrayList<>();

        //TODO: remember that if (!user.is_driver()) you can't have a ManamMiamPost.LOOKING_FOR_SERVER added to the post..
        //TODO: this happens in client side and not the server side

        Car car = new Car("Pride", "pale Blue", "125 a 93", 2.5f, Car.ACCEPT_ALL, 96, false, 6546);
        posts.add(new ManamMiamPost(false, ManamMiamPost.PASSENGER_ACCEPTED_A_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "i joined your service", "1396/09/29 20:30", 1, null, 0, 0));

        posts.add(new ManamMiamPost(false, ManamMiamPost.DRIVER_ASKING_PASSENGER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "Wanna come with", "1396/09/29 20:30", 3, car, 56431, 5312));

        posts.add(new ManamMiamPost(false, ManamMiamPost.LOOKING_FOR_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), null, "Amir", "I'm going from there to there", "1396/09/29 20:30", -1,  null, 65421, 0));

        posts.add(new ManamMiamPost(true, ManamMiamPost.PASSENGER_ACCEPTED_A_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "i joined your service", "1396/09/29 20:30", 1, null, 0, 0));

        posts.add(new ManamMiamPost(true, ManamMiamPost.DRIVER_ASKING_PASSENGER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "Wanna come with", "1396/09/29 20:30", 3, car, 654564, 6544));

        posts.add(new ManamMiamPost(true, ManamMiamPost.LOOKING_FOR_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), null, "Amir", "from here to there i am going", "1396/09/29 20:30", -1, null, 65421, 0));

        postEvent(new Posts.PostResponse(posts));
    }

    @Subscribe
    public void onTimeRequestReceived(MMTime.Request request) {
        MMTime.TimeResponse response;
        if (request.getTrip().getSourceName().equals("Amir")) {
            response = new MMTime.TimeResponse(request.getTrip(), request.getResponseContainer(), request.getCancelContainer(), false, request.getLoadingContainer());
        } else {
            response = new MMTime.TimeResponse(request.getTrip(), request.getResponseContainer(), request.getCancelContainer(), true, request.getLoadingContainer());
        }
        postEvent(response);
    }

    @Subscribe
    public void onServicesRequestReceived(Services.ServicesRequest request) {
        ArrayList<ManamMiamService> services = new ArrayList<>();

        services.add(new ManamMiamService(1, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(25468, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(3213, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(487985, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(5578, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(654, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(5421, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));

        Services.ServicesResponse response = new Services.ServicesResponse(services);

        postEvent(response);
    }

    @Subscribe
    public void onRateRequestReceived(Rate.RateRequest request) {
        Rate.RateResponse response = new Rate.RateResponse(request.getPassengerTrip(), request.getLoading(), request.getResponseContainer());

        postEvent(response);
    }

    @Subscribe
    public void onLocationRequestReceived(Location.LocationRequest request) {

        ArrayList<ManamMiamLocation> locations = new ArrayList<>();

        locations.add(new ManamMiamLocation("Pardis", "Jahrom - Khalij-fars blv.", 500));
        locations.add(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 50));
        locations.add(new ManamMiamLocation("Self", "Jahrom - Sepah blv.", 5002));
        locations.add(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 50));

        Location.LocationResponse response = new Location.LocationResponse(locations);

        postEvent(response);
    }

    
    @Subscribe
    public void onSpecificServicesRequestReceived(Services.ServicesSpecificRequest request) {

        ArrayList<ManamMiamService> services = new ArrayList<>();

        services.add(new ManamMiamService(25468, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(3213, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(487985, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
        services.add(new ManamMiamService(5578, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));

        Services.ServicesSpecificResponse response = new Services.ServicesSpecificResponse(services);
        postEvent(response);
    }

    @Subscribe
    public void onAddCarRequestReceived(Account.AddCarRequest request) {

        Account.AddCarResponse response = new Account.AddCarResponse(Account.AddCarResponse.SUCCESSFUL);

        postEvent(response);
    }

    @Subscribe
    public void onReportRequestReceived(Services.ReportServiceRequest request) {
        Services.ReportServiceResponse response = new Services.ReportServiceResponse(Services.ReportServiceResponse.SUCCESSFUL);
        postEvent(response);
    }

    @Subscribe
    public void onCancelTripRequestReceived(Trips.CancelRequest request) {
        if (request.getTrip() instanceof PassengerTrip) {
            //cancel passenger
        } else if (request.getTrip() instanceof DriverTrip) {
            //cancel server
        }

        Trips.CancelResponse response = new Trips.CancelResponse(request.getLoading(), request.getResponseContainer(), Trips.CancelResponse.SUCCESSFUL, request.getTrip());
        postEvent(response);
    }

    @Subscribe
    public void onServiceReserveRequestReceived(Services.ReserveRequest request) {
        Services.ReserveResponse response = new Services.ReserveResponse(Services.ReserveResponse.SUCCESSFUL, request.getLoading(), request.getService());
        postEvent(response);
    }

    @Subscribe
    public void onEnrollRequestReceived(Account.EnrollRequest request) {
        Account.EnrollResponse response = new Account.EnrollResponse(23053);
        postEvent(response);
    }

    @Subscribe
    public void onCreateTripRequestReceived(Trips.CreateRequest request) {
        Trips.CreateResponse response = new Trips.CreateResponse(Trips.CreateResponse.SUCCESSFUL);
        postEvent(response);
    }

    @Subscribe
    public void onAddServerRequestReceived(Services.AddServicesRequest request) {

        if (request.getPassengerId() == 0) {
            //create server with no notification
        } else {
            //create server with notification
        }

        Services.AddServicesResponse response = new Services.AddServicesResponse(Services.AddServicesResponse.SUCCESSFUL);
        postEvent(response);
    }

    @Subscribe
    public void onLogoutRequestReceieved(Account.LogoutRequest request) {
        Account.LogoutResponse response = new Account.LogoutResponse(Account.LogoutResponse.SUCCESSFUL);
        postEvent(response);
    }

    @Subscribe
    public void onJoinServiceRequestReceieved(Posts.AcceptRequest request) {
        request.getServerId();
        request.getToken();
        //reserve server method on php

        Posts.AcceptResponse response = new Posts.AcceptResponse(Posts.AcceptResponse.SUCCESSFUL, request.getLoading(), request.getServerId());
        postEvent(response);
    }


    @Subscribe
    public void getTrips(Trips.TripRequest request) {
        request.getToken();

        ArrayList<Trip> trips = new ArrayList<>();

        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", null, null, null, 5200, null, null, 26910));
        trips.add(new PassengerTrip("1396/09/29 20:30", "Amir", "h", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
        trips.add(new DriverTrip("1396/09/29 20:30", "LA", "NY", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "8000", 5, 3, 2500));
        trips.add(new DriverTrip("1396/09/29 20:30", "Amir", "NY", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "8000", 10, 1, 2500));
        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", null, null, null, 5200, null, null, 26910));
        trips.add(new DriverTrip("1396/09/29 20:30", "Texas", "NY", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "8000", 3, 2, 2500));
        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));

        postEvent(new Trips.TripResponse(trips));
    }

}

