package com.amir.manammiam.services;

import android.util.Log;

import com.amir.manammiam.R;
import com.amir.manammiam.base.ManamMiamApplication;
import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.Database;
import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.trip.DriverTrip;
import com.amir.manammiam.infrastructure.trip.PassengerTrip;
import com.amir.manammiam.infrastructure.trip.TimePOJO;
import com.amir.manammiam.infrastructure.trip.Trip;
import com.amir.manammiam.infrastructure.trip.TripPOJO;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


class LiveManamMiamServices {
    private static final String TAG = "LiveManamMiamServices";
    private final Bus bus;
    private final ManamMiamWebServices services;
    private final Database database;
    private final ManamMiamApplication application;

    public LiveManamMiamServices(ManamMiamApplication application) {
        this.bus = application.getBus();
        bus.register(this);
        database = application.getDatabase();
        services = application.getRetrofit().create(ManamMiamWebServices.class);
        this.application = application;
//        database.invalidate();
    }


    @Subscribe
    public void login(Account.LoginRequest request) {

//        Log.e(TAG, "login: " + request.getPassword() + " " + request.getUsername() + " " + request.getSourceType());
//
//        database.insertUserWithOnlyToken("RANDOM BULLSHITS");
//
//        Log.e(TAG, "login: " + database.getUser().getToken());


        services.login(Constants.LOGIN_USER, request.getUsername(), request.getPassword(), request.getSourceType())
                .enqueue(new RetrofitCallback<Account.LoginResponse>(bus) {
                    @Override
                    protected void onResolve(Account.LoginResponse body) {
                        database.invalidate();
                        if (body.getToken() == null) {
                            Log.e(TAG, "onResolve: token was null");
                            body.setOperationError(application.getString(R.string.wrong_password_or_username_or_missing_permission));
                        } else {
                            Log.e(TAG, "onResolve: " + body.getToken());
                            database.insertUserWithOnlyToken(body.getToken());
                        }

                        bus.post(body);
                    }
                });

    }

    @Subscribe
    public void getCars(Account.CarsRequest request) {

        services.getCars(Constants.GET_PERSON_S_CAR, request.getToken())
                .enqueue(new RetrofitCallback<List<Car>>(bus) {
                    @Override
                    protected void onResolve(List<Car> body) {

                        if (body != null) {
                            bus.post(new Account.CarsResponse((ArrayList<Car>) body));
                        } else {

                        }

                    }
                });
//                .enqueue(new RetrofitCallback<Account.LoginResponse>(bus) {
//                    @Override
//                    protected void onResolve(Account.LoginResponse body) {
//                        database.invalidate();
//                        if (body.getToken() == null) {
//                            Log.e(TAG, "onResolve: token was null");
//                            body.setOperationError(application.getString(R.string.wrong_password_or_username_or_missing_permission));
//                        } else {
//                            Log.e(TAG, "onResolve: " + body.getToken());
//                            database.insertUserWithOnlyToken(body.getToken());
//                        }
//
//                        bus.post(body);
//                    }
//                });

//        request.getToken();
//
//        ArrayList<Car> cars = new ArrayList<>();
//        cars.add(new Car("Aventador", "Orange", "123 45 P", 4.5f, Car.ACCEPT_ALL, 120, false, 45));
//        cars.add(new Car("Aventador", "Orange", "123 45 P", 4.5f, Car.FEMALE, 120, false, 45));
//        cars.add(new Car("Aventador", "Orange", "123 45 P", 4.5f, Car.MALE, 120, false, 45));
//        cars.add(new Car("GTR", "Dark Blue", "123 45 P", 5, Car.NO_VERIFIED_CAR, 520, false, 45));
//        cars.add(new Car("One:1", "White", "123 45 P", 2, Car.BLOCKED, 10, false, 45));
//
//        Account.CarsResponse response = new Account.CarsResponse(cars);
//        postEvent(response);
    }


