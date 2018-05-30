package com.robpercival.demoapp.rest.service;

import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.ReserveDTO;

import java.util.List;

/**
 * Created by User on 5/27/2018.
 */

public interface ReservationService {
    void getAvailableRestaurants(ReservationRequestDTO dto, final ServiceCallback<List<ReservationResponseDTO>> presenterCallback);
    void reserve(ReservationRequestDTO reservationRequest, long restaurantId, final ServiceCallback<ReserveDTO> presenterCallback);

}
