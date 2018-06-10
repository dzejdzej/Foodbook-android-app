package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

/**
 * Created by User on 5/19/2018.
 */

public class LoginPresenter {

    private final LoginPresenter.LoginView view;

    public interface LoginView {

        void onLoginFail();
        void onLoginSuccess();
    };

    UserService userService = UserServiceImpl.getInstance();

    public LoginPresenter(LoginPresenter.LoginView view) {
        this.view = view;
    }

    public void onLoginClick(String username, String password) {


        userService.login(username, password, new ServiceCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO body) {

                if(body == null) {
                    view.onLoginFail();
                }
                else {
                    ApplicationState.getInstance().setItem("UserDTO", body);
                    view.onLoginSuccess();
                }
            }

            @Override
            public void onError(UserDTO body) {

                view.onLoginFail();
            }
        });
    }
}