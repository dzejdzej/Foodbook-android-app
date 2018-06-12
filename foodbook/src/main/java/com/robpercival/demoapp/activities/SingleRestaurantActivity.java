package com.robpercival.demoapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.RowCommentAdapter;
import com.robpercival.demoapp.presenter.SingleRestaurantPresenter;
import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.services.FirebaseIDService;

import com.robpercival.demoapp.state.ApplicationState;

import java.io.IOException;
import java.util.List;

public class SingleRestaurantActivity extends AppCompatActivity implements OnMapReadyCallback, SingleRestaurantPresenter.SingleRestaurantView {

    private DrawerLayout drawerLayout;
    private GoogleMap mMap;
    private String reservationRequestJson;
    private ReservationRequestDTO reservationRequest;
    private long restaurantId;
    private SingleRestaurantPresenter presenter;
    private Button reserveButton, callPhoneButton, addCommentButton;
    private String restaurantDtoJson;
    private ReservationResponseDTO restaurantDto;
    private List<CommentDto> comments;
    private ListView commentsListView;
    private RowCommentAdapter adapter;
    private String restaurantContactNumber;
    private EditText commentText;
    private Dialog rankDialog;
    private RatingBar ratingBar, singleRestaurantRatingBar;
    private Double restaurantRating = 0d;
    private TextView ratingTextView;



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
            reservationRequest = new Gson().fromJson(reservationRequestJson, ReservationRequestDTO.class);
            restaurantDto = new Gson().fromJson(restaurantDtoJson, ReservationResponseDTO.class);
            ApplicationState.getInstance().setItem("reservationRequestSingleRestaurant", reservationRequest);
            ApplicationState.getInstance().setItem("restaurantDtoSingleRestaurant", restaurantDto);
        } else {
            reservationRequest = (ReservationRequestDTO) ApplicationState.getInstance().getItem("reservationRequestSingleRestaurant");
            restaurantDto = (ReservationResponseDTO) ApplicationState.getInstance().getItem("restaurantDtoSingleRestaurant");
        }

          restaurantContactNumber = restaurantDto.getRestaurantContact();



        reserveButton = (Button) findViewById(R.id.reserveButton);

        reserveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                makeAReservation();
            }
        });

        commentText = (EditText) findViewById(R.id.addComment);

        addCommentButton = (Button) findViewById(R.id.addCommentButton);

        addCommentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addComment();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupDrawerAndToolbar();

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
        presenter.getAllCommentsForRestaurant(restaurantId);
        presenter.getRatingForRestaurant(restaurantId);

        setupRestaurantData();

        Button rankBtn = (Button) findViewById(R.id.rank_button);
        rankBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rankDialog = new Dialog(SingleRestaurantActivity.this, R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(0);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Rate us");

                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateRestaurant(ratingBar.getRating());
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
            }
        });
    }

    private void addComment() {
        if(commentText.getText().toString().length()!=0)

            presenter.addComment(
                    commentText.getText().toString(),
                    ((UserDTO)ApplicationState.getInstance().getItem("UserDTO")).getName(),
                    restaurantId);
        else
            Toast.makeText(getApplicationContext(), "You can't post an empty comment.", Toast.LENGTH_LONG).show();
    }

    private void rateRestaurant(float rating) {
        if (ratingBar.getRating() != 0) {
            presenter.rateRestaurant(Double.valueOf(rating), ((UserDTO)ApplicationState.getInstance().getItem("UserDTO")).getName(), restaurantId);
        } else {
            Toast.makeText(getApplicationContext(), "You didn't choose you rating.", Toast.LENGTH_LONG).show();
        }

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

        MenuItem logoutMenu = menu.getItem(0);

        logoutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent login = new Intent(SingleRestaurantActivity.this, LoginActivity.class);
                SingleRestaurantActivity.this.startActivity(login);

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
                intent = new Intent(SingleRestaurantActivity.this, MainActivity.class);
                SingleRestaurantActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                intent = new Intent(SingleRestaurantActivity.this, MyReservationsActivity.class);
                SingleRestaurantActivity.this.startActivity(intent);
                //MainActivity.this.finish();
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
        LatLng latLng = new LatLng(this.restaurantDto.getX(), this.restaurantDto.getY());
        mMap.addMarker(new MarkerOptions().position(latLng).title(this.restaurantDto.getRestaurantName()));
        /*mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
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

        singleRestaurantRatingBar = findViewById(R.id.singleRestaurantRatingBar);
        singleRestaurantRatingBar.setRating((float)restaurantRating.doubleValue());

        ratingTextView = findViewById(R.id.singleRestaurantRatingBarValue);
        String s = (float)restaurantRating.doubleValue() +" / 5";
        ratingTextView.setText(s);

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

    @Override
    public void onPopulateComments(List<CommentDto> comments) {

        this.comments = comments;
        commentsListView = findViewById(R.id.commentsListView);

        adapter = new RowCommentAdapter(this, this.comments);

        commentsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onRestaurantRated(Double rating) {
        Log.d("tag", "RATING " + rating);
        restaurantRating = rating;
        singleRestaurantRatingBar.setRating((float)restaurantRating.doubleValue());
        String s = (float)restaurantRating.doubleValue() +" / 5";
        ratingTextView.setText(s);
    }

    @Override
    public void getRatingForRestaurant(Double rating) {
        restaurantRating = rating;
        singleRestaurantRatingBar.setRating((float)restaurantRating.doubleValue());
        String s = (float)restaurantRating.doubleValue() +" / 5";
        ratingTextView.setText(s);
    }
}
