package com.zebra.jamesswinton.periodicalarmmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import com.zebra.jamesswinton.periodicalarmmanager.utilities.AlarmHelper;
import com.zebra.jamesswinton.periodicalarmmanager.utilities.NotificationHelper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendHeartbeatService extends Service {

    // Debugging
    private static final String TAG = "SendHeartbeatService";

    // WakeLock Holder
    PowerManager.WakeLock mWakeLock = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service Created");

        // Acquire WakeLock
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "PeriodAlarmService::HeartbeatWakeLock");
        mWakeLock.acquire(10*60*1000L /*10 minutes*/);

        // Log Launch
        logToFile();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service Started");

        // StartUp
        startForeground(NotificationHelper.NOTIFICATION_ID,
                NotificationHelper.createNotification(this));

        // Start Heart Beat
        sendHeartbeat();

        // Return NOT_STICKY - I.e. when service is killed, leave it dead, don't restart
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Release WakeLock
        mWakeLock.release();
    }

    private void sendHeartbeat() {
        Log.i(TAG, "Sending Heartbeat...");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            // Reset Alarm
            Log.i(TAG, "Heartbeat sent, re-scheduling alarm");
            AlarmHelper.setAlarm(SendHeartbeatService.this);

            // Close Service
            Log.i(TAG, "Alarm rescheduled, qutting service");
            stopSelf();
        }, 5000);
    }

    private void logToFile() {
        // Log Results
        File logFile = new File(Environment.getExternalStorageDirectory()
                + File.separator + "PeriodicAlarmManagerTest" + File.separator + "log.txt");
        try {
            String logText = "Alarm & Service Triggered @ " + getCurrentTimeHumanReadable() +"\n";
            FileUtils.writeStringToFile(logFile, logText, Charset.defaultCharset(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTimeHumanReadable() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault())
                .format(new Date(System.currentTimeMillis()));
    }
}