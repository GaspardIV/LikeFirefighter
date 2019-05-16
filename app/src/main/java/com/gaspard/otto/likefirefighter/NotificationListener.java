package com.gaspard.otto.likefirefighter;

import android.app.Notification;
import android.app.PendingIntent;
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
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "unbinded", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        handleNotification(sbn);
        super.onNotificationPosted(sbn);
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
        if (isMessengerNotification(sbn) && isLikeMessage(sbn)) {
            sendLike(sbn);
        }
    }

    private void sendLike(StatusBarNotification sbn) {
        Notification.Action[] actions = sbn.getNotification().actions;
        if (actions != null) {
            for (Notification.Action action : actions) {
                if (action.title.equals("Like")) {
                    try {
                        action.actionIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Like Firefighter :)", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isLikeMessage(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        CharSequence text = extras.getCharSequence("android.text")/*.length()*/;
        return text != null && text.length() == 2 && (int) text.charAt(0) == 55357 && (int) text.charAt(1) == 56397;
    }

    private boolean isMessengerNotification(StatusBarNotification sbn) {
        return sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME);
    }
}
