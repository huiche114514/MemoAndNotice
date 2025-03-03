package com.yukiho.cn;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_id";
    private boolean isNotificationShown = false;
    private final int NOTIFICATION_ID = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImmersiveUtils.setImmersiveMode(this);

        boolean isDarkMode = ThemeUtils.isSystemInDarkMode(this);

        NotificationPermissionManager permissionManager = new NotificationPermissionManager(this, isDarkMode);
        permissionManager.checkAndRequestNotificationPermission();

        Button gotoSettingButton = findViewById(R.id.button_setting);
        gotoSettingButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        Button gotoInfoButton = findViewById(R.id.button_info);
        gotoInfoButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        });

        Button toggleButton = findViewById(R.id.button);
        final EditText editTextTitle = findViewById(R.id.title);
        final EditText editTextContent = findViewById(R.id.editText);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                if (isNotificationShown) {
                    cancelNotification();
                    isNotificationShown = false;
                    toggleButton.setText(R.string.send);
                } else {
                    if (!content.isEmpty()) {
                        showNotification(title.isEmpty() ? "通知备忘" : title, content);
                        isNotificationShown = true;
                        toggleButton.setText(R.string.hide);
                    } else {
                        Toast.makeText(MainActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String title, String content) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.notification_ic)
                .setOngoing(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}