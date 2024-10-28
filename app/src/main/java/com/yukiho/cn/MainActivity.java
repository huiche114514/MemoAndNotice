package com.yukiho.cn;

import static android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS;

import static com.yukiho.cn.R.color.colorBar;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel_id"; // 定义通知渠道的ID
    private boolean isNotificationShown = false; // 用于追踪通知是否已显示
    private final int NOTIFICATION_ID = 1; // 定义通知的ID

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 调用父类的onCreate方法
        setContentView(R.layout.activity_main); // 设置当前活动的布局文件

        // 设置沉浸式状态栏并调整文字颜色
        Window window = getWindow();
        int statusBarColor = getResources().getColor(colorBar); // 从颜色文件获取状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusBarColor); // 设置状态栏颜色
        window.setNavigationBarColor(statusBarColor); // 设置导航栏颜色与状态栏一致

        // 检查通知权限并引导用户开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkAndRequestNotificationPermission();
        }

        // 获取主布局View，并设置窗口插入监听器以处理系统栏的间距
        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            // 获取系统栏的间距
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 设置View的padding以适应系统栏
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // 消费掉这次插入事件，避免进一步传播
            return WindowInsetsCompat.CONSUMED;
        });

        // 获取按钮并设置点击监听器
        Button toggleButton = findViewById(R.id.button);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O) // 确保API级别在26以上
            @Override
            public void onClick(View view) {
                // 获取EditText组件
                EditText editText = findViewById(R.id.editText);
                // 获取EditText中的文本内容
                String content = editText.getText().toString();
                // 检查是否已经显示了通知
                if (isNotificationShown) {
                    // 如果通知已经显示，则取消通知
                    cancelNotification();
                    // 更新通知显示状态
                    isNotificationShown = false;
                    // 更新按钮文本
                    toggleButton.setText("发送");
                } else {
                    // 如果EditText不为空，则显示通知
                    if (!content.isEmpty()) {
                        showNotification(content);
                        // 更新通知显示状态
                        isNotificationShown = true;
                        // 更新按钮文本
                        toggleButton.setText("隐藏");
                    }
                }
            }
        });

        // 获取跳转按钮并设置点击监听器
        Button gotoSettingsButton = findViewById(R.id.button_setting);
        gotoSettingsButton.setOnClickListener(view -> {
            // 创建Intent对象，用于启动SettingsActivity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            // 启动SettingsActivity
            startActivity(intent);
        });
    }

    // 检查通知权限并引导用户开启
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkAndRequestNotificationPermission() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            createNotificationChannel();
        }
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            showNotificationPermissionDialog();
        }
    }

    // 创建通知渠道
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "通知备忘常驻通知",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("通知渠道");
        notificationManager.createNotificationChannel(channel);
    }

    // 显示通知的方法
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String content) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("通知备忘")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // 设置通知图标
                .setOngoing(true); // 设置为常驻通知

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // 取消通知的方法
    private void cancelNotification() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    // 显示通知权限对话框
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("需要通知权限")
                .setMessage("应用需要发送通知以提醒您重要信息。请开启通知权限。")
                .setPositiveButton("开启", (dialog, which) -> {
                    // 用户点击了开启按钮，跳转到通知权限设置页面
                    Intent intent = new Intent(ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    startActivity(intent);
                })
                .setNegativeButton("取消", null)
                .show();
    }
}