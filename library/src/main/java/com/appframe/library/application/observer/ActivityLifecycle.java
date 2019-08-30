package com.appframe.library.application.observer;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.appframe.utils.app.AppRuntimeUtil;
import com.appframe.utils.logger.Logger;

/**
 * activity状态改变监听器
 */

public class ActivityLifecycle extends Subject<ActivityLifecycleObserver>{

    public ActivityLifecycle() {
        Logger.getLogger().d("ActivityLifecycle init");
    }

    private String previousActivityName;

    public Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        private int activityCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activityCount == 0) {
                for (ActivityLifecycleObserver observer : observers) {
                    observer.changeToBackground();
                }
                AppRuntimeUtil.getInstance().setFrontOrBack(true);
                previousActivityName = "Home";
            }
            ++activityCount;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            AppRuntimeUtil.getInstance().setCurrentActivity(activity);

            for (ActivityLifecycleObserver observer : observers) {
                observer.activityResumed(activity.getClass().getName(), previousActivityName, System.currentTimeMillis());
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            for (ActivityLifecycleObserver observer : observers) {
                observer.activityPaused(activity.getClass().getName(), previousActivityName, System.currentTimeMillis());
            }

            previousActivityName = activity.getClass().getName();
        }

        @Override
        public void onActivityStopped(Activity activity) {
            --activityCount;
            if (activityCount == 0) {
                for (ActivityLifecycleObserver observer : observers) {
                    observer.changeToBackground();
                }
                AppRuntimeUtil.getInstance().setFrontOrBack(false);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
