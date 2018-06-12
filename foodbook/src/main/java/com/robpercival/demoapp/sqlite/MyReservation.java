package com.robpercival.demoapp.sqlite;

public class MyReservation {


    private int reservationId;
    private String reservationDate;
    private String restaurantName;

    public MyReservation() {

    }

    public MyReservation(int reservationId, String reservationDate, String restaurantName) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.restaurantName = restaurantName;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
