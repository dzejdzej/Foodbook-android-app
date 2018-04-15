package com.robpercival.demoapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.robpercival.demoapp.R;

public class SingleRestaurantActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupRestaurantData();
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
    }
}
