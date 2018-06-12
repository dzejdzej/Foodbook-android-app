package com.robpercival.demoapp.activities;


import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.ChangePasswordPresenter;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.services.FirebaseIDService;
import com.robpercival.demoapp.state.ApplicationState;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by User on 6/10/2018.
 */

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordPresenter.ChangePasswordView {

    private DrawerLayout drawerLayout;
    private ChangePasswordPresenter presenter;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatedNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setTitle(R.string.changePassword);

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

        setupDrawerAndToolbar();

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


    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar_change_password);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout_change_password);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view_change_password);
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

                Intent login = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                ChangePasswordActivity.this.startActivity(login);

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
                intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                ChangePasswordActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                intent = new Intent(ChangePasswordActivity.this, MyReservationsActivity.class);
                ChangePasswordActivity.this.startActivity(intent);
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
                            Intent intent = new Intent(ChangePasswordActivity.this, ChangePasswordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Locale locale = new Locale("en");
                            Configuration config = getBaseContext().getResources().getConfiguration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                            Intent intent = new Intent(ChangePasswordActivity.this, ChangePasswordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
                break;
            case R.id.nav_menu4:
               return;
            case R.id.nav_menu5:
                intent = new Intent(ChangePasswordActivity.this, SearchActivity.class);
                ChangePasswordActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_change_password, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_change_password);
        drawer.closeDrawer(GravityCompat.START);
    }

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