    //this is basically login_with_token
    @Subscribe
    public void getProfile(Account.ProfileRequest request) {

        Log.e(TAG, "getProfile: " + request.getToken());
        services.authenticate(Constants.LOGIN_WITH_TOKEN, request.getToken())
                .enqueue(new RetrofitCallback<Account.ProfileResponse>(bus) {
                    @Override
                    protected void onResolve(Account.ProfileResponse body) {
                        if (body.getName() == null) {
                            body.setOperationError("invalid Token");
                            database.invalidate();
                        } else {
                            database.updateUser(body);
                        }
                        bus.post(body);
                    }
                });
    }

    @Subscribe
    public void getPosts(Posts.PostsRequest request) {

        services.getPosts(Constants.GET_POSTS, request.token)
                .enqueue(new RetrofitCallback<List<ManamMiamPost>>(bus) {
                    @Override
                    protected void onResolve(List<ManamMiamPost> body) {
                        bus.post(new Posts.PostResponse((ArrayList<ManamMiamPost>) body));
                    }
                });
//        request.getToken();
//
//        ArrayList<ManamMiamPost> posts = new ArrayList<>();
//
//        //TODO: remember that if (!user.is_driver()) you can't have a ManamMiamPost.LOOKING_FOR_SERVER added to the post..
//        //TODO: this happens in client side and not the server side
//
//        Car car = new Car("Pride", "pale Blue", "125 a 93", 2.5f, Car.ACCEPT_ALL, 96, false, 6546);
//        posts.add(new ManamMiamPost(false, ManamMiamPost.PASSENGER_ACCEPTED_A_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "i joined your service", "1396/09/29 20:30", 1, null, 0, 0));
//
//        posts.add(new ManamMiamPost(false, ManamMiamPost.DRIVER_ASKING_PASSENGER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "Wanna come with", "1396/09/29 20:30", 3, car, 56431, 5312));
//
//        posts.add(new ManamMiamPost(false, ManamMiamPost.LOOKING_FOR_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), null, "Amir", "I'm going from there to there", "1396/09/29 20:30", -1,  null, 65421, 0));
//
//        posts.add(new ManamMiamPost(true, ManamMiamPost.PASSENGER_ACCEPTED_A_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "i joined your service", "1396/09/29 20:30", 1, null, 0, 0));
//
//        posts.add(new ManamMiamPost(true, ManamMiamPost.DRIVER_ASKING_PASSENGER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), "5000", "Amir", "Wanna come with", "1396/09/29 20:30", 3, car, 654564, 6544));
//
//        posts.add(new ManamMiamPost(true, ManamMiamPost.LOOKING_FOR_SERVER, new ManamMiamLocation("سپاه","جهرم - سپاه", 654), new ManamMiamLocation( "پردیس", "جهرم - خلیج‌فارس - پردیس", 5465), null, "Amir", "from here to there i am going", "1396/09/29 20:30", -1, null, 65421, 0));
//
//        postEvent(new Posts.PostResponse(posts));
    }

    @Subscribe
    public void onTimeRequestReceived(final MMTime.Request request) {

        services.isInTheFuture(Constants.IS_IN_THE_FUTURE, request.getTime())
                .enqueue(new RetrofitCallback<TimePOJO>(bus) {
                    @Override
                    protected void onResolve(TimePOJO body) {
                        bus.post(new MMTime.TimeResponse(request.getTrip(), request.getResponseContainer(), request.getCancelContainer(), body.isInTheFuture, request.getLoadingContainer()));
                    }
                });
//        MMTime.TimeResponse response;
//        if (request.getTrip().getSourceName().equals("Amir")) {
//            response = new MMTime.TimeResponse(request.getTrip(), request.getResponseContainer(), request.getCancelContainer(), false, request.getLoadingContainer());
//        } else {
//            response = new MMTime.TimeResponse(request.getTrip(), request.getResponseContainer(), request.getCancelContainer(), true, request.getLoadingContainer());
//        }
//        postEvent(response);
    }

