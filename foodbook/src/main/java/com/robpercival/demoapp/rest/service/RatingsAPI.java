package com.robpercival.demoapp.rest.service;

import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.dto.RatingDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RatingsAPI {

    @POST("ratings/rate")
    Call<Double> rateRestaurant(@Body RatingDTO ratingDTO);

    @GET("ratings/{restaurantId}")
    Call<Double> getRatingForRestaurant(@Path("restaurantId")long restaurantId);
}
