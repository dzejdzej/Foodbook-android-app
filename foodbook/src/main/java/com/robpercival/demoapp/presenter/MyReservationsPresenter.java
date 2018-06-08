package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.ReservationDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

import java.util.List;

/**
 * Created by User on 6/3/2018.
 */

public class MyReservationsPresenter {

    private final MyReservationsPresenter.MyReservationsView view;


    public interface MyReservationsView {

        void onGetReservationsFail(String s);

        void onGetReservationsSuccess();

        void onPopulateReservations(List<ReservationDTO> reservations);

        void onCancelReservation(List<ReservationDTO> reservations);

    };

    ReservationService reservationService = ReservationServiceImpl.getInstance();

    public MyReservationsPresenter(MyReservationsPresenter.MyReservationsView view) {
        this.view = view;

        //getCountriesAndCities();
    }

    public void getAllReservationsForUser() {

        UserDTO dto = (UserDTO) ApplicationState.getInstance().getItem("UserDTO");

        long userId = dto.getUserId();

        reservationService.getAllReservationsForUser(userId, new ServiceCallback<List<ReservationDTO>>() {
            @Override
            public void onSuccess(List<ReservationDTO> body) {

                view.onPopulateReservations(body);
            }

            @Override
            public void onError(List<ReservationDTO> body) {
                view.onGetReservationsFail("");
            }
        });

    }

    public void onCancelClicked(ReservationDTO dto) {

        UserDTO userDto = (UserDTO) ApplicationState.getInstance().getItem("UserDTO");

        long userId = userDto.getUserId();

        reservationService.cancelReservation(userId, dto.getId(), new ServiceCallback<List<ReservationDTO>>() {

            @Override
            public void onSuccess(List<ReservationDTO> body) {
                view.onCancelReservation(body);
            }

            @Override
            public void onError(List<ReservationDTO> body) {
            }
        });
    }


}
