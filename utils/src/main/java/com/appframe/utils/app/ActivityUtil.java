package com.appframe.utils.app;

import android.app.Activity;
import android.view.View;

/**
 * 页面 工具类
 */

public class ActivityUtil {

    public static boolean isAvailable(Activity activity) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            return true;
        }
        return false;
    }

    public static boolean isAvailable(View view) {
        if (view != null && view.isShown() && view.getParent() != null) {
            return true;
        }
        return false;
    }
}
