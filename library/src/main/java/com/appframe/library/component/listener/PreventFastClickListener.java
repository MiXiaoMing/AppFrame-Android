package com.appframe.library.component.listener;

import android.view.View;

import java.util.Calendar;

public abstract class PreventFastClickListener implements View.OnClickListener {
    private int MIN_CLICK_DELAY_TIME = 2000;
    private long lastClickTime = 0L;

    public PreventFastClickListener(int minClickDelayTime) {
        this.MIN_CLICK_DELAY_TIME = minClickDelayTime;
    }

    public PreventFastClickListener() {
    }

    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - this.lastClickTime > (long)this.MIN_CLICK_DELAY_TIME) {
            this.lastClickTime = currentTime;
            this.onPreventFastClick(v);
        }

    }

    public abstract void onPreventFastClick(View var1);
}
