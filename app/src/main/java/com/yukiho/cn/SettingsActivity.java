package com.yukiho.cn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);

        // 按钮点击事件
        findViewById(R.id.devtq).setOnClickListener(v -> {
            // 创建Intent打开网页
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://afdian.com/a/yixiaziquancile")); // 替换为你的网址
            startActivity(intent);
        });

        Window window = getWindow();
        // 获取当前的状态栏颜色
        int statusBarColor = window.getStatusBarColor();
        // 清除FLAG_TRANSLUCENT_STATUS和FLAG_TRANSLUCENT_NAVIGATION flags
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        // 判断当前系统是否为深色模式
        int uiMode = getResources().getConfiguration().uiMode;
        boolean isNightMode = (uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        if (isNightMode) {
            // 深色模式下，设置状态栏图标为深色（白色）
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            // 非深色模式下，设置状态栏图标为浅色（黑色）
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusBarColor);
        // 设置导航栏颜色与状态栏一致
        window.setNavigationBarColor(statusBarColor);

        // 获取主布局View，并设置窗口插入监听器以处理系统栏的间距
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View mainView = findViewById(R.id.setting_main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            // 获取系统栏的间距
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 设置View的padding以适应系统栏
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // 消费掉这次插入事件，避免进一步传播
            return WindowInsetsCompat.CONSUMED;
        });
    }
}