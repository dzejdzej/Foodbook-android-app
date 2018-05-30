package com.robpercival.demoapp.rest.dto.user;

import java.util.Date;

/**
 * Created by User on 5/27/2018.
 */

public class ReservationRequestDTO {

    private String city;
    private String cuisine;

    private long date;
    private String begin;
    private long duration;
    private int seats;

    public ReservationRequestDTO() {

    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public static String toEnumString(String s){
        switch(s) {
            case "Indian":
                return "INDIAN";
            case "Italian":
                return "ITALIAN";
            case "Serbian":
                return "SERBIAN";
            case "Franche":
                return "FRANCHE";
            case "Chinese":
                return "CHINESE";
            default:
                return null;
        }
    }
}
