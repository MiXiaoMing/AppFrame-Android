package com.appframe.utils.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.appframe.utils.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.List;

// app信息工具类
public class AppUtil {

    // 获取应用名称
    public static String getAppName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = manager.getApplicationInfo(context.getPackageName(), 0);
            return (String) manager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.getLogger().e("获取应用名称失败；" + e.toString());
            return null;
        }
    }

    // 获取包名
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.getLogger().e("获取应用包名失败：" + e.toString());
            return null;
        }
    }

    // 获取版本名称
    public static String getVersionName(Context cxt) {
        try {
            PackageManager manager = cxt.getPackageManager();
            PackageInfo info = manager.getPackageInfo(cxt.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("获取app版本名称错误：" + e.toString());
            return "";
        }
    }

    // 获取版本号
    public static int getVersionCode(Context cxt) {
        try {
            PackageManager manager = cxt.getPackageManager();
            PackageInfo info = manager.getPackageInfo(cxt.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("获取app版本错误：" + e.toString());
            return 0;
        }
    }
}
