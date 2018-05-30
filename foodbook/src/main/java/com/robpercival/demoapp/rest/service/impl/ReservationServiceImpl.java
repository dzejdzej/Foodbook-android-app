package com.robpercival.demoapp.rest.service.impl;

import com.robpercival.demoapp.rest.ReservationAPI;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.ReserveDTO;
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
    public void reserve(ReservationRequestDTO reservationRequest, long restaurantId, final ServiceCallback<ReserveDTO> presenterCallback) {
        Call<ReserveDTO> apiCall = reservationAPI.reserve(reservationRequest, restaurantId);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<ReserveDTO>() {

            @Override
            public void onResponse(Call<ReserveDTO> call, Response<ReserveDTO> response) {
                ReserveDTO body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<ReserveDTO> call, Throwable t) {

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
