package com.robpercival.demoapp.activities;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.ConfirmCancelAttendancePresenter;
import com.robpercival.demoapp.rest.dto.user.UserDTO;
import com.robpercival.demoapp.services.FirebaseIDService;
import com.robpercival.demoapp.state.ApplicationState;

import java.io.IOException;

public class ConfirmCancelAttendanceActivity extends AppCompatActivity implements ConfirmCancelAttendancePresenter.ConfirmCancelAttendanceView{

    private DrawerLayout drawerLayout;
    private ConfirmCancelAttendancePresenter presenter;
    private long reservationId;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_invite_friends);

        setTitle("Confirm or cancel attendance");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reservationId = extras.getLong("reservationId");
        }


        Button confirmButton = (Button) findViewById(R.id.confirmAttendanceButton);



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onConfirmAttendance(reservationId);
            }
        });
        Button cancelButton = (Button) findViewById(R.id.cancelAttendanceButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.onCancelAttendance(reservationId);
            }
        });


        setupDrawerAndToolbar();

        presenter = new ConfirmCancelAttendancePresenter(this);

      }

    public void goToOffline(View v) {
        Intent i = new Intent(ConfirmCancelAttendanceActivity.this, OfflineMyReservations.class);
        startActivity(i);
    }

    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar_confirm_cancel_attendance);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout_confirm_cancel_attendance);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view_confirm_cancel_attendance);
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

                Intent login = new Intent(ConfirmCancelAttendanceActivity.this, LoginActivity.class);
                ConfirmCancelAttendanceActivity.this.startActivity(login);

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
                intent = new Intent(ConfirmCancelAttendanceActivity.this, MainActivity.class);
                ConfirmCancelAttendanceActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                intent = new Intent(ConfirmCancelAttendanceActivity.this, MyReservationsActivity.class);
                ConfirmCancelAttendanceActivity.this.startActivity(intent);
                //MainActivity.this.finish();
                break;
            case R.id.nav_menu4:
                intent = new Intent(ConfirmCancelAttendanceActivity.this, ChangePasswordActivity.class);
                ConfirmCancelAttendanceActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu5:
                intent = new Intent(ConfirmCancelAttendanceActivity.this, SearchActivity.class);
                ConfirmCancelAttendanceActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_invite_friends, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_invite_friends);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onConfirmSuccess() {
        Toast.makeText(getApplicationContext(), "You confirmed your attendance", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ConfirmCancelAttendanceActivity.this, SearchActivity.class);
        ConfirmCancelAttendanceActivity.this.startActivity(intent);

    }

    @Override
    public void onCancelSuccess() {
        Toast.makeText(getApplicationContext(), "You canceled your attendance", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ConfirmCancelAttendanceActivity.this, SearchActivity.class);
        ConfirmCancelAttendanceActivity.this.startActivity(intent);
    }
}