package com.appframe.library.application;

import android.app.Application;
import android.content.Context;

import com.appframe.library.application.multidex.MultiDex;
import com.appframe.utils.logger.Logger;

/**
 * Minimal MultiDex capable application. To use the legacy multidex library there is 3 possibility:
 * <ul>
 * <li>Declare this class as the application in your AndroidManifest.xml.</li>
 * <li>Have your {@link Application} extends this class.</li>
 * <li>Have your {@link Application} override attachBaseContext starting with<br>
 * <code>
 * protected void attachBaseContext(Context base) {<br>
 * super.attachBaseContext(base);<br>
 * MultiDex.install(this);
 * </code></li>
 * <ul>
 */

public class AFMultiDexApplication extends AFApplication {
    private static final String TAG = "AFMultiDexApplication ";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.getLogger().e(TAG + "onCreate.");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Logger.getLogger().e(TAG + "attachBaseContext.");
        MultiDex.install(this);
    }
}
