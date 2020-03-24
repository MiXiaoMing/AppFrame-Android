package com.appframe.application;

import com.appframe.BuildConfig;
import com.appframe.framework.application.FrameworkApplication;
import com.appframe.testdata.TestDataManager;
import com.appframe.utils.logger.Logger;

public class AppApplication extends FrameworkApplication {
    private static final String TAG = "AppApplication ";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.getLogger().e(TAG + "onCreate.");

        initTestData();
    }

    private void initTestData() {
        if (BuildConfig.testDataPlatform) {
            TestDataManager.getInstance().start("http://192.168.1.47:9100/td/");
        }
    }
}
