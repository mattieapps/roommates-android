package com.mattieapps.roommates.systems.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by andrewmattie on 5/11/15.
 */
public class RestartBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot Achieved", Toast.LENGTH_LONG).show();
    }
}
