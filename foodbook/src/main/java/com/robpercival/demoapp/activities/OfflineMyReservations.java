package com.robpercival.demoapp.activities;

import android.app.LauncherActivity;
import android.content.ContentResolver;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.MyOfflineReservationAdapter;
import com.robpercival.demoapp.sqlite.MyReservation;
import com.robpercival.demoapp.sqlite.MyReservationContract;
import com.robpercival.demoapp.sqlite.MyReservationLoader;

import java.util.List;

public class OfflineMyReservations extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<MyReservation>> {

    private ContentResolver mContentResolver;
    private MyOfflineReservationAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ListView myReservationsListView;
    private List<MyReservation> myReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offline_my_reservations);
        myReservationsListView  = (ListView) findViewById(R.id.myReservationsListView);

        getSupportLoaderManager ().initLoader(LOADER_ID, null,  this);
    }


    @Override
    public Loader<List<MyReservation>> onCreateLoader(int i, Bundle args) {

        mContentResolver = OfflineMyReservations.this.getContentResolver();

        return new MyReservationLoader(this, MyReservationContract.URI_TABLE, mContentResolver);
    }


    @Override
    public void onLoadFinished(Loader<List<MyReservation>> loader, List<MyReservation> reservations) {
        myReservations = reservations;
        mAdapter = new MyOfflineReservationAdapter(this, reservations);
        myReservationsListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<MyReservation>> loader) {
        myReservations.clear();
        mAdapter.notifyDataSetChanged();
    }



}
