package com.robpercival.demoapp.activities;

import android.content.Intent;
import android.content.SyncStatusObserver;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.RowRestaurantAdapter;
import com.robpercival.demoapp.fragments.Menu1Fragment;
import com.robpercival.demoapp.fragments.Menu2Fragment;
import com.robpercival.demoapp.fragments.Menu3Fragment;
import com.robpercival.demoapp.fragments.Menu4Fragment;
import com.robpercival.demoapp.fragments.Menu5Fragment;
import com.robpercival.demoapp.presenter.MainPresenter;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        availableRestaurantsJson = null;
        reservationRequestJson = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            availableRestaurantsJson = extras.getString("availableRestaurants");
            reservationRequestJson = extras.getString("reservationRequest");
        }

        availableRestaurants = stringToArray(availableRestaurantsJson, ReservationResponseDTO[].class);
        reservationRequest = new Gson().fromJson(reservationRequestJson, ReservationRequestDTO.class);

        //Log.d("Velicina dtos: ", jsonMyObject);

        setupDrawerAndToolbar();
        populateListView();

        displaySelectedScreen(R.id.nav_menu1);

        System.out.println(getIntent().getExtras().get("location") + "");
        System.out.println(getIntent().getExtras().get("cuisine") + "");

        mainPresenter = new MainPresenter(this);



    }

    private void populateListView() {

        restaurantListView = findViewById(R.id.restaurantListView);
        // izvuci iz bundle listu
        //
        /*List<String> stockList = new ArrayList<String>();
        stockList.add("stock1");
        stockList.add("stock2");

        String[] stockArr = new String[stockList.size()];
        stockArr = stockList.toArray(stockArr);*/
        //ReservationResponseDTO[] dtoArray = new ReservationResponseDTO[this.dtos.size()];
        //ReservationResponseDTO[] dtoArray =  (ReservationResponseDTO[]) this.dtos.toArray( new ReservationResponseDTO[this.dtos.size()]);


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

        switch (id) {
            case R.id.nav_menu1:
                fragment = new Menu1Fragment();
                break;
            case R.id.nav_menu2:
                fragment = new Menu2Fragment();
                break;
            case R.id.nav_menu3:
                fragment = new Menu3Fragment();
                break;
            case R.id.nav_menu4:
                fragment = new Menu4Fragment();
                break;
            case R.id.nav_menu5:
                fragment = new Menu5Fragment();
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
