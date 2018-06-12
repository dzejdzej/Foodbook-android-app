package com.robpercival.demoapp.rest.service;

import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.dto.RatingDTO;

import java.util.List;

public interface RatingService {

    // void getAllCommentsForRestaurant(long restaurantId, final ServiceCallback<List<CommentDto>> presenterCallback);

    void rateRestaurant(RatingDTO ratingDTO, final ServiceCallback<Double> presenterCallback);

    void getRatingForRestaurant(long restaurantId, final ServiceCallback<Double> presenterCallback);
}