    @Subscribe
    public void onServicesRequestReceived(Services.ServicesRequest request) {

        services.getServices(Constants.GET_SERVERS, request.getToken(), request.getGender())
                .enqueue(new RetrofitCallback<List<ManamMiamService>>(bus) {
                    @Override
                    protected void onResolve(List<ManamMiamService> body) {
                        bus.post(new Services.ServicesResponse((ArrayList<ManamMiamService>) body));
                    }
                });
//        ArrayList<ManamMiamService> services = new ArrayList<>();
//
//        services.add(new ManamMiamService(1, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(25468, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(3213, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(487985, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(5578, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(654, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(5421, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//
//        Services.ServicesResponse response = new Services.ServicesResponse(services);
//
//        postEvent(response);
    }

    @Subscribe
    public void onRateRequestReceived(Rate.RateRequest request) {
//        Rate.RateResponse response = new Rate.RateResponse(request.getPassengerTrip(), request.getLoading(), request.getResponseContainer());
//
//        postEvent(response);
    }

    @Subscribe
    public void onLocationRequestReceived(Location.LocationRequest request) {
//
        services.searchLocations(Constants.SEARCH_LOCATIONS, request.getSequence())
                .enqueue(new RetrofitCallback<List<ManamMiamLocation>>(bus) {
                    @Override
                    protected void onResolve(List<ManamMiamLocation> body) {
                        bus.post(new Location.LocationResponse((ArrayList<ManamMiamLocation>) body));
                    }
                });
//        ArrayList<ManamMiamLocation> locations = new ArrayList<>();
//
//        locations.add(new ManamMiamLocation("Pardis", "Jahrom - Khalij-fars blv.", 500));
//        locations.add(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 50));
//        locations.add(new ManamMiamLocation("Self", "Jahrom - Sepah blv.", 5002));
//        locations.add(new ManamMiamLocation("Sepah Blv.", "Jahrom - Sepah blv.", 50));
//
//        Location.LocationResponse response = new Location.LocationResponse(locations);
//
//        postEvent(response);
    }


    @Subscribe
    public void onSpecificServicesRequestReceived(Services.ServicesSpecificRequest request) {

        services.getServerSpecific(Constants.GET_SERVER_SPECIFIC, request.getGender(), request.getDestinationId(), request.getSourceId())
                .enqueue(new RetrofitCallback<List<ManamMiamService>>(bus) {
                    @Override
                    protected void onResolve(List<ManamMiamService> body) {
                        bus.post(new Services.ServicesSpecificResponse((ArrayList<ManamMiamService>) body));
                    }
                });
//
//        ArrayList<ManamMiamService> services = new ArrayList<>();
//
//        services.add(new ManamMiamService(25468, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(3213, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(487985, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//        services.add(new ManamMiamService(5578, 100025, "Sepah", new Car("peikan", "yellow", "230 h 23", 5, Car.UNKNOWN, 123, false, 5), "hell", "free", 4, "1396/09/19 20:30", "Masoud"));
//
//        Services.ServicesSpecificResponse response = new Services.ServicesSpecificResponse(services);
//        postEvent(response);
    }

    @Subscribe
    public void onAddCarRequestReceived(Account.AddCarRequest request) {

        services.addCar(Constants.ADD_A_CAR, request.getCarType(), request.getCarCode(), request.getCarColor(), request.isTaxi(), request.getToken())
                .enqueue(new RetrofitCallback<Account.AddCarResponse>(bus) {
                    @Override
                    protected void onResolve(Account.AddCarResponse body) {
                        bus.post(body);
                    }
                });


//
//        Account.AddCarResponse response = new Account.AddCarResponse(Account.AddCarResponse.SUCCESSFUL);
//
//        postEvent(response);
    }

