package com.robpercival.demoapp.rest.service;

import com.robpercival.demoapp.rest.dto.user.LoginDTO;
import com.robpercival.demoapp.rest.dto.user.RegisterDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;

/**
 * Created by User on 5/19/2018.
 */

public interface UserService {

    void login(String username, String password, final ServiceCallback<UserDTO> presenterCallback);
    void register(String name, String surname, String username, String password, String email, String address, final ServiceCallback<UserDTO> presenterCallback);

    void changePassword(long userId, String oldPassword, String newPassword, final ServiceCallback<LoginDTO> presenterCallback);
    /**************
     *
     *        UserService userService = UserServiceImpl.getInstance();
     *
     *        userService.login("Pera", "Peric", new ServiceCallback<UserDTO> () {
     *
     *
     *            public void onSuccess(UserDTO body) {
     *
     *            }
     *
     *            public void onError(UserDTO body) {
     *
     *            }
     *
     *        });
     *
     *
     */

}
