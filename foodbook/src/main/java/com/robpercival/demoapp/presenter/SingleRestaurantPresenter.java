package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReserveDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;

/**
 * Created by User on 5/29/2018.
 */

public class SingleRestaurantPresenter {

    private final SingleRestaurantPresenter.SingleRestaurantView view;

    public interface SingleRestaurantView {

        void onReservationFail();
        void onReservationSuccess();
    };

    ReservationService reservationService = ReservationServiceImpl.getInstance();

    public SingleRestaurantPresenter(SingleRestaurantPresenter.SingleRestaurantView view) {
        this.view = view;

        //getCountriesAndCities();
    }


    public void onReservationClick(ReservationRequestDTO reservationRequest, long restaurantId) {

        reservationService.reserve(reservationRequest, restaurantId, new ServiceCallback<ReserveDTO>() {
            @Override
            public void onSuccess(ReserveDTO body) {
                view.onReservationSuccess();
            }

            @Override
            public void onError(ReserveDTO body) {
                view.onReservationFail();
            }
        });

    }








}

