package com.example.saravanamurali.farmersgen.firebasepushnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.util.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class FirebaseInstanceService extends FirebaseMessagingService {


    public FirebaseInstanceService() {
    }


    SessionManager sessionManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String s=remoteMessage.getData().get("title");
        String ss=remoteMessage.getData().get("message");
      //  String action=remoteMessage.getNotification().getClickAction();
        System.out.println("SSSS"+s+"SSSSSPPP"+ss+"ClickAction");

        pushNotificationBuilder(s,ss);

        System.out.println(remoteMessage.getData().get("title"));
        System.out.println(remoteMessage.getData().get("message"));

        /*System.out.println(remoteMessage.getNotification().getTitle());
        System.out.println(remoteMessage.getNotification().getBody().toString());
        System.out.println(remoteMessage.getNotification().getClickAction().toString());

        sendNotitfication(remoteMessage.getData());


       *//* sendMessage(remoteMessage);*//*

        if(remoteMessage.getNotification().getTitle()!=null && remoteMessage.getNotification().getBody()!=null && remoteMessage.getNotification().getClickAction()!=null) {

            pushNotificationBuilder(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getClickAction());
        }
        else if(remoteMessage.getNotification().getTitle()==null || remoteMessage.getNotification().getBody()==null || remoteMessage.getNotification().getClickAction()==null) {

            System.out.println("Null Values");
        }*/

    }

    private void sendNotitfication(Map<String, String> remoteMessage) {





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

    private void pushNotificationBuilder(String title, String body) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="com.example.saravanamurali.farmersgen.notification";

        // Here pass your activity where you want to redirect.
        //Intent intent = new Intent(clickAction);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setContentTitle(title)
                .setContentText(body)
                //.setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setPriority(1)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.splashscreenlogo);



        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){

            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("FG Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            //nBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);

        }




        notificationManager.notify(new Random().nextInt(), nBuilder.build());


    }


}
