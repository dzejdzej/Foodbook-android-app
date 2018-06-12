package com.robpercival.demoapp.presenter;

import android.util.Log;

import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.dto.RatingDTO;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.CommentService;
import com.robpercival.demoapp.rest.service.RatingService;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.impl.CommentServiceImpl;
import com.robpercival.demoapp.rest.service.impl.RatingServiceImpl;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

import java.util.List;

/**
 * Created by User on 5/29/2018.
 */

public class SingleRestaurantPresenter {

    private final SingleRestaurantPresenter.SingleRestaurantView view;
    private CommentService commentService = CommentServiceImpl.getInstance();
    private RatingService ratingService = RatingServiceImpl.getInstance();

    public interface SingleRestaurantView {

        void onReservationFail(String s);

        void onReservationSuccess(long reservationId);

        void onPopulateComments(List<CommentDto> comments);

        void onRestaurantRated(Double rating);

        void getRatingForRestaurant(Double rating);

        void onCommentAdded(CommentDto body);
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

        commentService.getAllCommentsForRestaurant(restaurantId, new ServiceCallback<List<CommentDto>>() {

            @Override
            public void onSuccess(List<CommentDto> body) {
                view.onPopulateComments(body);
            }

            @Override
            public void onError(List<CommentDto> body) {
            }
        });
    }


    public void addComment(String commentText, String username, long restaurantId) {

        CommentDto comm = new CommentDto();
        comm.setUser(username);
        comm.setText(commentText);
        comm.setRestaurantId(restaurantId);

        commentService.addComment(comm, new ServiceCallback<CommentDto>() {
            @Override
            public void onSuccess(CommentDto body) {
                Log.d("tag","Succesfully added comment!");
                view.onCommentAdded(body);
            }

            @Override
            public void onError(CommentDto body) {

            }
        });

        commentService.getAllCommentsForRestaurant(restaurantId, new ServiceCallback<List<CommentDto>>() {

            @Override
            public void onSuccess(List<CommentDto> body) {
                view.onPopulateComments(body);
            }

            @Override
            public void onError(List<CommentDto> body) {
            }
        });
    }

    public void rateRestaurant(Double rating, String username, long restaurantId) {

        final RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRating(rating);
        ratingDTO.setRestaurantId(restaurantId);
        ratingDTO.setUser(username);

        ratingService.rateRestaurant(ratingDTO, new ServiceCallback<Double>() {
            @Override
            public void onSuccess(Double body) {
                view.onRestaurantRated(body);
            }

            @Override
            public void onError(Double body) {

            }
        });
    }

    public void getRatingForRestaurant(long restaurantId) {
        ratingService.getRatingForRestaurant(restaurantId, new ServiceCallback<Double>() {
            @Override
            public void onSuccess(Double body) {
                view.getRatingForRestaurant(body);
            }

            @Override
            public void onError(Double body) {

            }
        });
    }

}

