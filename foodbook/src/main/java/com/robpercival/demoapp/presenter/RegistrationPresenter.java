package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;

/**
 * Created by User on 5/20/2018.
 */

public class RegistrationPresenter {

    private final RegistrationPresenter.RegistrationView view;

    public interface RegistrationView {

        void onRegistrationFail(String reason);
        void onRegistrationSuccess();
        void setCountriesAndCities();
    };

    UserService userService = UserServiceImpl.getInstance();

    public RegistrationPresenter(RegistrationPresenter.RegistrationView view) {
        this.view = view;

        //getCountriesAndCities();
    }


    public void onRegistrationClick(String name, String surname, String username, String password, String email, String address) {

        userService.register(name, surname, username, password, email, address, new ServiceCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO body) {
                view.onRegistrationSuccess();
            }

            @Override
            public void onError(UserDTO body) { view.onRegistrationFail("Neuspesna registracija"); }
        });

    }

}


