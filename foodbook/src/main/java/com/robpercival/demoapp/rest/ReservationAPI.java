package com.robpercival.demoapp.rest;

import com.robpercival.demoapp.rest.dto.InviteFriendsDTO;
import com.robpercival.demoapp.rest.dto.ReservationDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by User on 5/27/2018.
 */

public interface ReservationAPI {

    @POST("restaurant/available-restaurants-for-reservation")
    Call<List<ReservationResponseDTO>> getAvailableRestaurants(@Body ReservationRequestDTO dto);

    @POST("reservation/make-reservation")
    Call<CreatedReservationDTO> reserve(@Body ReservationRequestDTO dto);

    @POST("reservation/finish-reservation")
    Call<InviteFriendsDTO> finishReservation(@Body InviteFriendsDTO dto);

    @GET("reservation/get-all-reservations/user/{userId}")
    Call<List<ReservationDTO>> getAllReservationsForUser(@Path("userId")long userId);

    @GET("reservation/cancel-reservation/user/{userId}/reservation/{reservationId}")
    Call<List<ReservationDTO>> cancelReservation(@Path("userId") long userId, @Path("reservationId") long reservationId);
}
