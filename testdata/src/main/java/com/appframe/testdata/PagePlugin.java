package com.appframe.testdata;

import com.appframe.library.application.observer.ActivityLifecycle;
import com.appframe.library.application.observer.ActivityLifecycleObserver;
import com.appframe.testdata.entity.PointData;

import java.util.ArrayList;

public class PagePlugin {
    public ArrayList<PointData> pages = new ArrayList<>();

    public PagePlugin() {
        pages.clear();
        ActivityLifecycle.attach(observer);
    }

    public void stop() {
        ActivityLifecycle.detach(observer);
    }

    private ActivityLifecycleObserver observer = new ActivityLifecycleObserver() {
        public String previousActivityName;
        public long previousTime;

        @Override
        public void activityResumed(String activityName, String previousActivityName, long time) {
            if (this.previousActivityName.equalsIgnoreCase(previousActivityName)) {
                long during = time - previousTime;

                PointData page = new PointData();
                page.name = activityName;
                page.duration = during;
                page.recordTime = time;
                pages.add(page);
            }
        }

        @Override
        public void activityPaused(String activityName, String previousActivityName, long time) {
            this.previousActivityName = activityName;
            previousTime = time;
        }

        @Override
        public void changeToForeground() {

        }

        @Override
        public void changeToBackground() {

        }

        @Override
        public boolean frontOrBack() {
            return false;
        }

        @Override
        public void attached() {
            previousActivityName = "";
            previousTime = 0L;
        }

        @Override
        public void detached() {
        }
    };
}
