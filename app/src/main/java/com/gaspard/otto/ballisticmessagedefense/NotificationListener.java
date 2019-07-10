package com.gaspard.otto.ballisticmessagedefense;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import com.gaspard.otto.ballisticmessagedefense.Utils.CharSequenceUtil;


public class NotificationListener extends NotificationListenerService {
    public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
    public static final String TAG = "XDDD";
    private static final int[][] conversationEmojiOtherThanLike = {{55357, 56396},
            {55357, 56394},
            {55357, 56459},
            {55357, 56834},
            {55357, 56845},
            {55357, 56856},
            {55357, 56861},
            {55357, 56841},
            {55357, 56860},
            {55357, 56833},
            {55357, 56851},
            {55357, 56885},
            {55357, 56881},
            {55357, 56883},
            {55357, 56911},
            {55357, 56393},
            {55357, 56490},
            {55357, 56395},
            {55357, 56908},
            {55357, 56489},
            {55357, 57021},
            {55356, 57211},
            {55356, 57208},
            {55357, 56451},
            {55357, 56613},
            {55357, 56593},
            {55357, 56462},
            {55356, 57096},
            {55357, 56470},
            {55357, 56960},
            {55356, 57119},
            {55356, 57113},
            {55356, 57224},
            {55356, 57225},
            {55356, 57217},
            {55356, 57145},
            {55356, 57146},
            {55357, 56452},
            {55356, 57270},
            {55356, 57098},
            {55356, 57216},
            {55357, 56416},
            {55356, 57288},
            {55356, 57280},
            {55356, 57278},
            {55356, 57265},
            {55356, 57281},
            {55356, 57286},
            {55357, 56496},
            {55357, 56865},
            {55357, 56445},
            {55357, 56438},
            {55357, 56447},
            {55357, 56448},
            {55357, 56440},
            {55357, 56379},
            {55357, 56373},
            {55357, 56368},
            {55357, 56374},
            {55357, 56366},
            {55357, 56375},
            {55357, 56364},
            {55357, 56361},
            {55357, 56333},
            {55357, 56351},
            {55357, 56334},
            {55357, 56363},
            {55356, 57152},
            {55357, 56483},
            {55357, 56619},
            {55357, 56384},
            {55357, 56387},
            {55357, 56388},
            {55357, 56983},
            {55357, 56386},
            {55357, 57010},
            {55356, 57166},
            {55356, 57171},
            {55356, 57161},
            {55357, 56477},
            {55357, 56472},
            {55357, 56475},
            {55357, 56473},
            {55357, 56476},
            {55357, 56474},
            {9918},
            {9973},
            {9917},
            {9924},
            {9728},
            {9996},
            {10084}};
    //    nice tutorials
    //    https://medium.com/@polidea/how-to-respond-to-any-messaging-notification-on-android-7befa483e2d7
    //    https://github.com/Chagall/notification-listener-service-example

    public static boolean textEqualsEmojiArray(CharSequence text, int[] emojiArray) {
        if (text == null || emojiArray == null)
            return false;

        int length = emojiArray.length;
        if (text.length() != length)
            return false;

        for (int i = 0; i < length; i++)
            if ((int) text.charAt(i) != emojiArray[i])
                return false;

        return true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Ballistic Message Defense activated \uD83E\uDD19 \uD83E\uDD19\uD83C\uDFFF", Toast.LENGTH_LONG).show();
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "Ballistic Message Defense deactivated \uD83D\uDE80 \uD83D\uDE80 \uD83D\uDE80", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        handleAllNotifications(this.getActiveNotifications());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        handleAllNotifications(this.getActiveNotifications());
    }

    private void handleAllNotifications(final StatusBarNotification[] sbn) {
        for (final StatusBarNotification notification : sbn) {
            if (notification.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME)) {
                handleNotification(notification);
            }
        }
    }

    private void handleNotification(final StatusBarNotification sbn) {
        new Thread() {
            @Override
            public void run() {
                if (isMessengerNotification(sbn) && (isLikeMessage(sbn) || isConversationEmojiOtherThanLike(sbn))) {
                    cancelNotification(sbn.getKey());
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendLike(sbn);
                }
            }
        }.start();
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
                }
            }
        }
    }

    private boolean isLikeMessage(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        CharSequence text = extras.getCharSequence("android.text")/*.length()*/;
        text = CharSequenceUtil.removeWhitespaces(text);
//        if (text != null) {
//            String resp = "";
//            for (int i = 0; i < text.length(); i++) resp = resp + " " + Integer.toString((int)text.charAt(i));
//            Log.d(TAG, "isLikeMessage: " + text + "->" + resp);
//
//        }
        return text != null && ((text.length() == 2 && (int) text.charAt(0) == 55357 && (int) text.charAt(1) == 56397)
                || (text.length() >= 3 && (int) text.charAt((text.length() - 3)) == ':' && (int) text.charAt((text.length() - 2)) == 55357 && (int) text.charAt(text.length() - 1) == 56397)
                || (text.length() == 4 && (int) text.charAt(0) == 55357 && (int) text.charAt(2) == 56397 && (int) text.charAt(2) == 55356 && (int) text.charAt(2) == 57339)
                || (text.length() == 4 && (int) text.charAt(0) == 55357 && (int) text.charAt(2) == 56397 && (int) text.charAt(2) == 55356 && (int) text.charAt(2) == 57340)
                || (text.length() == 4 && (int) text.charAt(0) == 55357 && (int) text.charAt(2) == 56397 && (int) text.charAt(2) == 55356 && (int) text.charAt(2) == 57341)
                || (text.length() == 4 && (int) text.charAt(0) == 55357 && (int) text.charAt(2) == 56397 && (int) text.charAt(2) == 55356 && (int) text.charAt(2) == 57342)
                || (text.length() == 4 && (int) text.charAt(0) == 55357 && (int) text.charAt(2) == 56397 && (int) text.charAt(2) == 55356 && (int) text.charAt(2) == 57343)
                || (text.length() == 3 && text.charAt(0) == '(' && text.charAt(1) == 'y' && text.charAt(2) == ')'));
    }

    private boolean isConversationEmojiOtherThanLike(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        CharSequence text = extras.getCharSequence("android.text")/*.length()*/;
        text = CharSequenceUtil.removeWhitespaces(text);
        if (text != null && text.length() >= 3 && text.charAt(text.length()-3) == ':') {
            text = text.subSequence(text.length() - 2, text.length());
        } else if (text != null && text.length() >= 2 && text.charAt(text.length()-2) == ':') {
            text = text.subSequence(text.length()-1, text.length());
        }
        for (int[] ints : conversationEmojiOtherThanLike) {
            if (textEqualsEmojiArray(text, ints)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMessengerNotification(StatusBarNotification sbn) {
        return sbn.getPackageName().equals(FACEBOOK_MESSENGER_PACK_NAME);
    }
}
