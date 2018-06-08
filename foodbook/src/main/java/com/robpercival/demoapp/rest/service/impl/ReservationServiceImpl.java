package com.robpercival.demoapp.rest.service.impl;

import com.robpercival.demoapp.rest.ReservationAPI;
import com.robpercival.demoapp.rest.dto.InviteFriendsDTO;
import com.robpercival.demoapp.rest.dto.ReservationDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 5/27/2018.
 */

public class ReservationServiceImpl extends BaseServiceImpl implements ReservationService {


    private ReservationAPI reservationAPI;

    private static ReservationService instance;


    private ReservationServiceImpl() {
        super();
        buildHttpClient();

    }

    private void buildHttpClient() {
        reservationAPI = retrofit.create(ReservationAPI.class);
    }


    @Override
    public void getAvailableRestaurants(ReservationRequestDTO dto, final ServiceCallback<List<ReservationResponseDTO>> presenterCallback) {

        Call<List<ReservationResponseDTO>> apiCall = reservationAPI.getAvailableRestaurants(dto);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<List<ReservationResponseDTO>>() {

            @Override
            public void onResponse(Call<List<ReservationResponseDTO>> call, Response<List<ReservationResponseDTO>> response) {
                List<ReservationResponseDTO> body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<List<ReservationResponseDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void reserve(ReservationRequestDTO reservationRequest, long restaurantId, final ServiceCallback<CreatedReservationDTO> presenterCallback) {
        Call<CreatedReservationDTO> apiCall = reservationAPI.reserve(reservationRequest);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<CreatedReservationDTO>() {

            @Override
            public void onResponse(Call<CreatedReservationDTO> call, Response<CreatedReservationDTO> response) {
                CreatedReservationDTO body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<CreatedReservationDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void finishReservation(InviteFriendsDTO dto, final ServiceCallback<InviteFriendsDTO> presenterCallback) {
        Call<InviteFriendsDTO> apiCall = reservationAPI.finishReservation(dto);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<InviteFriendsDTO>() {

            @Override
            public void onResponse(Call<InviteFriendsDTO> call, Response<InviteFriendsDTO> response) {
                InviteFriendsDTO body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<InviteFriendsDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void getAllReservationsForUser(long userId, final ServiceCallback<List<ReservationDTO>> presenterCallback) {
        Call<List<ReservationDTO>> apiCall = reservationAPI.getAllReservationsForUser(userId);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<List<ReservationDTO>>() {

            @Override
            public void onResponse(Call<List<ReservationDTO>> call, Response<List<ReservationDTO>> response) {
                List<ReservationDTO> body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<List<ReservationDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void cancelReservation(long userId, long reservationId, final ServiceCallback<List<ReservationDTO>> presenterCallback) {

        Call<List<ReservationDTO>> apiCall = reservationAPI.cancelReservation(userId, reservationId);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<List<ReservationDTO>>() {

            @Override
            public void onResponse(Call<List<ReservationDTO>> call, Response<List<ReservationDTO>> response) {
                List<ReservationDTO> body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<List<ReservationDTO>> call, Throwable t) {
            }
        });

    }

    public static ReservationService getInstance() {
        if(instance == null) {
            instance = new ReservationServiceImpl();
        }
        return instance;
    }
}
