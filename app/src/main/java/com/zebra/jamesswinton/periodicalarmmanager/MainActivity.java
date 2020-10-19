package com.zebra.jamesswinton.periodicalarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.zebra.jamesswinton.periodicalarmmanager.databinding.ActivityMainBinding;
import com.zebra.jamesswinton.periodicalarmmanager.utilities.AlarmHelper;

public class MainActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "PeriodicAlarmManager";

    // UI
    private ActivityMainBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mDataBinding.setAlarmButton.setOnClickListener(view -> {
            AlarmHelper.setAlarm(this);
            finish();
        });
    }
}