    @Subscribe
    public void onReportRequestReceived(Services.ReportServiceRequest request) {
//        Services.ReportServiceResponse response = new Services.ReportServiceResponse(Services.ReportServiceResponse.SUCCESSFUL);
//        postEvent(response);
    }

    @Subscribe
    public void onCancelTripRequestReceived(Trips.CancelRequest request) {
//        if (request.getTrip() instanceof PassengerTrip) {
//            //cancel passenger
//        } else if (request.getTrip() instanceof DriverTrip) {
//            //cancel server
//        }
//
//        Trips.CancelResponse response = new Trips.CancelResponse(request.getLoading(), request.getResponseContainer(), Trips.CancelResponse.SUCCESSFUL, request.getTrip());
//        postEvent(response);
    }

    @Subscribe
    public void onServiceReserveRequestReceived(final Services.ReserveRequest request) {

        services.reserveServer(Constants.RESERVE_SERVER, request.getToken(), request.getService().getServerId())
                .enqueue(new RetrofitCallback<Services.ReserveResponsePOJO>(bus) {
                    @Override
                    protected void onResolve(Services.ReserveResponsePOJO body) {
                        Services.ReserveResponse res = new Services.ReserveResponse(body.result);
                        res.setService(request.getService());
                        res.setLoading(request.getLoading());
                        bus.post(res);
                    }
                });

//        Services.ReserveResponse response = new Services.ReserveResponse(Services.ReserveResponse.SUCCESSFUL, request.getLoading(), request.getService());
//        postEvent(response);
    }

    @Subscribe
    public void onEnrollRequestReceived(Account.EnrollRequest request) {
//        Account.EnrollResponse response = new Account.EnrollResponse(23053);
//        postEvent(response);
    }

    @Subscribe
    public void onCreateTripRequestReceived(Trips.CreateRequest request) {

        Log.e(TAG, "onCreateTripRequestReceived: " + String.format("%d %s %s %s %s", Constants.ADD_PASSENGER, request.getToken(), request.getDestinationId(), request.getSourceId(), request.getTime()));

        services.createTrip(Constants.ADD_PASSENGER, request.getToken(), request.getDestinationId(), request.getSourceId(), request.getTime())
                .enqueue(new RetrofitCallback<Trips.CreateResponse>(bus) {
                    @Override
                    protected void onResolve(Trips.CreateResponse body) {
                        Log.e(TAG, "------onResolve: " + body.getResult());
                        bus.post(body);
                    }
                });

//        Trips.CreateResponse response = new Trips.CreateResponse(Trips.CreateResponse.SUCCESSFUL);
//        postEvent(response);
    }

    @Subscribe
    public void onAddServerRequestReceived(Services.AddServicesRequest request) {

        RetrofitCallback<Services.AddServicesResponse> addServiceCallback = new RetrofitCallback<Services.AddServicesResponse>(bus) {
            @Override
            protected void onResolve(Services.AddServicesResponse body) {
                switch (body.getResult()) {
                }
            }
        };

        if (request.getPassengerId() == 0) {
            //create server with no notification
            services.addServiceNoNotification(
                    Constants.ADD_SERVER_WITH_NO_NOTIFICATION,
                    request.getToken(),
                    request.getDestinationId(),
                    request.getSourceId(),
                    request.getCarId(),
                    request.getTime(),
                    String.valueOf(request.getPrice()),
                    request.getCapacity())

                    .enqueue(addServiceCallback);
        } else {
            //create server with notification
            services.addServiceWithNotification(
                    Constants.ADD_SERVER_WITH_NO_NOTIFICATION,
                    request.getToken(),
                    request.getDestinationId(),
                    request.getSourceId(),
                    request.getCarId(),
                    request.getPassengerId(),
                    request.getTime(),
                    String.valueOf(request.getPrice()),
                    request.getCapacity())

                    .enqueue(addServiceCallback);
        }
//
//        Services.AddServicesResponse response = new Services.AddServicesResponse(Services.AddServicesResponse.SUCCESSFUL);
//        postEvent(response);
    }

