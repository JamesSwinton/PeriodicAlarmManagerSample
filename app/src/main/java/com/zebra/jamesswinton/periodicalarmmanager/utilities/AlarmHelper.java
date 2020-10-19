package com.zebra.jamesswinton.periodicalarmmanager.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.zebra.jamesswinton.periodicalarmmanager.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

public class AlarmHelper {

    // Alarm Constants
    private static final int ALARM_REQUEST_CODE = 100;

    public static void setAlarm(Context cx) {
        // Init AlarmManager
        AlarmManager alarmManager = (AlarmManager) cx.getSystemService(ALARM_SERVICE);

        // Create Pending Intent. This will launch our BroadcastReceiver, from which we launch our
        // Foreground Service. Foreground Service will handle the "Heartbeat"
        Intent alarmIntent = new Intent(cx, AlarmReceiver.class);
        alarmIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(cx,
                ALARM_REQUEST_CODE, alarmIntent, 0);

        // Set Alarm using Pending Intent
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 120000, pendingIntent);
    }
}
