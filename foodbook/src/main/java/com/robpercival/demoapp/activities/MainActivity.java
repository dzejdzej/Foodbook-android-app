package com.robpercival.demoapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.RowRestaurantAdapter;
import com.robpercival.demoapp.presenter.MainPresenter;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.services.FirebaseIDService;
import com.robpercival.demoapp.state.ApplicationState;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements  MainPresenter.MainView {

    private DrawerLayout drawerLayout;
    private ListView restaurantListView;

    private MainPresenter mainPresenter;
    private List<ReservationResponseDTO> availableRestaurants;
    private ReservationRequestDTO reservationRequest;
    private String availableRestaurantsJson;
    private String reservationRequestJson;

    public static <ReservationResponseDTO> List<ReservationResponseDTO> stringToArray(String jsonString, Class<ReservationResponseDTO[]> clazz) {
        ReservationResponseDTO[] arr = new Gson().fromJson(jsonString, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.home);

        availableRestaurantsJson = null;
        reservationRequestJson = null;
        Bundle extras = getIntent().getExtras();

        setupDrawerAndToolbar();

        if(extras == null) {
            Toast.makeText(getApplicationContext(), "No results available, please perform search before continuing !", Toast.LENGTH_LONG).show();
            return;
        }

        if (extras.getString("availableRestaurants") != null) {
            availableRestaurantsJson = extras.getString("availableRestaurants");
            reservationRequestJson = extras.getString("reservationRequest");
            availableRestaurants = stringToArray(availableRestaurantsJson, ReservationResponseDTO[].class);
            reservationRequest = new Gson().fromJson(reservationRequestJson, ReservationRequestDTO.class);
            ApplicationState.getInstance().setItem("availableRestaurants", availableRestaurants);
            ApplicationState.getInstance().setItem("reservationRequest", reservationRequest);
        }else {
            availableRestaurants = (List) ApplicationState.getInstance().getItem("availableRestaurants");
            reservationRequest = (ReservationRequestDTO) ApplicationState.getInstance().getItem("reservationRequest");
        }

        //Log.d("Velicina dtos: ", jsonMyObject);



        if(availableRestaurants == null) {
            return;
        }

        populateListView();



       // System.out.println(getIntent().getExtras().get("location") + "");
       // System.out.println(getIntent().getExtras().get("cuisine") + "");

        mainPresenter = new MainPresenter(this);



    }

    private void populateListView() {

        restaurantListView = findViewById(R.id.restaurantListView);

        restaurantListView.setAdapter(new RowRestaurantAdapter(this, this.availableRestaurants));

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridLayout restaurantGridLayout = (GridLayout) view;
                long restaurantId = availableRestaurants.get(i).getRestaurantId();
                // iscupati koji je restoran u pitanju
                Intent singleRestaurantIntent = new Intent(MainActivity.this, SingleRestaurantActivity.class);
                singleRestaurantIntent.putExtra("reservationRequest", reservationRequestJson);
                singleRestaurantIntent.putExtra("restaurantId", restaurantId);
                singleRestaurantIntent.putExtra("restaurantDto", new Gson().toJson(availableRestaurants.get(i)));
                MainActivity.this.startActivity(singleRestaurantIntent);

            }
        });
    }

    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();

                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        displaySelectedScreen(id);
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);

        MenuItem logoutMenu = menu.getItem(0);

        logoutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(login);

                UserDTO dto = (UserDTO) ApplicationState.getInstance().getItem("UserDTO");

                FirebaseIDService.unsubscribe(dto.getUserId());
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ApplicationState.getInstance().clear();

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id) {
        Fragment fragment = null;

        Intent intent = null;

        switch (id) {
            case R.id.nav_menu1:
                return;
            case R.id.nav_menu2:
                intent = new Intent(MainActivity.this, MyReservationsActivity.class);
                MainActivity.this.startActivity(intent);
                //MainActivity.this.finish();
                break;
            case R.id.nav_menu3:
                CharSequence languages[] = new CharSequence[] {"Serbian", "English"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.selectLanguage);
                builder.setItems(languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {
                            Locale locale = new Locale("sr");
                            Configuration config = getBaseContext().getResources().getConfiguration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Locale locale = new Locale("en");
                            Configuration config = getBaseContext().getResources().getConfiguration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
                break;
            case R.id.nav_menu4:
                intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu5:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onLoginFail() {

    }

    @Override
    public void onLoginSuccess() {

    }
}
