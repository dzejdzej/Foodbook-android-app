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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.robpercival.demoapp.R;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private ListView searchListView;
    private ArrayList<String> searchListData = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> cuisines = new ArrayList<>();
    private EditText locationEditText;
    private EditText cuisineEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        populateArrays();
        setupEditTexts();
        setupViewEvents();
    }

    private void setupEditTexts() {
        locationEditText = findViewById(R.id.locationEditText);
        locationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    generateListViewData(true, ((EditText)view).getText().toString());
                }
            }
        });
        locationEditText.addTextChangedListener(new TextWatcher() {
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
                if(!locations.contains(locationEditText.getText().toString()) || !cuisines.contains(cuisineEditText.getText().toString())){
                    Toast.makeText(getApplicationContext(), R.string.invalidLocationOrCuisine,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent main = new Intent(SearchActivity.this, MainActivity.class);
                main.putExtra("location", locationEditText.getText());
                main.putExtra("cuisine", cuisineEditText.getText());
                SearchActivity.this.startActivity(main);
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

        searchListData.addAll(locations);

        searchListView.setAdapter(new ArrayAdapter<String>(this, R.layout.row_location_cuisine, searchListData));
    }
}
