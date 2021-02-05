package com.zebra.jamesswinton.periodicalarmmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.zebra.jamesswinton.periodicalarmmanager.databinding.ActivityMainBinding;
import com.zebra.jamesswinton.periodicalarmmanager.utilities.AlarmHelper;
import com.zebra.jamesswinton.periodicalarmmanager.utilities.PermissionsHelper;

public class MainActivity extends AppCompatActivity implements
        PermissionsHelper.OnPermissionsResultListener {

    // Permissions
    private final PermissionsHelper mPermissionsHelper = new PermissionsHelper(this,
            this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mDataBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        // Request Permissions
        mPermissionsHelper.requestAllPermissions();

        // Alarm Click Listener
        mDataBinding.setAlarmButton.setOnClickListener(view -> {
            if (mPermissionsHelper.permissionsGranted()) {
                AlarmHelper.setAlarm(MainActivity.this);
                finish();
            } else {
                Toast.makeText(this ,"Please enable storage permissions",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsHelper.onRequestPermissionsResult();
    }

    @Override
    public void onPermissionsGranted() {
        Toast.makeText(MainActivity.this, "Permissions Granted", Toast.LENGTH_LONG)
                .show();
    }
}