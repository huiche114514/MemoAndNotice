package com.yukiho.cn;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private boolean isNotificationShown = false;
    private LargeIconHelper largeIconHelper;

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

        TextView picText = findViewById(R.id.pic_text);
        largeIconHelper = new LargeIconHelper(this, picText);
        LinearLayout noticePic = findViewById(R.id.notice_pic);
        noticePic.setOnClickListener(v -> largeIconHelper.handleImageSelection());

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
                        showNotification(title.isEmpty() ? getString(R.string.app_name) : title, content);
                        isNotificationShown = true;
                        toggleButton.setText(R.string.hide);
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.java_notification_hint), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        largeIconHelper.handleImageResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String title, String content) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.notification_ic)
                .setOngoing(true);

        Bitmap largeIconBitmap = largeIconHelper.getLargeIconBitmap();
        if (largeIconBitmap != null) {
            builder.setLargeIcon(largeIconBitmap);
        }

        notificationManager.notify(1, builder.build());
    }

    private void cancelNotification() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(1);
    }
}