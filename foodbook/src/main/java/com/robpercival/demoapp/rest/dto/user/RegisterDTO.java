package com.robpercival.demoapp.rest.dto.user;

/**
 * Created by User on 5/20/2018.
 */

public class RegisterDTO {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String address;
   // private long countryId;
   // private long cityId;

    public RegisterDTO() {}

    public RegisterDTO(String name, String surname, /*long countryId, long cityId,*/ String username, String password, String email, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        //this.countryId = countryId;
        //this.cityId = cityId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
