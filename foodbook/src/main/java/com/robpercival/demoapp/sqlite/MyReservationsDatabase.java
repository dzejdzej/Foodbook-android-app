package com.robpercival.demoapp.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MyReservationsDatabase extends SQLiteOpenHelper {
    private static final String TAG = MyReservationsDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "country.db";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    interface Tables{
        String MY_RESERVATIONS = "MyReservations";
    }

    public MyReservationsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a SQLite DB on create
        db.execSQL("CREATE TABLE " + Tables.MY_RESERVATIONS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyReservationContract.MyReservationColumns.RESERVATION_ID + " INTEGER NOT NULL,"
                + MyReservationContract.MyReservationColumns.RESERVATION_DATE + " TEXT NOT NULL,"
                + MyReservationContract.MyReservationColumns.RESTAURANT_NAME + " TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void deleteDatabse(Context context){

        // Delete the database
        context.deleteDatabase(DATABASE_NAME);
    }
}