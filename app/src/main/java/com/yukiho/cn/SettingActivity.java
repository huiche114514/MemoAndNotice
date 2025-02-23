package com.yukiho.cn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    @SuppressLint({"MissingInflatedId", "BatteryLife"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);

        ImmersiveUtils.setImmersiveMode(this);

        findViewById(R.id.KeepApp).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        });
    }
}