package com.robpercival.demoapp.rest.dto.user;

/**
 * Created by User on 5/27/2018.
 */

public class ReservationResponseDTO {

    private String restaurantName;
    private long restaurantId;
    private String imageUrl;
    private String about;

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

}
