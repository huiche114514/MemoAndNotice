package com.yukiho.cn;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationPermissionManager {

    private static final String CHANNEL_ID = "channel_id";
    private final Context context;
    private final boolean isDarkMode;

    public NotificationPermissionManager(Context context, boolean isDarkMode) {
        this.context = context;
        this.isDarkMode = isDarkMode;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkAndRequestNotificationPermission() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            createNotificationChannel(notificationManager);
        }
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            showNotificationPermissionDialog();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.java_on_going_notification),
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(context.getString(R.string.java_notification_channel));
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.create();
        dialog.setMessage(context.getString(R.string.java_dialog_text));

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.java_dialog_yes), (dialog1, which) -> {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.java_dialog_no), (dialog1, which) ->
                Toast.makeText(context, R.string.java_dialog_toast_text, Toast.LENGTH_SHORT).show());
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (isDarkMode) {
            positiveButton.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.white)));
            negativeButton.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.white)));
        } else {
            positiveButton.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.black)));
            negativeButton.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.black)));
        }
    }
}
