package com.robpercival.demoapp.rest.service.impl;

import com.robpercival.demoapp.rest.CommentsAPI;
import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.dto.RatingDTO;
import com.robpercival.demoapp.rest.service.CommentService;
import com.robpercival.demoapp.rest.service.RatingService;
import com.robpercival.demoapp.rest.service.RatingsAPI;
import com.robpercival.demoapp.rest.service.ServiceCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingServiceImpl extends BaseServiceImpl implements RatingService {

    private RatingsAPI ratingsAPI;

    private static RatingService instance;

    private RatingServiceImpl() {
        super();
        buildHttpClient();
    }

    public static RatingService getInstance() {
        if (instance == null) {
            instance = new RatingServiceImpl();
        }
        return instance;
    }

    private void buildHttpClient() {
        ratingsAPI = retrofit.create(RatingsAPI.class);
    }


    @Override
    public void rateRestaurant(RatingDTO ratingDTO, final ServiceCallback<Double> presenterCallback) {
        Call<Double> apiCall = ratingsAPI.rateRestaurant(ratingDTO);
        apiCall.enqueue(new Callback<Double>() {

            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Double body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
            }
        });
    }

    @Override
    public void getRatingForRestaurant(long restaurantId, final ServiceCallback<Double> presenterCallback) {
        Call<Double> apiCall = ratingsAPI.getRatingForRestaurant(restaurantId);
        apiCall.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Double body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }
}
