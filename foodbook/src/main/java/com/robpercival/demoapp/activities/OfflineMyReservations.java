package com.robpercival.demoapp.activities;

import android.content.ContentResolver;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.MyOfflineReservationAdapter;
import com.robpercival.demoapp.sqlite.MyReservation;
import com.robpercival.demoapp.sqlite.MyReservationContract;
import com.robpercival.demoapp.sqlite.MyReservationLoader;

import java.util.ArrayList;
import java.util.List;

public class OfflineMyReservations extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<MyReservation>> {

    private ContentResolver mContentResolver;
    private MyOfflineReservationAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ListView myReservationsListView;
    private List<MyReservation> myReservations = new ArrayList<MyReservation>();
    private TextView txtNoData;


    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of OfflineFragment");
        super.onResume();
        // refresh our data
        getSupportLoaderManager ().initLoader(LOADER_ID, null,  this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offline_my_reservations);
        myReservationsListView  = (ListView) findViewById(R.id.myReservationsListView);
        //txtNoData = (TextView) findViewById(R.id.txtNoData);
        mAdapter = new MyOfflineReservationAdapter(this, myReservations);
        myReservationsListView.setAdapter(mAdapter);

        getSupportLoaderManager ().initLoader(LOADER_ID, null,  this);
    }


    @Override
    public Loader<List<MyReservation>> onCreateLoader(int i, Bundle args) {

        mContentResolver = OfflineMyReservations.this.getContentResolver();
        return new MyReservationLoader(this, MyReservationContract.URI_TABLE, mContentResolver);
    }


    @Override
    public void onLoadFinished(Loader<List<MyReservation>> loader, List<MyReservation> reservations) {
        if(reservations.size() == 0) {
            //txtNoData.setVisibility(View.VISIBLE);
            return;
        }
        myReservations.clear();
        myReservations.addAll(reservations);
        mAdapter.notifyDataSetChanged();
        myReservationsListView.refreshDrawableState();

    }

    @Override
    public void onLoaderReset(Loader<List<MyReservation>> loader) {
        myReservations.clear();
        mAdapter.notifyDataSetChanged();
    }



}
