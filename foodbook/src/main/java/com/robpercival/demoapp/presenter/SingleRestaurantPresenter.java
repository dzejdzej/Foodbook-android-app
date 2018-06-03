package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

/**
 * Created by User on 5/29/2018.
 */

public class SingleRestaurantPresenter {

    private final SingleRestaurantPresenter.SingleRestaurantView view;

    public interface SingleRestaurantView {

        void onReservationFail(String s);
        void onReservationSuccess(long reservationId);
    };

    ReservationService reservationService = ReservationServiceImpl.getInstance();

    public SingleRestaurantPresenter(SingleRestaurantPresenter.SingleRestaurantView view) {
        this.view = view;

        //getCountriesAndCities();
    }


    public void onReservationClick(ReservationRequestDTO reservationRequest, long restaurantId) {

        UserDTO dto = (UserDTO) ApplicationState.getInstance().getItem("UserDTO");

        long userId = dto.getUserId();
        reservationRequest.setUserId(userId);
        reservationRequest.setRestaurantId(restaurantId);
        reservationService.reserve(reservationRequest, restaurantId, new ServiceCallback<CreatedReservationDTO>() {
            @Override
            public void onSuccess(CreatedReservationDTO body) {

                if(body.getReservationId() == -1)
                    view.onReservationFail("Not enough seats!");
                else
                    view.onReservationSuccess(body.getReservationId());
            }

            @Override
            public void onError(CreatedReservationDTO body) {
                view.onReservationFail("");
            }
        });

    }








}

