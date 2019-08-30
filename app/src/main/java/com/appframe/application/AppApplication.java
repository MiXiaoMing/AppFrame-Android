package com.appframe.application;

import com.appframe.framework.application.FrameworkApplication;
import com.appframe.utils.logger.Logger;

/**
 * @author jiangkun
 * @date 2019/8/15
 */

public class AppApplication extends FrameworkApplication {
    private static final String TAG = "AppApplication ";
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.getLogger().e(TAG + "onCreate.");
    }
}
