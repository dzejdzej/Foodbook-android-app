package com.robpercival.demoapp.rest.dto;

import java.time.LocalDateTime;

public class CommentDto {

    Long restaurantId;
    String user;
    LocalDateTime date;
    String text;
    int rate;

    public CommentDto() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(final Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }
}
