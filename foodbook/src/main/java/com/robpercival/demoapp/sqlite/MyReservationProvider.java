package com.robpercival.demoapp.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;



    public class MyReservationProvider extends ContentProvider {
        private MyReservationsDatabase mOpenHelper;

        private static String TAG = MyReservationProvider.class.getSimpleName();

        // Checks for valid URIs
        private static UriMatcher sUriMatcher = buildUriMatcher();

        private static final int MY_RESERVATION = 100;
        private static final int RESERVATION_ID = 101;

        private static UriMatcher buildUriMatcher(){

            // Initalize
            final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

            final String authority = MyReservationContract.CONTENT_AUTHORITY;

            // See if matches country records
            matcher.addURI(authority, "myReservation", MY_RESERVATION);

            // See if matches country item
            matcher.addURI(authority, "myReservation/#", RESERVATION_ID);

            return matcher;
        }

        @Override
        public boolean onCreate() {
            mOpenHelper = new MyReservationsDatabase(getContext());
            return true;
        }

        @Nullable
        @Override
        public String getType(Uri uri) {
            final int match = sUriMatcher.match(uri);
            switch(match){
                case MY_RESERVATION:
                    return MyReservationContract.MyReservation.CONTENT_TYPE;
                case RESERVATION_ID:
                    return MyReservationContract.MyReservation.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalArgumentException("Unknown URI : "+ uri);
            }
        }

        @Override
        public Bundle call(String method, String arg, Bundle extras) {
            if(method.equals("deleteAll")) {
                deleteAll();
            }
            return null;
        }


        private void deleteDatabase(){
            mOpenHelper.close();
            MyReservationsDatabase.deleteDatabse(getContext());
            mOpenHelper = new MyReservationsDatabase(getContext());
        }



        @Nullable
        @Override
        public Uri insert(Uri uri, ContentValues values) {
            Log.v(TAG,"insert(uri="+ uri + ", values="+values.toString());
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);
            switch (match){
                case MY_RESERVATION:
                    // Create a new record
                    long recordId = db.insertOrThrow(MyReservationsDatabase.Tables.MY_RESERVATIONS, null, values);
                    return MyReservationContract.MyReservation.buildReservationUri(String.valueOf(recordId));
                default:
                    throw new IllegalArgumentException("Unknown URI : "+ uri);
            }
        }


        public void deleteAll() {
            Log.v(TAG,"DELETE ALLLL");
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            db.execSQL("delete from "+ MyReservationsDatabase.Tables.MY_RESERVATIONS);
        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            Log.v(TAG,"delete(uri="+ uri);
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);

            switch(match){
                case MY_RESERVATION:
                    // Do nothing
                    break;
                case RESERVATION_ID:
                    String id = MyReservationContract.MyReservation.getReservationId(uri);
                    String selectionCriteria = BaseColumns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                    return db.delete(MyReservationsDatabase.Tables.MY_RESERVATIONS, selectionCriteria, selectionArgs);
                default:
                    throw new IllegalArgumentException("Unknown URI : " + uri);
            }

            return 0;
        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            Log.v(TAG,"update(uri="+ uri + ", values="+values.toString());
            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            final int match = sUriMatcher.match(uri);

            String selectionCriteria = selection;

            switch (match){
                case MY_RESERVATION:
                    // Do nothing
                    break;
                case RESERVATION_ID:
                    String id = MyReservationContract.MyReservation.getReservationId(uri);
                    selectionCriteria = BaseColumns._ID + "=" + id
                            + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI : "+ uri);
            }

            int updateCount = db.update(MyReservationsDatabase.Tables.MY_RESERVATIONS, values, selectionCriteria, selectionArgs);
            return updateCount;
        }

        @Nullable
        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
            final int match = sUriMatcher.match(uri);

            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

            queryBuilder.setTables(MyReservationsDatabase.Tables.MY_RESERVATIONS);

            switch(match){
                case MY_RESERVATION:
                    break;
                case RESERVATION_ID:
                    String id  = MyReservationContract.MyReservation.getReservationId(uri);
                    queryBuilder.appendWhere(BaseColumns._ID + "="+ id);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI : "+ uri);
            }

            // Projection : Columns to return
            Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }
    }

