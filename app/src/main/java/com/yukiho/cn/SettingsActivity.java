package com.yukiho.cn;

import static com.yukiho.cn.R.color.colorBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SettingsActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        // 获取状态栏高度

        // 设置沉浸式状态栏并调整文字颜色
        Window window = getWindow();
        int statusBarColor = getResources().getColor(colorBar); // 从颜色文件获取状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusBarColor); // 设置状态栏颜色
        window.setNavigationBarColor(statusBarColor); // 设置导航栏颜色与状态栏一致

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