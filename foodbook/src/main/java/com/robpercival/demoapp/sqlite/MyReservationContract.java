package com.robpercival.demoapp.sqlite;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

public class MyReservationContract {

    public static ContentValues toContentValues(com.robpercival.demoapp.sqlite.MyReservation myReservation) {
        ContentValues values = new ContentValues();
        values.put(MyReservationColumns.RESERVATION_ID, myReservation.getReservationId());
        values.put(MyReservationColumns.RESERVATION_DATE, myReservation.getReservationDate());
        values.put(MyReservationColumns.RESTAURANT_NAME, myReservation.getRestaurantName());
        return values;
    }

        interface MyReservationColumns {
            String RESERVATION_ID = "id";
            String RESERVATION_DATE = "reservation_date";
            String RESTAURANT_NAME = "restaurant_name";
        }

        // Used to access the content
        public static final String CONTENT_AUTHORITY = "com.robpercival.demoapp.sqlite.provider";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

        private static final String PATH_MY_RESERVATION = "myReservation";

        public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() +"/"+PATH_MY_RESERVATION );

        public static final String[] TOP_LEVEL_PATHS = {PATH_MY_RESERVATION};

        // Table for country
        public static class MyReservation implements MyReservationColumns, BaseColumns {

            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MY_RESERVATION).build();

            // Accessing content directory and item
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd."+CONTENT_AUTHORITY+".myReservation";
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."+CONTENT_AUTHORITY+".myReservation";

            public static Uri buildReservationUri(String reservationId){
                return CONTENT_URI.buildUpon().appendEncodedPath(reservationId).build();
            }

            public static String getReservationId(Uri uri){
                return uri.getPathSegments().get(1);
            }

        }
}
