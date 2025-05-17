package com.yukiho.cn;

import android.content.Context;
import android.content.res.Configuration;

// 应用主题判断模块
public class ThemeUtils {

    public static boolean isSystemInDarkMode(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
