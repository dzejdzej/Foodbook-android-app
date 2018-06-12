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
import com.robpercival.demoapp.presenter.RegistrationPresenter;

import java.util.ArrayList;

//import static com.robpercival.demoapp.R.id.cuisineEditText;
//import static com.robpercival.demoapp.R.id.email;

public class RegistrationActivity extends Activity implements RegistrationPresenter.RegistrationView {

    private EditText passwordTxt;
    private EditText repeatPasswordTxt;
    private EditText usernameTxt;
    private EditText nameTxt;
    private EditText surnameTxt;
    private EditText emailTxt;
    private EditText addressTxt;

    private EditText countryEditText;
    private EditText cityEditText;

    private ListView countryListView;
    private ListView cityListView;
    private ArrayList<String> searchListData = new ArrayList<>();
    private ArrayList<String> countries = new ArrayList<>();
    private ArrayList<String> cities = new ArrayList<>();

    RegistrationPresenter registrationPresenter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_registration);

        Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerAndGotoLoginActivity();
            }
        });


        passwordTxt = findViewById(R.id.passwordInput);
        repeatPasswordTxt = findViewById(R.id.repeatPasswordInput);
        passwordTxt.addTextChangedListener(textViewer);
        repeatPasswordTxt.addTextChangedListener(textViewer);

        usernameTxt = findViewById(R.id.usernameInput);
        nameTxt = findViewById(R.id.nameInput);
        surnameTxt = findViewById(R.id.surnameInput);
        emailTxt = findViewById(R.id.emailInput);
        addressTxt = findViewById(R.id.addressInput);

        //countryEditText = findViewById(R.id.countryInput);
        //cityEditText = findViewById(R.id.cityInput);
        registrationPresenter = new RegistrationPresenter(this);

        //populateArrays();
        //setupEditTexts();
        //setupViewEvents();

    }


    private final TextWatcher textViewer = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable s) {

            // you can call or do what you want with your EditText here
            //
            String firstPassword = passwordTxt.getText().toString();
            String secondPassword = repeatPasswordTxt.getText().toString();

            if(firstPassword == null || secondPassword == null) {
                return;
            }

            if(!firstPassword.equals(secondPassword)) {
                Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };



    private void registerAndGotoLoginActivity(){

        registrationPresenter.onRegistrationClick(nameTxt.getText().toString(), surnameTxt.getText().toString(), usernameTxt.getText().toString(), passwordTxt.getText().toString(), emailTxt.getText().toString(), addressTxt.getText().toString());


    }

    @Override
    public void onRegistrationFail(String reason) {
        Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRegistrationSuccess() {
        Intent loginActivity = new Intent(RegistrationActivity.this, LoginActivity.class);
        RegistrationActivity.this.startActivity(loginActivity);
        //RegistrationActivity.this.finish();
    }

    @Override
    public void setCountriesAndCities() {

    }
}
