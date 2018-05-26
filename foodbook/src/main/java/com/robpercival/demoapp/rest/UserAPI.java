package com.robpercival.demoapp.rest;

import com.robpercival.demoapp.rest.dto.user.LoginDTO;
import com.robpercival.demoapp.rest.dto.user.RegisterDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by User on 5/19/2018.
 */

public interface UserAPI {

    @POST("user/login")
    Call<UserDTO> loginUser(@Body LoginDTO dto);

    @POST("user/")
    Call<UserDTO> registerUser(@Body RegisterDTO dto);
}