    @Subscribe
    public void onLogoutRequestReceieved(Account.LogoutRequest request) {

        services.logout(Constants.LOGOUT, request.getToken())
                .enqueue(new RetrofitCallback<Account.LogoutResponse>(bus) {
                    @Override
                    protected void onResolve(Account.LogoutResponse body) {
                        bus.post(body);
                    }
                });

//        Account.LogoutResponse response = new Account.LogoutResponse(Account.LogoutResponse.SUCCESSFUL);
//        postEvent(response);
    }

    @Subscribe
    public void onJoinServiceRequestReceieved(Posts.AcceptRequest request) {
//        request.getServerId();
//        request.getToken();
//        //reserve server method on php
//
//        Posts.AcceptResponse response = new Posts.AcceptResponse(Posts.AcceptResponse.SUCCESSFUL, request.getLoading(), request.getServerId());
//        postEvent(response);
    }


    @Subscribe
    public void getTrips(Trips.TripRequest request) {

        services.getTrips(Constants.GET_TRIPS, request.token)
                .enqueue(new RetrofitCallback<List<TripPOJO>>(bus) {
                    @Override
                    protected void onResolve(List<TripPOJO> body) {
                        ArrayList<Trip> trips = new ArrayList<>(body.size());
                        for (TripPOJO tripPojo : body) {
                            if (tripPojo.getCar() != null) {

                                if (tripPojo.getDriverPhoneNumber() == null) {
                                    //this is shown to the driver... obviously the driver doesn't need to know his own phone number
                                    trips.add(new DriverTrip(tripPojo.getTime(), tripPojo.getSourceName(), tripPojo.getDestinationName(), tripPojo.getCar(), tripPojo.getPrice(), tripPojo.getCapacity(), tripPojo.getNumberOfPassenger(), tripPojo.getServerID()));
                                } else {
                                    //this is shown to the passenger
                                    trips.add(new PassengerTrip(tripPojo.getTime(), tripPojo.getSourceName(), tripPojo.getDestinationName(), tripPojo.getCar(), tripPojo.getPrice(), tripPojo.getDriverName(), tripPojo.getServerID(), tripPojo.getDriverTelegramId(), tripPojo.getDriverPhoneNumber(), tripPojo.getPassengerId()));
                                }

                            } else {
                                trips.add(new Trip(tripPojo.getTime(), tripPojo.getSourceName(), tripPojo.getDestinationName(), tripPojo.getCar(), tripPojo.getPrice(), tripPojo.getServerID()));
                            }
                        }

                        bus.post(new Trips.TripResponse(trips));
                    }
                });

//        request.getToken();
//
//        ArrayList<Trip> trips = new ArrayList<>();
//
//        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
//        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", null, null, null, 5200, null, null, 26910));
//        trips.add(new PassengerTrip("1396/09/29 20:30", "Amir", "h", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
//        trips.add(new DriverTrip("1396/09/29 20:30", "LA", "NY", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "8000", 5, 3, 2500));
//        trips.add(new DriverTrip("1396/09/29 20:30", "Amir", "NY", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "8000", 10, 1, 2500));
//        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", null, null, null, 5200, null, null, 26910));
//        trips.add(new DriverTrip("1396/09/29 20:30", "Texas", "NY", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "8000", 3, 2, 2500));
//        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
//        trips.add(new PassengerTrip("1396/09/29 20:30", "پردیس", "سپاه", new Car("Pride", "blue", "۷۸ ح ۳۴۵ ۳۴", 3.6f, Car.UNKNOWN, 15, false, 4), "1Million", "AlexisTexas", 5200, "amir5121", "+989178947604", 26910));
//
//        postEvent(new Trips.TripResponse(trips));
    }

}
