package com.mattieapps.roommates.systems;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.mattieapps.roommates.R;
import com.mattieapps.roommates.model.database.Alarm;
import com.mattieapps.roommates.systems.notification.NotificationBroadcastReceiver;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by andrewmattie on 3/10/15.
 */
public class MattieUtils {

    private Context context;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public MattieUtils(Context context) {
        this.context = context;
    }

    public MattieUtils(Context context, FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.fragmentTransaction = fragmentTransaction;
    }

    public void fragmentMethod(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    public void fragmentMethod(Fragment fragment, boolean addToBackstack) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void fragmentMethod(int layout, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }

    public void fragmentMethod(int layout, Fragment fragment, boolean addToBackstack) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setAlarm(String expenseName, String expensePrice, int alarmDay, int alarmMonth, int alarmYear, int alarmHour, int alarmMinute, int alarmFreq, int randomId) {
        Realm realm = Realm.getInstance(context);
        Alarm alarm = realm.createObject(Alarm.class);

        alarm.setId(randomId);
        alarm.setFrequency(alarmFreq);
        alarm.setExpenseName(expenseName);
        alarm.setExpensePrice(expensePrice);
        alarm.setAlarmDay(alarmDay);
        alarm.setAlarmMonth(alarmMonth);
        alarm.setAlarmYear(alarmYear);
        alarm.setAlarmHour(alarmHour);
        alarm.setAlarmMinuite(alarmMinute);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, alarmMonth - 1);
        calendar.set(Calendar.YEAR, alarmYear);
        calendar.set(Calendar.DAY_OF_MONTH, alarmDay);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Log.e("RM-ALARM-MILLS", "" + calendar.getTimeInMillis() + "");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, randomId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        switch (alarmFreq) {
            case 0:
                return;
            case 1:
                if (isKitKat()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                break;
            case 2:
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                if (isKitKat()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                break;
            case 3:
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                if (isKitKat()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                break;
            case 4:
                calendar.add(Calendar.MONTH, 1);
                if (isKitKat()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                break;
            case 5:
                calendar.add(Calendar.YEAR, 1);
                if (isKitKat()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                break;
            default:
                return;

        }

        //TODO change to set(very low priority)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static void shortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
