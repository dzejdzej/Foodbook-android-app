package com.robpercival.demoapp.rest;

import com.robpercival.demoapp.rest.dto.CommentDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentsAPI {

    @GET("reviews/{restaurantId}")
    Call<List<CommentDto>> getCommentsForRestaurant(@Path("restaurantId")long restaurantId);
}
