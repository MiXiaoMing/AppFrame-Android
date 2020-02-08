package com.appframe.utils.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.appframe.utils.logger.Logger;

import java.util.List;


// app操作工具类
public class AppOperateUtil {

    // 判断一个app是否已经安装
    public static boolean isAppInstalled(Context context, String packageNameTarget) {
        if (packageNameTarget == null) {
            Logger.getLogger().e("packageNameTarget == null return false.");
            return false;
        }

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            String packageNameSource = packageInfo.packageName;
            if (packageNameSource.equals(packageNameTarget)) {
                return true;
            }
        }
        return false;
    }

    // 打开第三方app
    public static void openApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }


    /**
     * 是否安装支付宝。
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

}
