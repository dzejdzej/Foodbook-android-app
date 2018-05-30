package com.robpercival.demoapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.SearchPresenter;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.robpercival.demoapp.R.id.linearLayoutSearch;
import static com.robpercival.demoapp.R.id.locationEditText;

public class SearchActivity extends Activity implements SearchPresenter.SearchView{

    private ListView searchListView;
    private ArrayList<String> searchListData = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> cuisines = new ArrayList<>();
    private EditText cityEditText;
    private EditText cuisineEditText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText duration;
    private EditText numberOfSeats;
    private SearchPresenter presenter;
    private ProgressBar pgsBar;
    private View searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

         pgsBar = (ProgressBar)findViewById(R.id.loader);
         searchView = findViewById(R.id.linearLayoutSearch);

        DatePicker datePicker = findViewById(R.id.datePicker);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        presenter = new SearchPresenter(this);
        populateArrays();
        setupEditTexts();
        setupViewEvents();


    }

    private void setupEditTexts() {
        cityEditText = findViewById(R.id.locationEditText);
        cityEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    generateListViewData(true, ((EditText)view).getText().toString());
                }
            }
        });
        cityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                generateListViewData(true, charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });


        cuisineEditText = findViewById(R.id.cuisineEditText);
        cuisineEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    generateListViewData(false, ((EditText)v).getText().toString());
                }
            }
        });
        cuisineEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                generateListViewData(false, charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        duration = findViewById(R.id.durationEditText);
        numberOfSeats = findViewById(R.id.numberOfSeatsEditText);
    }

    private void setupViewEvents() {
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EditText locationEditText = findViewById(R.id.locationEditText);

                if(locationEditText.hasFocus()) {
                    locationEditText.setText(((TextView) view).getText().toString());
                } else {
                    EditText cuisineEditText = findViewById(R.id.cuisineEditText);
                    cuisineEditText.setText(((TextView) view).getText().toString());
                }
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!locations.contains(cityEditText.getText().toString()) || !cuisines.contains(cuisineEditText.getText().toString())){
                    Toast.makeText(getApplicationContext(), R.string.invalidLocationOrCuisine,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                presenter.onSearchClick();


            }
        });
    }

    private void generateListViewData(boolean isLocation, String userInput) {
        if (isLocation) {
            searchListData.clear();
            String filter = userInput.trim().toLowerCase();

            for (String location : locations) {
                if(location.trim().toLowerCase().startsWith(filter)) {
                    searchListData.add(location);
                }
            }
        } else {
            searchListData.clear();
            userInput = userInput.trim().toLowerCase();

            for (String cuisine : cuisines) {
                if(cuisine.trim().toLowerCase().startsWith(userInput)) {
                    searchListData.add(cuisine);
                }
            }
        }
        ((BaseAdapter) searchListView.getAdapter()).notifyDataSetChanged();
    }

    private void populateArrays(){
        searchListView = findViewById(R.id.searchListView);

        locations.add("Novi Sad");
        locations.add("Beograd");
        //locations.add("Paris");

        cuisines.add("Italian");
        cuisines.add("Greek");
        cuisines.add("Serbian");
        cuisines.add("Chinese");

        searchListData.addAll(locations);

        searchListView.setAdapter(new ArrayAdapter<String>(this, R.layout.row_location_cuisine, searchListData));
    }

    @Override
    public void onSearchFail() {

    }

    @Override
    public void onSearchSuccess(List<ReservationResponseDTO> availableRestaurants, ReservationRequestDTO reservationRequest) {
        Intent activity = new Intent(SearchActivity.this, MainActivity.class);
        activity.putExtra("availableRestaurants", new Gson().toJson(availableRestaurants));
        activity.putExtra("reservationRequest", new Gson().toJson(reservationRequest));
        SearchActivity.this.startActivity(activity);
        SearchActivity.this.finish();
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    @Override
    public Date getReservationDate() {
        return getDateFromDatePicker(datePicker);
    }

    @Override
    public String getReservationTime() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        StringBuilder builder = new StringBuilder();
        builder.append(hour).append(":").append(minute);

        return builder.toString();
    }

    @Override
    public String getCuisineType() {
        return cuisineEditText.getText().toString();
    }

    @Override
    public String getCity() {
        return cityEditText.getText().toString();
    }

    @Override
    public int getDuration() {
        return Integer.parseInt(duration.getText().toString());
    }

    @Override
    public int getNumberOfSeats() { return Integer.parseInt(numberOfSeats.getText().toString()); }

    @Override
    public void showLoader() {
        pgsBar.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoader() {
        pgsBar.setVisibility(View.GONE);
        searchView.setVisibility(View.VISIBLE);
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

}
