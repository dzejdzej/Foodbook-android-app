package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.InviteFriendsDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

import java.util.List;

public class ConfirmCancelAttendancePresenter {

    private final ConfirmCancelAttendancePresenter.ConfirmCancelAttendanceView view;


    public interface ConfirmCancelAttendanceView {
        void onConfirmSuccess();
        void onCancelSuccess();
    };

    ReservationService reservationService = ReservationServiceImpl.getInstance();

    public ConfirmCancelAttendancePresenter(ConfirmCancelAttendancePresenter.ConfirmCancelAttendanceView view) {
        this.view = view;
    }

    public void onConfirmAttendance(long reservationId) {

        UserDTO user = (UserDTO)ApplicationState.getInstance().getItem("UserDTO");
        long userId = user.getUserId();
        reservationService.confirmAttendance(reservationId, userId, new ServiceCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO body) {
                view.onConfirmSuccess();
            }

            @Override
            public void onError(UserDTO body) {

            }
        });
    }


    public void onCancelAttendance(long reservationId) {

        UserDTO user = (UserDTO)ApplicationState.getInstance().getItem("UserDTO");
        long userId = user.getUserId();
        reservationService.cancelAttendance(reservationId, userId, new ServiceCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO body) {
                view.onCancelSuccess();
            }

            @Override
            public void onError(UserDTO body) {

            }
        });
    }
}

