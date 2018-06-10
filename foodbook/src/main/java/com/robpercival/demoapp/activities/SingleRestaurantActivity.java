package com.robpercival.demoapp.activities;

import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.SingleRestaurantPresenter;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.state.ApplicationState;

public class SingleRestaurantActivity extends AppCompatActivity implements OnMapReadyCallback, SingleRestaurantPresenter.SingleRestaurantView {

    private DrawerLayout drawerLayout;
    private GoogleMap mMap;
    private String reservationRequestJson;
    private ReservationRequestDTO reservationRequest;
    private long restaurantId;
    private SingleRestaurantPresenter presenter;
    private Button reserveButton, callPhoneButton;
    private String restaurantDtoJson;
    private ReservationResponseDTO restaurantDto;
    private String restaurantContactNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);

        setTitle("Restaurant details");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reservationRequestJson = extras.getString("reservationRequest");
            restaurantId = extras.getLong("restaurantId");
            restaurantDtoJson = extras.getString("restaurantDto");
        }

        reservationRequest = new Gson().fromJson(reservationRequestJson, ReservationRequestDTO.class);
        restaurantDto = new Gson().fromJson(restaurantDtoJson, ReservationResponseDTO.class);
        restaurantContactNumber = restaurantDto.getRestaurantContact();


        reserveButton = (Button) findViewById(R.id.reserveButton);

        reserveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                makeAReservation();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupDrawerAndToolbar();
        setupRestaurantData();

        callPhoneButton = (Button) findViewById(R.id.callPhoneButton);
        callPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                String phoneNum = "tel:" + restaurantContactNumber;
                callIntent.setData(Uri.parse(phoneNum));
                startActivity(callIntent);
            }
        });

        presenter = new SingleRestaurantPresenter(this);
    }


    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar_single_restaurant);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout_single_restaurant);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view_single_restaurant);
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

        Intent intent = null;

        switch (id) {
            case R.id.nav_menu1:
                intent = new Intent(SingleRestaurantActivity.this, MainActivity.class);
                SingleRestaurantActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                intent = new Intent(SingleRestaurantActivity.this, MyReservationsActivity.class);
                SingleRestaurantActivity.this.startActivity(intent);
                //MainActivity.this.finish();
                break;
            case R.id.nav_menu3:
                //rad sa mapom implementirati - da se otvori mapa sa svim restoranima u tom gradu
                break;
            case R.id.nav_menu4:
                intent = new Intent(SingleRestaurantActivity.this, ChangePasswordActivity.class);
                SingleRestaurantActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu5:
                intent = new Intent(SingleRestaurantActivity.this, SearchActivity.class);
                SingleRestaurantActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_single_restaurant, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_single_restaurant);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void makeAReservation() {
        presenter.onReservationClick(reservationRequest, restaurantId);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void setupRestaurantData() {
        TextView nameTextView = findViewById(R.id.singleRestaurantNameTextView);
        nameTextView.setText(restaurantDto.getRestaurantName());



        ImageView imageView = findViewById(R.id.singleRestaurantImageView);

        Glide.with(this)
                .load(ApplicationState.SERVER_IP +"/" + restaurantDto.getImageUrl())
                .into(imageView);
        //imageView.setImageDrawable(myDrawable);




        TextView openTextView = findViewById(R.id.singleRestaurantOpenTextView);
        openTextView.setText("Open: 8AM - 10PM (OPEN NOW)");

        TextView streetTextView = findViewById(R.id.singleRestaurantStreetTextView);
        streetTextView.setText("Street: Oxford rd. 23");

        RatingBar ratingBar = findViewById(R.id.singleRestaurantRatingBar);
        ratingBar.setRating(4.0f);

        TextView ratingTextView = findViewById(R.id.singleRestaurantRatingBarValue);
        ratingTextView.setText("4.5 / 5");

        Button reserveButton = findViewById(R.id.reserveButton);
        reserveButton.setText("Reserve " + reservationRequest.getSeats() + " seats.");
    }


    @Override
    public void onReservationFail(String s) {

    }

    @Override
    public void onReservationSuccess(long reservationId) {
        Intent activity = new Intent(SingleRestaurantActivity.this, InviteFriendsActivity.class);
        activity.putExtra("reservationId", reservationId);
        SingleRestaurantActivity.this.startActivity(activity);
        SingleRestaurantActivity.this.finish();
    }
}
