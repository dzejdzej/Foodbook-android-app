package com.robpercival.demoapp.presenter;

import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;

/**
 * Created by User on 5/25/2018.
 */

public class SearchPresenter {

    private final SearchPresenter.SearchView view;

    public interface SearchView {

        void onSearchFail();
        void onSearchSuccess();
    };

    UserService userService = UserServiceImpl.getInstance();

    public SearchPresenter(SearchPresenter.SearchView view) {
        this.view = view;
    }

    public void onSearchClick(String username, String password) {

        userService.login(username, password, new ServiceCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO body) {

                view.onSearchSuccess();
            }

            @Override
            public void onError(UserDTO body) {

                view.onSearchFail();
            }
        });
    }
}
