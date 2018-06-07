package com.robpercival.demoapp.rest.service;

import com.robpercival.demoapp.rest.dto.InviteFriendsDTO;
import com.robpercival.demoapp.rest.dto.ReservationDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.CreatedReservationDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;

import java.util.List;

/**
 * Created by User on 5/27/2018.
 */

public interface ReservationService {
    void getAvailableRestaurants(ReservationRequestDTO dto, final ServiceCallback<List<ReservationResponseDTO>> presenterCallback);

    void reserve(ReservationRequestDTO reservationRequest, long restaurantId, final ServiceCallback<CreatedReservationDTO> presenterCallback);

    void finishReservation(InviteFriendsDTO dto, final ServiceCallback<InviteFriendsDTO> presenterCallback);

    void getAllReservationsForUser(long userId, final ServiceCallback<List<ReservationDTO>> presenterCallback);

    void cancelReservation(long userId, long reservationId, final ServiceCallback<List<ReservationDTO>> serviceCallback);
}
