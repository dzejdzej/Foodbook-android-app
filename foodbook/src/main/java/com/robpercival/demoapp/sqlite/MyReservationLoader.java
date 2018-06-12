package com.robpercival.demoapp.sqlite;


import android.annotation.TargetApi;
import android.support.v4.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class MyReservationLoader extends AsyncTaskLoader<List<MyReservation>> {
        private static final String LOG_TAG = MyReservationLoader.class.getSimpleName();
        private List<MyReservation> mMyReservation;
        private ContentResolver mContentResolver;
        private Cursor mCursor;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public MyReservationLoader(Context context, Uri uri, ContentResolver contentResolver){
            super(context);
            mContentResolver = contentResolver;
        }

        @Override
        public List<MyReservation> loadInBackground() {
            String[] projection = {BaseColumns._ID,
                    MyReservationContract.MyReservationColumns.RESERVATION_ID,
                    MyReservationContract.MyReservationColumns.RESTAURANT_NAME,
                    MyReservationContract.MyReservationColumns.RESERVATION_DATE};

            List<MyReservation> entries = new ArrayList<MyReservation>();

            mCursor = mContentResolver.query(MyReservationContract.URI_TABLE, projection, null, null, null);

            if(mCursor != null){
                if(mCursor.moveToFirst()){
                    do{
                        int _id = mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));
                        int reservationId = mCursor.getInt(mCursor.getColumnIndex(MyReservationContract.MyReservationColumns.RESERVATION_ID));
                        String reservationDate = mCursor.getString(mCursor.getColumnIndex(MyReservationContract.MyReservationColumns.RESERVATION_DATE));
                        String restaurantName = mCursor.getString(mCursor.getColumnIndex(MyReservationContract.MyReservationColumns.RESTAURANT_NAME));
                        MyReservation myReservation = new MyReservation(reservationId, reservationDate, restaurantName);
                        entries.add(myReservation);
                    }while(mCursor.moveToNext());
                }
            }
            return entries;
        }

        @Override
        public void deliverResult(List<MyReservation> myReservations) {

            if(isReset()){
                if(myReservations != null){
                    mCursor.close();
                }
            }

            List<MyReservation> oldMyReservationList = mMyReservation;

            if(mMyReservation == null || mMyReservation.size() == 0){
                Log.d(LOG_TAG, "++++++++++++ NO DATA RETURNED ");
            }

            // Saving the country
            mMyReservation = myReservations;

            if(isStarted()){
                super.deliverResult(myReservations);
            }

            if(oldMyReservationList != myReservations){
                mCursor.close();
            }
        }

        @Override
        protected void onStartLoading() {
            if(mMyReservation != null){
                deliverResult(mMyReservation);
            }

            if(takeContentChanged() || mMyReservation == null){
                forceLoad();
            }
        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

        @Override
        protected void onReset() {
            onStopLoading();
            if(mCursor != null){
                mCursor.close();
            }

            mMyReservation = null;
        }

        @Override
        public void onCanceled(List<MyReservation> country) {
            super.onCanceled(country);
            if(mCursor != null){
                mCursor.close();
            }
        }

        @Override
        public void forceLoad() {
            super.forceLoad();
        }
    }

