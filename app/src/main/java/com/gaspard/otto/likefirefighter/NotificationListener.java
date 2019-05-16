package com.gaspard.otto.likefirefighter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;


public class NotificationListener extends NotificationListenerService {
    public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
    public static final String TAG = "XDDD";
//    Context context;
    //    https://medium.com/@polidea/how-to-respond-to-any-messaging-notification-on-android-7befa483e2d7
    //    https://github.com/Chagall/notification-listener-service-example

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "binded", Toast.LENGTH_LONG).show();
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Toast.makeText(this, "OK OOOOOK", Toast.LENGTH_LONG).show();

        handleNotification(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
            for (StatusBarNotification notification : this.getActiveNotifications()) {
                if (notification.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
                    handleNotification(notification);
                }
            }
        }
    }

    private void handleNotification(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
            Bundle extras = sbn.getNotification().extras;
            String title = extras.getString("android.title");
            String text = extras.getCharSequence("android.text").toString();
            Log.d(TAG, "handleNotification: " + title + " -> " + text);
            Notification.Action[] actions = sbn.getNotification().actions;
            if (actions != null) {
                for (Notification.Action action : actions) {
                    if (action.title == "Like") {
                        try {
                            action.actionIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(this, "like firefighter :)", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
