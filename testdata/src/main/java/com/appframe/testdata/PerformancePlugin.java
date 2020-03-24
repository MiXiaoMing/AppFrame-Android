package com.appframe.testdata;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.appframe.library.application.AFApplication;
import com.appframe.library.network.http.AFHttpClient;
import com.appframe.testdata.entity.Performance;
import com.appframe.testdata.entity.PointData;
import com.appframe.utils.app.AppRuntimeUtil;
import com.appframe.utils.app.AppUtil;
import com.appframe.utils.device.SystemUtil;
import com.appframe.utils.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    private CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, 500) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (processID > 0) {
                Performance performance = new Performance();
                // TODO: 2020/3/24 这里不对，内存应为实时的
//                performance.cpuUse = AppRuntimeUtil.getInstance().getProcessCpuUsage(processID);
                performance.memoryUse = AppRuntimeUtil.getInstance().getPidMemorySize(AFApplication.applicationContext, processID);
                performance.recordTime = System.currentTimeMillis();

                performances.add(performance);
            }
        }

        @Override
        public void onFinish() {
        }
    };
}
