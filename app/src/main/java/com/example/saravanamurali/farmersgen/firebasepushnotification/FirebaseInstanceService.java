package com.example.saravanamurali.farmersgen.firebasepushnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.util.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseInstanceService extends FirebaseMessagingService {


    public FirebaseInstanceService() {
    }


    SessionManager sessionManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        pushNotificationBuilder(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction());

    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);


        System.out.println("FCMToken" + s);

        if (s != null) {

            sessionManager = new SessionManager(FirebaseInstanceService.this);
            sessionManager.storeFCMToken(s);

        }
        if (s == null) {
            return;
        }

    }

    private void pushNotificationBuilder(String title, String body, String clickAction) {

        // Here pass your activity where you want to redirect.
        Intent intent = new Intent(clickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setContentTitle(title)
                .setContentText(body)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setPriority(1)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.splashscreenlogo);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(), nBuilder.build());


    }


}
