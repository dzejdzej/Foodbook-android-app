package com.robpercival.demoapp.activities;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.LoginPresenter;
import com.robpercival.demoapp.presenter.MainPresenter;


public class LoginActivity extends Activity implements LoginPresenter.LoginView {

    private LoginPresenter loginPresenter;
    private EditText editTxtUsername;
    private EditText editTxtPassword;

    private ContentResolver mContentResolver;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_login);


        mContentResolver = LoginActivity.this.getContentResolver();

        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSearchActivity();
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoRegisterActivity();
            }
        });

        editTxtUsername = (EditText) findViewById(R.id.editTxtUsername);
        editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
        loginPresenter = new LoginPresenter(this);
    }

    private void gotoSearchActivity(){
        //
        if(editTxtPassword.getText().toString().length()!=0 && editTxtUsername.getText().toString().length()!=0)
            loginPresenter.onLoginClick(editTxtUsername.getText().toString(), editTxtPassword.getText().toString());
        else
            Toast.makeText(getApplicationContext(), "Both username and password have to be inserted!", Toast.LENGTH_LONG).show();
    }

    private void gotoRegisterActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(loginIntent);
    }

    @Override
    public void onLoginFail() {
        Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess() {
        Intent searchActivity = new Intent(LoginActivity.this, SearchActivity.class);
        LoginActivity.this.startActivity(searchActivity);
    }

    @Override
    public ContentResolver getAndroidContentResolver() {
        return mContentResolver;
    }
}
