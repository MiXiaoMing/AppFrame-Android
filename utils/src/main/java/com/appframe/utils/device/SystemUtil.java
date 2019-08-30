package com.appframe.utils.device;

import android.content.Context;
import android.os.Build;

import java.util.Locale;


public class SystemUtil {

    // 获取当前语言
    public static String getLanguage(Context context) {
        return Locale.getDefault().getLanguage();
    }

    // 获取设备系统版本号
    public static String getVersion() {
        return Build.VERSION.RELEASE;
    }
}
