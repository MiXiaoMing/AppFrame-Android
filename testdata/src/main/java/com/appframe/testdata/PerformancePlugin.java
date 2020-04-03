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
                // TODO: 2020/3/24 这里不对，内存应为实时的
//                performance.cpuUse = AppRuntimeUtil.getInstance().getProcessCpuUsageTop(processID);
//                performance.memoryUse = AppRuntimeUtil.getInstance().getPidMemorySize(AFApplication.applicationContext, processID);
                performance.recordTime = System.currentTimeMillis();

            //    double[] userCpuAndMemory = AppRuntimeUtil.getInstance().getCpuAndMemoryUsageTop(AppUtil.getPackageName(AFApplication.applicationContext));
                double cpu = AppRuntimeUtil.getInstance().getCpuUsage(AppUtil.getPackageName(AFApplication.applicationContext));
                double mem = AppRuntimeUtil.getInstance().getMemoryUsage(processID);
                performance.cpuUse = cpu;
                performance.memoryUse = mem;

                Logger.getLogger().e("=========cpu" + cpu + "mem" + mem);


                performances.add(performance);
            }
        }

        @Override
        public void onFinish() {
        }
    };
}
