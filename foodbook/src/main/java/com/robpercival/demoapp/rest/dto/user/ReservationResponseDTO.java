package com.robpercival.demoapp.rest.dto.user;

/**
 * Created by User on 5/27/2018.
 */

public class ReservationResponseDTO {

    private String restaurantName;
    private long restaurantId;
    private String imageUrl;
    private String about;
    private String restaurantContact;
    private double x;
    private double y;

    public ReservationResponseDTO() {

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRestaurantContact() {
        return restaurantContact;
    }

    public void setRestaurantContact(String restaurantContact) {
        this.restaurantContact = restaurantContact;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
