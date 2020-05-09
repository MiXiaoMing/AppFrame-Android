package com.appframe.testdata;

import android.os.CountDownTimer;
import com.appframe.library.application.AFApplication;
import com.appframe.testdata.entity.Performance;
import com.appframe.utils.app.AppRuntimeUtil;
import com.appframe.utils.app.AppUtil;
import com.appframe.utils.logger.Logger;
import java.util.ArrayList;


public class PerformancePlugin {
    public ArrayList<Performance> performances = new ArrayList<>();
    private int processID = -1;

    public PerformancePlugin() {
        performances.clear();

        processID = AppRuntimeUtil.getInstance().getPidByProcessName(AFApplication.applicationContext, AppUtil.getPackageName(AFApplication.applicationContext));
        Logger.getLogger().d("pid：" + processID);
        timer.start();
    }

    public void stop() {
        timer.cancel();
        processID = -1;
    }

    private CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (processID > 0) {
                Performance performance = new Performance();
                performance.recordTime = System.currentTimeMillis();

                double[] userCpuAndMemory = AppRuntimeUtil.getInstance().getCpuAndMemoryByTop(AppUtil.getPackageName(AFApplication.applicationContext));
                performance.cpuUse = userCpuAndMemory[0];
                performance.memoryUse = userCpuAndMemory[1];

                performances.add(performance);
            }
        }

        @Override
        public void onFinish() {
        }
    };
}
