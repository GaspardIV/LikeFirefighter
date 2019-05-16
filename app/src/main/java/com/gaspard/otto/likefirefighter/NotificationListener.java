package com.gaspard.otto.likefirefighter;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;


public class NotificationListener extends NotificationListenerService {
    public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";


    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
            Toast.makeText(this, "wez", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
//        int notificationCode = matchNotificationCode(sbn);
        if (sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
            Toast.makeText(this, "wez", Toast.LENGTH_LONG).show();
            for (StatusBarNotification notification : this.getActiveNotifications()) {
                if (notification.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
                    Toast.makeText(this, "jeszcze", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
