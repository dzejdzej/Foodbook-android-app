package com.robpercival.demoapp.presenter;

import android.util.Log;

import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * Created by User on 5/25/2018.
 */

public class SearchPresenter {

    private final SearchPresenter.SearchView view;

    public interface SearchView {

        void onSearchFail();

        void onSearchSuccess(List<ReservationResponseDTO> dtos, ReservationRequestDTO dto);

        Date getReservationDate();

        String getReservationTime();

        String getCuisineType();

        String getCity();

        int getDuration();

        int getNumberOfSeats();

        void showLoader();

        void hideLoader();
    }

    ;

    ReservationService reservationService = ReservationServiceImpl.getInstance();

    public SearchPresenter(SearchPresenter.SearchView view) {
        this.view = view;
    }

    public void onSearchClick() {
        final ReservationRequestDTO dto = constructDTO();
        view.showLoader();
        reservationService.getAvailableRestaurants(dto, new ServiceCallback<List<ReservationResponseDTO>>() {
            @Override
            public void onSuccess(List<ReservationResponseDTO> body) {
                Log.d("DTOS", body.toString());
                //view.hideLoader();
                view.onSearchSuccess(body, dto);
            }

            @Override
            public void onError(List<ReservationResponseDTO> body) {

                view.onSearchFail();
            }
        });
    }

    private ReservationRequestDTO constructDTO() {
        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setBegin(view.getReservationTime());
        dto.setCity(view.getCity());
        dto.setCuisine(ReservationRequestDTO.toEnumString(view.getCuisineType()));
        dto.setDate(view.getReservationDate().getTime());
        dto.setDuration(view.getDuration() * 3600 * 1000);
        dto.setSeats(view.getNumberOfSeats());
        return dto;
    }
}
