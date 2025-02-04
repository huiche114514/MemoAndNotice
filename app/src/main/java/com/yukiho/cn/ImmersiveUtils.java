package com.yukiho.cn;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ImmersiveUtils {

    public static void setImmersiveMode(Activity activity) {
        Window window = activity.getWindow();
        int statusBarColor = window.getStatusBarColor();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        int uiMode = activity.getResources().getConfiguration().uiMode;
        boolean isNightMode = (uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        if (isNightMode) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusBarColor);
        window.setNavigationBarColor(statusBarColor);
    }
}