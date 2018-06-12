package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.CommentService;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.CommentServiceImpl;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

import java.util.List;

/**
 * Created by User on 5/29/2018.
 */

public class SingleRestaurantPresenter {

    private final SingleRestaurantPresenter.SingleRestaurantView view;
    private CommentService commentService = CommentServiceImpl.getInstance();

    public interface SingleRestaurantView {

        void onReservationFail(String s);

        void onReservationSuccess(long reservationId);

        void onPopulateComments(List<CommentDto> comments);
    }

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

                if (body.getReservationId() == -1)
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

    public void getAllCommentsForRestaurant(Long restaurantId) {

/*        commentService.getAllCommentsForRestaurant(restaurantId, new ServiceCallback<List<CommentDto>>() {

            @Override
            public void onSuccess(List<CommentDto> body) {
                view.onPopulateComments(body);
            }

            @Override
            public void onError(List<CommentDto> body) {
            }
        });*/
    }
}

