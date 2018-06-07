package com.robpercival.demoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.presenter.InviteFriendsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 6/3/2018.
 */

public class InviteFriendsActivity extends Activity implements InviteFriendsPresenter.InviteFriendsView{

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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reservationId = extras.getLong("reservationId");
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

