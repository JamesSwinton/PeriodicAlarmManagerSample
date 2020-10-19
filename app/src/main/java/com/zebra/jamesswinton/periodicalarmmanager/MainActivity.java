package com.zebra.jamesswinton.periodicalarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.zebra.jamesswinton.periodicalarmmanager.databinding.ActivityMainBinding;
import com.zebra.jamesswinton.periodicalarmmanager.utilities.AlarmHelper;
import com.zebra.jamesswinton.periodicalarmmanager.utilities.PermissionsHelper;

public class MainActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "PeriodicAlarmManager";

    // UI
    private ActivityMainBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        PermissionsHelper permissionsHelper = new PermissionsHelper(this, () -> {
            Toast.makeText(MainActivity.this, "Permissions Granted", Toast.LENGTH_LONG).show();
        });

        mDataBinding.setAlarmButton.setOnClickListener(view -> {
            if (permissionsHelper.permissionsGranted()) {
                AlarmHelper.setAlarm(MainActivity.this);
                finish();
            } else {
                Toast.makeText(this ,"Please enable storage permissions",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}