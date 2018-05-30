package com.robpercival.demoapp.rest;

import com.robpercival.demoapp.rest.dto.user.LoginDTO;
import com.robpercival.demoapp.rest.dto.user.RegisterDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.ReserveDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by User on 5/27/2018.
 */

public interface ReservationAPI {

    @POST("user/available-restaurants-for-reservation")
    Call<List<ReservationResponseDTO>> getAvailableRestaurants(@Body ReservationRequestDTO dto);

    @POST("user/make-reservation")
    Call<ReserveDTO> reserve(@Body ReservationRequestDTO dto, long restaurantId);
}
