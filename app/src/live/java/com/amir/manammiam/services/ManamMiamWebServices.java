package com.amir.manammiam.services;


import com.amir.manammiam.infrastructure.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ManamMiamWebServices {

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.LoginResponse> login(@Field(Constants.ACTION_TYPE) Integer actionType,
                                      @Field("username") String username,
                                      @Field("pass") String password,
                                      @Field("source_type") int source_type);

    @POST(Constants.CONCAT_URL)
    @FormUrlEncoded
    Call<Account.ProfileResponse> authenticate(@Field(Constants.ACTION_TYPE) Integer actionType, @Field("token") String token);

}
