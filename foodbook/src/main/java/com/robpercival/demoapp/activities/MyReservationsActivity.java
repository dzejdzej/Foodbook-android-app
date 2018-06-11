package com.robpercival.demoapp.activities;

import com.robpercival.demoapp.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.RowReservationAdapter;

import com.robpercival.demoapp.fragments.Menu1Fragment;
import com.robpercival.demoapp.fragments.Menu3Fragment;
import com.robpercival.demoapp.fragments.Menu4Fragment;
import com.robpercival.demoapp.fragments.Menu5Fragment;

import com.robpercival.demoapp.presenter.MyReservationsPresenter;
import com.robpercival.demoapp.rest.dto.ReservationDTO;
import com.robpercival.demoapp.rest.dto.user.ChangePasswordDTO;

import java.util.List;

public class MyReservationsActivity extends AppCompatActivity implements MyReservationsPresenter.MyReservationsView {


    private DrawerLayout drawerLayout;
    private MyReservationsPresenter presenter;
    private List<ReservationDTO> reservations;
    private ListView reservationsListView;
    private RowReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        setTitle(R.string.myReservations);
        setupDrawerAndToolbar();

        presenter = new MyReservationsPresenter(this);
        presenter.getAllReservationsForUser();
    }



    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar_my_reservations);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout_my_reservations);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view_my_reservations);
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
                intent = new Intent(MyReservationsActivity.this, MainActivity.class);
                MyReservationsActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                return;
            case R.id.nav_menu3:
                //dodati rad sa mapom
                break;
            case R.id.nav_menu4:
                intent = new Intent(MyReservationsActivity.this, ChangePasswordActivity.class);
                MyReservationsActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu5:
                intent = new Intent(MyReservationsActivity.this, SearchActivity.class);
                MyReservationsActivity.this.startActivity(intent);
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_my_reservations, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_my_reservations);
        drawer.closeDrawer(GravityCompat.START);

    }



    @Override
    public void onGetReservationsFail(String s) {
    }

    @Override
    public void onGetReservationsSuccess() {

    }

    @Override
    public void onPopulateReservations(List<ReservationDTO> reservations) {

        this.reservations = reservations;
        reservationsListView = findViewById(R.id.reservationsListView);
        // izvuci iz bundle listu
        //
        /*List<String> stockList = new ArrayList<String>();
        stockList.add("stock1");
        stockList.add("stock2");

        String[] stockArr = new String[stockList.size()];
        stockArr = stockList.toArray(stockArr);*/
        //ReservationResponseDTO[] dtoArray = new ReservationResponseDTO[this.dtos.size()];
        //ReservationResponseDTO[] dtoArray =  (ReservationResponseDTO[]) this.dtos.toArray( new ReservationResponseDTO[this.dtos.size()]);


        adapter = new RowReservationAdapter(this, this.reservations);

        reservationsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelReservation(List<ReservationDTO> reservations) {
        this.reservations.clear();
        this.reservations.addAll(reservations);
        adapter.notifyDataSetChanged();
    }

    public void onCancelClicked(ReservationDTO dto) {
        presenter.onCancelClicked(dto);
    }
}
