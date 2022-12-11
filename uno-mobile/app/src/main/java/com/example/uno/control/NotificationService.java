package com.example.uno.control;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.uno.R;
import com.example.uno.TelaInicial;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    private String token;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        this.token = s;
        System.out.println("NEW_TOKEN: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            System.out.println("Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            System.out.println("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

}
