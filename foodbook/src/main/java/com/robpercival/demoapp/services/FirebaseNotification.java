package com.robpercival.demoapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.activities.SearchActivity;

import java.util.Map;
import java.util.Random;

public class FirebaseNotification extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());


        int notificationId = new Random().nextInt(60000);

        String type = remoteMessage.getData().get("notificationType");
        if("Invited".equals(type)) {
            buildIntentNotification(remoteMessage.getData());
            return;
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, TAG)
                .setSmallIcon(R.mipmap.logo2)
                .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
                .setContentText(remoteMessage.getData().get("infoMessage")) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }

    private void buildIntentNotification(Map<String, String> data) {

        long reservationId = Long.parseLong(data.get("inviteReservationId"));
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int notificationId = new Random().nextInt(60000);


        Intent notificationIntent = new Intent(getApplicationContext(), SearchActivity.class);
        notificationIntent.putExtra("item_id", "1001"); // <-- HERE I PUT THE EXTRA VALUE
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, TAG)
                .setSmallIcon(R.mipmap.logo2)
                .setContentTitle(data.get("title")) //the "title" value you sent in your notification
                .setContentText(data.get("infoMessage")) //ditto
                .setContentIntent(contentIntent)
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

    }

}
