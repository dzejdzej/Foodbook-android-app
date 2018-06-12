package com.robpercival.demoapp.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.robpercival.demoapp.activities.MyReservationsActivity;
import com.robpercival.demoapp.activities.OfflineMyReservations;
import com.robpercival.demoapp.activities.SearchActivity;
import com.robpercival.demoapp.state.ApplicationState;

public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null

        boolean isOnOfflineActivity = ApplicationState.getInstance().getItem("offlineViewActive") != null;
        if (netInfo != null && netInfo.isConnected() && isOnOfflineActivity) {
            // Do something
            Log.d("Network Available ", "Flag No 1");
            ApplicationState.getInstance().setItem("offlineViewActive", null);
            // change back to my reservations and print a TOASTTTTT
            Toast.makeText(context, "Online!", Toast.LENGTH_LONG).show();
            Intent launchIntent = new Intent(context, SearchActivity.class);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
            return;
        }

        // if we are in airplane mode or we lost connection...
        if(netInfo == null || !netInfo.isConnected()) {
            // Do something
            Log.d("Network Not Available ", "Flag No 1");
            // change back to my reservations and print a TOASTTTTT
            Toast.makeText(context, "Offline!", Toast.LENGTH_LONG).show();
            ApplicationState.getInstance().setItem("offlineViewActive", true);
            Intent launchIntent = new Intent(context, OfflineMyReservations.class);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
            return;
        }




    }
}