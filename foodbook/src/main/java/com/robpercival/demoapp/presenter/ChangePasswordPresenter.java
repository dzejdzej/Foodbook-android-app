package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.LoginDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;
import com.robpercival.demoapp.state.ApplicationState;

/**
 * Created by User on 6/8/2018.
 */

public class ChangePasswordPresenter {

    private final ChangePasswordPresenter.ChangePasswordView view;

    public interface ChangePasswordView {

        void onChangePasswordFail();
        void onChangePasswordSuccess();
    };

    UserService userService = UserServiceImpl.getInstance();

    public ChangePasswordPresenter(ChangePasswordPresenter.ChangePasswordView view) {
        this.view = view;
    }

    public void onChangePasswordClick(String oldPassword, String newPassword) {

        UserDTO dto = (UserDTO) ApplicationState.getInstance().getItem("UserDTO");

        long userId = dto.getUserId();

        userService.changePassword(userId, oldPassword, newPassword, new ServiceCallback<LoginDTO>() {
            @Override
            public void onSuccess(LoginDTO body) {

                if(body == null) {
                    view.onChangePasswordFail();;
                }
                else {
                    view.onChangePasswordSuccess();
                }
            }

            @Override
            public void onError(LoginDTO body) {

                view.onChangePasswordSuccess();
            }
        });


    }
}



