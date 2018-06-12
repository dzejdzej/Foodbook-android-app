package com.robpercival.demoapp.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.SearchPresenter;
import com.robpercival.demoapp.rest.dto.user.ReservationRequestDTO;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.services.FirebaseIDService;
import com.robpercival.demoapp.state.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements SearchPresenter.SearchView{

    private DrawerLayout drawerLayout;
    private ListView searchListView;
    private ArrayList<String> searchListData = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> cuisines = new ArrayList<>();
    private AutoCompleteTextView cityAutocompleteTextView;
    private AutoCompleteTextView cuisineAutocompleteTextView;
    private EditText duration;
    private EditText numberOfSeats;
    private SearchPresenter presenter;
    private ProgressBar pgsBar;
    private View searchView;
    private Button datePickerButton, timePickerButton;
    private TextView timePickedText;
    public static Date RESERVATION_DATE;
    public static String RESERVATION_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(R.string.searchRestaurant);

        pgsBar = (ProgressBar)findViewById(R.id.loader);
        searchView = findViewById(R.id.searchLayout);


        timePickedText = (TextView)findViewById(R.id.timePickedText);

        datePickerButton = (Button)findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                DialogFragment dialogfragment = new DatePickerDialogTheme2();

                dialogfragment.show(getFragmentManager(), "Theme 2");

            }
        });


        timePickerButton = (Button)findViewById(R.id.timePickerButton);
        timePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SearchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        RESERVATION_TIME = selectedHour + ":" + selectedMinute;
                        timePickedText.setText(RESERVATION_TIME);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        timePickedText = (TextView)findViewById(R.id.timePickedText);

        datePickerButton = (Button)findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                DialogFragment dialogfragment = new DatePickerDialogTheme2();

                dialogfragment.show(getFragmentManager(), "Theme 2");

            }
        });


        timePickerButton = (Button)findViewById(R.id.timePickerButton);
        timePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SearchActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        RESERVATION_TIME = selectedHour + ":" + selectedMinute;
                        timePickedText.setText(RESERVATION_TIME);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        setupDrawerAndToolbar();

        presenter = new SearchPresenter(this);
        populateArrays();
        setupAutocompleteTexts();
        //setupViewEvents();

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!locations.contains(cityAutocompleteTextView.getText().toString()) || !cuisines.contains(cuisineAutocompleteTextView.getText().toString())){
                    Toast.makeText(getApplicationContext(), R.string.invalidLocationOrCuisine,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                presenter.onSearchClick();


            }
        });
    }



    public static class DatePickerDialogTheme2 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            TextView textview = (TextView)getActivity().findViewById(R.id.datePickedText);
            RESERVATION_DATE = getDateFromDatePicker(view);

            textview.setText(day + ":" + (month+1) + ":" + year);

        }
    }

    private void setupAutocompleteTexts() {

        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, locations);
//        AutoCompleteTextView textView = (AutoCompleteTextView)
//                findViewById(R.id.countries_list);
//        textView.setAdapter(adapter);

        cityAutocompleteTextView = (AutoCompleteTextView)findViewById(R.id.locationAutocompleteText);
        cityAutocompleteTextView.setAdapter(adapterLocations);
//        cityEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    generateListViewData(true, ((EditText)view).getText().toString());
//                    System.out.println(((EditText)view).getText().toString());
//                }
//            }
//        });
//        cityEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                generateListViewData(true, charSequence.toString());
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//        });

        ArrayAdapter<String> adapterCuisines = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, cuisines);

        cuisineAutocompleteTextView = findViewById(R.id.cuisineAutocompleteText);
        cuisineAutocompleteTextView.setAdapter(adapterCuisines);
//        cuisineAutocompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    generateListViewData(false, ((EditText)v).getText().toString());
//                }
//            }
//        });
//        cuisineAutocompleteTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                generateListViewData(false, charSequence.toString());
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//        });
        duration = findViewById(R.id.durationEditText);
        numberOfSeats = findViewById(R.id.numberOfSeatsEditText);
    }

//    private void setupViewEvents() {
//        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                EditText locationEditText = findViewById(R.id.locationEditText);
//
//                if(locationEditText.hasFocus()) {
//                    locationEditText.setText(((TextView) view).getText().toString());
//                } else {
//                    EditText cuisineEditText = findViewById(R.id.cuisineEditText);
//                    cuisineEditText.setText(((TextView) view).getText().toString());
//                }
//            }
//        });
//    }

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



    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar_search);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout_search);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view_search);
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

                Intent login = new Intent(SearchActivity.this, LoginActivity.class);
                SearchActivity.this.startActivity(login);

                try {
                    UserDTO dto = (UserDTO) ApplicationState.getInstance().getItem("UserDTO");

                    FirebaseIDService.unsubscribe(dto.getUserId());

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
                intent = new Intent(SearchActivity.this, MainActivity.class);
                SearchActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                intent = new Intent(SearchActivity.this, MyReservationsActivity.class);
                SearchActivity.this.startActivity(intent);
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
                            Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Locale locale = new Locale("en");
                            Configuration config = getBaseContext().getResources().getConfiguration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                            Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
                break;
            case R.id.nav_menu4:
                intent = new Intent(SearchActivity.this, ChangePasswordActivity.class);
                SearchActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu5:
                intent = new Intent(SearchActivity.this, SearchActivity.class);
                SearchActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_search, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_search);
        drawer.closeDrawer(GravityCompat.START);
    }









    private void populateArrays(){
        // searchListView = findViewById(R.id.searchListView);

        locations.add("Novi Sad");
        locations.add("Novi Beograd");
        locations.add("Beograd");
        //locations.add("Paris");

        cuisines.add("Italian");
        cuisines.add("Greek");
        cuisines.add("Serbian");
        cuisines.add("Chinese");

        searchListData.addAll(locations);

        // searchListView.setAdapter(new ArrayAdapter<String>(this, R.layout.row_location_cuisine, searchListData));
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
        //SearchActivity.this.finish();
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    @Override
    public Date getReservationDate() {
        return RESERVATION_DATE;
    }

    @Override
    public String getReservationTime() {
        return RESERVATION_TIME;
    }

    @Override
    public String getCuisineType() {
        return cuisineAutocompleteTextView.getText().toString();
    }

    @Override
    public String getCity() {
        return cityAutocompleteTextView.getText().toString();
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

