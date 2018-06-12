package com.robpercival.demoapp.presenter;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.robpercival.demoapp.rest.dto.ReservationDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.rest.service.ReservationService;
import com.robpercival.demoapp.rest.service.ServiceCallback;
import com.robpercival.demoapp.rest.service.UserService;
import com.robpercival.demoapp.rest.service.impl.ReservationServiceImpl;
import com.robpercival.demoapp.rest.service.impl.UserServiceImpl;
import com.robpercival.demoapp.services.FirebaseIDService;
import com.robpercival.demoapp.sqlite.MyReservation;
import com.robpercival.demoapp.sqlite.MyReservationContract;
import com.robpercival.demoapp.sqlite.MyReservationProvider;
import com.robpercival.demoapp.state.ApplicationState;

import java.util.List;

/**
 * Created by User on 5/19/2018.
 */

public class LoginPresenter {

    private final LoginPresenter.LoginView view;
    ReservationService reservationService = ReservationServiceImpl.getInstance();


    public interface LoginView {

        void onLoginFail();
        void onLoginSuccess();

        ContentResolver getAndroidContentResolver();
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
                    FirebaseInstanceId.getInstance().getToken();
                    FirebaseIDService.subscribeToUsernameTopic("" +body.getUserId());
                    // clear DB
                    ContentResolver cr  = view.getAndroidContentResolver();
                    cr.call(MyReservationContract.BASE_CONTENT_URI, "deleteAll", null, null);


                    // fill DB with my reservations
                    fetchMyReservationsAndStoreInSQLite(body);

                    view.onLoginSuccess();

                }
            }

            @Override
            public void onError(UserDTO body) {

                view.onLoginFail();
            }
        });
    }

    private void fetchMyReservationsAndStoreInSQLite(UserDTO dto) {
        long userId = dto.getUserId();

        reservationService.getAllReservationsForUser(userId, new ServiceCallback<List<ReservationDTO>>() {
            @Override
            public void onSuccess(List<ReservationDTO> body) {
                Log.v("cacheReservations","Caching reservations");
                ContentResolver cr = view.getAndroidContentResolver();
                for(ReservationDTO reservationDTO: body) {
                    Log.v("cacheReservations","reservationDTO Id=" + reservationDTO.getId());

                    MyReservation reservation = new MyReservation((int)reservationDTO.getId(),
                            reservationDTO.getBeginDate().toString(),
                            reservationDTO.getRestaurantName());

                    ContentValues contentValues = MyReservationContract.toContentValues(reservation);
                    cr.insert(MyReservationContract.URI_TABLE,contentValues);
                }

                         Log.v("cacheReservations","DONE");
            }

            @Override
            public void onError(List<ReservationDTO> body) {

            }
        });
    }
}