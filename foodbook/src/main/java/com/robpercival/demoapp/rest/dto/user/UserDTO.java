package com.robpercival.demoapp.rest.dto.user;

/**
 * Created by User on 5/19/2018.
 */

public class UserDTO {
    private String name;
    private String surname;
    private long userId;

    public UserDTO() {

    }

    public UserDTO(String name, String surname, long userId) {
        this.name = name;
        this.surname = surname;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
