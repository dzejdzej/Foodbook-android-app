package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.InviteFriendsDTO;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;

import java.util.List;

/**
 * Created by User on 6/3/2018.
 */

public class InviteFriendsPresenter {

    private final InviteFriendsPresenter.InviteFriendsView view;


    public interface InviteFriendsView {
        void onFinishFail();
        void onFinishSuccess();
    };

    ReservationService reservationService = ReservationServiceImpl.getInstance();

    public InviteFriendsPresenter(InviteFriendsPresenter.InviteFriendsView view) {
        this.view = view;
    }

    public void onFinishClicked(List<String> invitedFriends, long reservationId) {

        InviteFriendsDTO dto = new InviteFriendsDTO();
        dto.setInvitedFriends(invitedFriends);
        dto.setReservationId(reservationId);
        reservationService.finishReservation(dto, new ServiceCallback<InviteFriendsDTO>() {

            @Override
            public void onSuccess(InviteFriendsDTO body) {
                view.onFinishSuccess();
            }

            @Override
            public void onError(InviteFriendsDTO body) {

            }
        });
    }

}

