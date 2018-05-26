package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;

/**
 * Created by User on 5/19/2018.
 */
public class MainPresenter {

    private final MainView view;

    public interface MainView {

       void onLoginFail();
       void onLoginSuccess();
    };

    UserService userService = UserServiceImpl.getInstance();

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void onLoginClick(String username, String password) {

        userService.login(username, password, new ServiceCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO body) {
                view.onLoginSuccess();
            }

            @Override
            public void onError(UserDTO body) {
                view.onLoginFail();
            }
        });

    }

}

