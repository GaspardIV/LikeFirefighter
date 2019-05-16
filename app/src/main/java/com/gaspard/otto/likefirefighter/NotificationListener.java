package com.gaspard.otto.likefirefighter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;


public class NotificationListener extends NotificationListenerService {
    public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
    public static final String TAG = "XDDD";

    //    https://medium.com/@polidea/how-to-respond-to-any-messaging-notification-on-android-7befa483e2d7
    //    https://github.com/Chagall/notification-listener-service-example

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        handleNotification(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
//            Toast.makeText(this, "wez", Toast.LENGTH_LONG).show();
            for (StatusBarNotification notification : this.getActiveNotifications()) {
                if (notification.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
//                    Toast.makeText(this, "jeszcze", Toast.LENGTH_LONG).show();
                    handleNotification(notification);
                }
            }
        }
    }

    private void handleNotification(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
            Notification.Action[] actions = sbn.getNotification().actions;
            try {
                for (Notification.Action action : actions) {
                    if (action.title == "Like") {
                        action.actionIntent.send();
                    }
//                    Log.d(TAG, "onNotificationPosted: " + action.toString() + "   " + action.title);
                }
//                actions[0].actionIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
//            Log.d("XDD", "onNotificationPosted: " + sbn.getNotification().toString());
            Toast.makeText(this, "like firefighter :)", Toast.LENGTH_LONG).show();
        }
    }
}
