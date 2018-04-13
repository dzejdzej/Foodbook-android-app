package com.robpercival.demoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.robpercival.demoapp.R;


public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_login);

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
    }

    private void gotoSearchActivity(){
        Intent searchActivity = new Intent(LoginActivity.this, SearchActivity.class);
        LoginActivity.this.startActivity(searchActivity);
        LoginActivity.this.finish();
    }

    private void gotoRegisterActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(loginIntent);
        LoginActivity.this.finish();
    }

}
