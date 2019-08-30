package com.appframe.utils.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * app运行时工具类
 */

public class AppRuntimeUtil {
    /**
     * 饿汉模式
     */
    private static class AppRuntimeUtilInstance {
        private static AppRuntimeUtil instance = new AppRuntimeUtil();
    }

    public static AppRuntimeUtil getInstance() {
        return AppRuntimeUtilInstance.instance;
    }

    private WeakReference<Activity> currentActivityRef = null;
    private boolean frontOrBack = false;

    /**
     * 设置当前页面对象
     * @param activity
     */
    public void setCurrentActivity(Activity activity) {
        currentActivityRef = new WeakReference<Activity>(activity);
    }

    /**
     * 获取当前页面对象
     * @return
     */
    public Activity getCurrentActivity() {
        if (currentActivityRef == null) {
            return null;
        }
        return currentActivityRef.get();
    }

    /**
     * 获取进程名称
     * @param cxt
     * @param pid
     * @return
     */
    public String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public void setFrontOrBack(boolean frontOrBack) {
        this.frontOrBack = frontOrBack;
    }

    public boolean isFrontOrBack() {
        return frontOrBack;
    }
}
