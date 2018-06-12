package com.robpercival.demoapp.rest.dto.user;

/**
 * Created by User on 5/30/2018.
 */

public class CreatedReservationDTO {

    private long reservationId;

    public CreatedReservationDTO() {

    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }
}