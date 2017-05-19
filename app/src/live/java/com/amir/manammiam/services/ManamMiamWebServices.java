package com.amir.manammiam.services;


import com.amir.manammiam.infrastructure.Constants;
import com.amir.manammiam.infrastructure.car.Car;
import com.amir.manammiam.infrastructure.location.ManamMiamLocation;
import com.amir.manammiam.infrastructure.post.ManamMiamPost;
import com.amir.manammiam.infrastructure.services.ManamMiamService;
import com.amir.manammiam.infrastructure.trip.TimePOJO;
import com.amir.manammiam.infrastructure.trip.TripPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ManamMiamWebServices {

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.LoginResponse> login(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("username") String username,
            @Field("pass") String password,
            @Field("source_type") int source_type);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.ProfileResponse> authenticate(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.ProfileResponse> enroll(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.LogoutResponse> logout(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token);


    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<List<Car>> getCars(@Field(Constants.ACTION_TYPE) Integer actionType,
                            @Field("token") String token);


    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<List<ManamMiamPost>> getPosts(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<TimePOJO> isInTheFuture(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("date") String time);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<List<ManamMiamService>> getServices(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token,
            @Field("gender") int gender);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<List<TripPOJO>> getTrips(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<List<ManamMiamLocation>> searchLocations(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("sequence") String sequence);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<List<ManamMiamService>> getServerSpecific(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("gender") int gender,
            @Field("destination_id") long destinationId,
            @Field("source_id") long sourceId);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.AddCarResponse> addCar(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("car_type") String carType, @Field("car_code") String CoreCode,
            @Field("car_color") String carColor,
            @Field("is_taxi") boolean isTaxi,
            @Field("token") String token);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Services.ReserveResponsePOJO> reserveServer(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token,
            @Field("server_id") long serverId);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Trips.CreateResponse> createTrip(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token, @Field("destination_id") long destinationId,
            @Field("source_id") long sourceId,
            @Field("date") String date);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Services.AddServicesResponse> addServiceNoNotification(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token,
            @Field("destination_id") long destinationId,
            @Field("source_id") long sourceId,
            @Field("car_id") long carId,
            @Field("date") String date,
            @Field("price") String price,
            @Field("capacity") Integer capacity);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Services.AddServicesResponse> addServiceWithNotification(
            @Field(Constants.ACTION_TYPE) Integer actionType,
            @Field("token") String token,
            @Field("destination_id") long destinationId,
            @Field("source_id") long sourceId,
            @Field("car_id") long carId,
            @Field("passenger_id") long passengerId,
            @Field("date") String date,
            @Field("price") String price,
            @Field("capacity") Integer capacity);

}
