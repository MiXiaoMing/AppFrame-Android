package com.appframe.framework.application;

import com.appframe.library.application.AFMultiDexApplication;
import com.appframe.utils.logger.Logger;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * 在BaseApplication中，通过反射执行。
 *
 * @author jiangkun
 * @date 2019/8/14
 */

public class FrameworkApplication extends AFMultiDexApplication {
    private static final String TAG = "FrameworkApplication ";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.getLogger().e(TAG + "onCreate.");
        // todo 替换成真实的appkey，这里都是假的。
        UMConfigure.init(application, "5a12384aa40fa3551f0001d1", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}
