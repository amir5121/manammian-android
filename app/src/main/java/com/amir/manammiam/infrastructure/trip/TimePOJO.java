package com.amir.manammiam.infrastructure.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimePOJO {

    @Expose
    @SerializedName("is_in_the_future")
    public boolean isInTheFuture;

}
