package com.robpercival.demoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

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

public class SingleRestaurantActivity extends FragmentActivity implements OnMapReadyCallback, SingleRestaurantPresenter.SingleRestaurantView {

    private GoogleMap mMap;
    private String reservationRequestJson;
    private ReservationRequestDTO reservationRequest;
    private long restaurantId;
    private SingleRestaurantPresenter presenter;
    private Button reserveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reservationRequestJson = extras.getString("reservationRequest");
            restaurantId = extras.getLong("restaurantId");
        }

        reservationRequest = new Gson().fromJson(reservationRequestJson, ReservationRequestDTO.class);


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

        setupRestaurantData();
        presenter = new SingleRestaurantPresenter(this);
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
        nameTextView.setText("Lanterna");

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
