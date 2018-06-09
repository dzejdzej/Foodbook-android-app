package com.robpercival.demoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.ChangePasswordPresenter;

import static com.robpercival.demoapp.R.id.editTxtUsername;

public class ChangePasswordActivity extends Activity implements ChangePasswordPresenter.ChangePasswordView {

    private ChangePasswordPresenter presenter;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatedNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        repeatedNewPassword = (EditText) findViewById(R.id.repeatNewPassword);


        newPassword.addTextChangedListener(textViewer);
        repeatedNewPassword.addTextChangedListener(textViewer);

        Button confirmButton = (Button) findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMainActivity();
            }
        });


        presenter = new ChangePasswordPresenter(this);
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
            String firstPassword = newPassword.getText().toString();
            String secondPassword = repeatedNewPassword.getText().toString();

            if (firstPassword == null || secondPassword == null) {
                return;
            }

            if (!firstPassword.equals(secondPassword)) {
                Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };


    private void gotoMainActivity() {
        //
        if (oldPassword.getText().toString().length() != 0 && newPassword.getText().toString().length() != 0 && repeatedNewPassword.getText().toString().length() != 0)
            presenter.onChangePasswordClick(oldPassword.getText().toString(), newPassword.getText().toString());
        else
            Toast.makeText(getApplicationContext(), "All fields have to be inserted!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangePasswordFail() {
        Toast.makeText(getApplicationContext(), "Change password failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangePasswordSuccess() {
        Intent mainActivity = new Intent(ChangePasswordActivity.this, MainActivity.class);
        ChangePasswordActivity.this.startActivity(mainActivity);
    }

}