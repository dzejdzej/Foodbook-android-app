package com.robpercival.demoapp.activities;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.InviteFriendsPresenter;
import com.robpercival.demoapp.state.ApplicationState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 6/3/2018.
 */

public class InviteFriendsActivity extends AppCompatActivity implements InviteFriendsPresenter.InviteFriendsView{

    private DrawerLayout drawerLayout;
    private InviteFriendsPresenter presenter;
    private EditText friendsEmail;
    private List<String> invitedFriends = new ArrayList<String>();
    private ListView invitedFriendsListView;
    private long reservationId;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_invite_friends);

        setTitle("Invite friends");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reservationId = extras.getLong("reservationId");
            ApplicationState.getInstance().setItem("reservationIdInviteFriends", reservationId);
        } else {
            reservationId = (long) ApplicationState.getInstance().getItem("reservationIdInviteFriends");
        }


        Button inviteButton = (Button) findViewById(R.id.inviteButton);


        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = friendsEmail.getText().toString();
                email = email.toLowerCase();
                invitedFriends.remove(email);
                invitedFriends.add(email);
                adapter.notifyDataSetChanged();
            }
        });

        Button finishButton = (Button) findViewById(R.id.finishButton);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFinishClicked(invitedFriends, reservationId);
            }
        });
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2 = new Intent(InviteFriendsActivity.this, MyReservationsActivity.class);
                InviteFriendsActivity.this.startActivity(activity2);
                InviteFriendsActivity.this.finish();
            }
        });

        friendsEmail = (EditText) findViewById(R.id.friendsEmail);

        setupDrawerAndToolbar();

        presenter = new InviteFriendsPresenter(this);

        // Get ListView object from xml
        invitedFriendsListView = (ListView) findViewById(R.id.invitedFriendsListView);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, invitedFriends);


        // Assign adapter to ListView
        invitedFriendsListView.setAdapter(adapter);

        // ListView Item Click Listener
        invitedFriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) invitedFriendsListView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }



    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar_invite_friends);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        this.drawerLayout = findViewById(R.id.drawer_layout_invite_friends);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);

        NavigationView navigationView = findViewById(R.id.nav_view_invite_friends);
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
                intent = new Intent(InviteFriendsActivity.this, MainActivity.class);
                InviteFriendsActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu2:
                intent = new Intent(InviteFriendsActivity.this, MyReservationsActivity.class);
                InviteFriendsActivity.this.startActivity(intent);
                //MainActivity.this.finish();
                break;
            case R.id.nav_menu3:
                //rad sa mapom implementirati - da se otvori mapa sa svim restoranima u tom gradu
                break;
            case R.id.nav_menu4:
                intent = new Intent(InviteFriendsActivity.this, ChangePasswordActivity.class);
                InviteFriendsActivity.this.startActivity(intent);
                break;
            case R.id.nav_menu5:
                intent = new Intent(InviteFriendsActivity.this, SearchActivity.class);
                InviteFriendsActivity.this.startActivity(intent);
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



    private void onFinishClicked() {
        presenter.onFinishClicked(invitedFriends, reservationId);


    }

    @Override
    public void onFinishFail() {

    }

    @Override
    public void onFinishSuccess() {
        Intent activity = new Intent(InviteFriendsActivity.this, MyReservationsActivity.class);
        InviteFriendsActivity.this.startActivity(activity);
        InviteFriendsActivity.this.finish();
    }
}