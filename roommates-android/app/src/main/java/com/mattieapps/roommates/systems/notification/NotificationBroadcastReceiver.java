package com.mattieapps.roommates.systems.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by andrewmattie on 5/11/15.
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent newIntent = new Intent(context, NotifcationShowActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
