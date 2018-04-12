package com.robpercival.demoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.robpercival.demoapp.R;

public class RegistrationActivity extends Activity {

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
    }

    private void registerAndGotoLoginActivity(){
        Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        RegistrationActivity.this.startActivity(loginIntent);
        RegistrationActivity.this.finish();
    }

}
