package com.robpercival.demoapp.rest.service.impl;

import com.robpercival.demoapp.rest.UserAPI;
import com.robpercival.demoapp.rest.dto.user.LoginDTO;
import com.robpercival.demoapp.rest.dto.user.RegisterDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 5/19/2018.
 */

public class UserServiceImpl extends BaseServiceImpl implements  UserService{


    private UserAPI userApi;

    private static UserService instance;


    private UserServiceImpl() {
        super();
        buildHttpClient();

    }

    private void buildHttpClient() {
        userApi = retrofit.create(UserAPI.class);
    }

    //private tempPolje
    @Override
    public void login(String username, String password, final ServiceCallback<UserDTO> presenterCallback) {
        LoginDTO dto = new LoginDTO(username, password);

        //presenterCallback.onSuccess(new UserDTO());


        Call<UserDTO> apiCall = userApi.loginUser(dto);
        //tempPolje = presenterCallback;
        apiCall.enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                UserDTO body = response.body();
                presenterCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void register(String name, String surname, String username, String password, String email, String address, final ServiceCallback<UserDTO> presenterCallback) {
        RegisterDTO dto = new RegisterDTO(name, surname, username, password, email, address);
        Call<UserDTO> apiCall = userApi.registerUser(dto);

        apiCall.enqueue(new Callback<UserDTO>() {
            //String reason;
            //int responseCode;
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                UserDTO body = response.body();
                presenterCallback.onSuccess(body);
                //responseCode = response.code();
                //reason = response.body().toString();
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
            }
        });

    }


    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }
}
