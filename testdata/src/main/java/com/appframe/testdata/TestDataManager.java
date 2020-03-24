package com.appframe.testdata;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.appframe.framework.http.EmptyHttpResult;
import com.appframe.library.application.AFApplication;
import com.appframe.library.component.notify.AFToast;
import com.appframe.testdata.api.MobileServerRetrofit;
import com.appframe.testdata.api.TestDataServerManager;
import com.appframe.testdata.api.input.PerformancesBody;
import com.appframe.testdata.api.input.PointsBody;
import com.appframe.testdata.api.input.StartRecordBody;
import com.appframe.testdata.api.input.StopRecordBody;
import com.appframe.testdata.api.output.StartRecordResult;
import com.appframe.testdata.entity.Performance;
import com.appframe.testdata.entity.PointData;
import com.appframe.utils.app.AppUtil;
import com.appframe.utils.device.DeviceUtil;
import com.appframe.utils.device.SystemUtil;
import com.appframe.utils.logger.Logger;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestDataManager {
    private PagePlugin pagePlugin;
    private InterfacePlugin interfacePlugin;
    private PerformancePlugin performancePlugin;
    private String recordID;

    private static TestDataManager instance = new TestDataManager();

    public static TestDataManager getInstance() {
        return instance;
    }

    private TestDataManager() {

    }

    public void start(final String host) {
        recordID = "";

        timer.start();

        pagePlugin = new PagePlugin();
        interfacePlugin = new InterfacePlugin();
        performancePlugin = new PerformancePlugin();

        MobileServerRetrofit.getInstance().resetApp(host);
        startRecord();
    }

    public void stop() {
        if (!TextUtils.isEmpty(recordID)) {
            pagePlugin.stop();
            interfacePlugin.stop();
            performancePlugin.stop();

            uploadPages();
            uploadInterfaces();
            uploadPerformance();

            stopRecord();
        }
    }

    private CountDownTimer timer = new CountDownTimer(2 * 60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished <= 5 * 1000) {
                Logger.getLogger().e("上传数据 倒计时：" + millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            if (TextUtils.isEmpty(recordID)) {
                startRecord();
            } else {
                uploadPages();
                uploadInterfaces();
                uploadPerformance();
            }

            timer.start();
        }
    };

    /*******  网络请求  *******/

    private void startRecord() {
        StartRecordBody body = new StartRecordBody();
        body.phoneNum = DeviceUtil.getBrand() + " " + DeviceUtil.getModel();
        body.phoneSystem = SystemUtil.getVersion();
        body.version = AppUtil.getVersionName(AFApplication.application);
        body.appBundleId = AppUtil.getPackageName(AFApplication.application);
        body.platform = "Android";

        new TestDataServerManager()
                .startRecord(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StartRecordResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StartRecordResult result) {
                        if (result.isSuccess()) {
                            recordID = result.getData().recordId;
                        } else {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，开始记录 -- 失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void stopRecord() {
        StopRecordBody body = new StopRecordBody();
        body.recordId = recordID;

        new TestDataServerManager()
                .stopRecord(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmptyHttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EmptyHttpResult result) {
                        if (result.isSuccess()) {
                            recordID = "";

                            AFToast.showShort(AFApplication.applicationContext, "评测数据，结束记录");
                        } else {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，结束记录 -- 失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void uploadPages() {
        if (TextUtils.isEmpty(recordID)) {
            startRecord();
            return;
        }

        final ArrayList<PointData> pages = new ArrayList<>(pagePlugin.pages);
        if (pages.size() <= 0) {
            return;
        }

        pagePlugin.pages.clear();

        PointsBody body = new PointsBody();
        body.recordId = recordID;
        body.context = pages;

        new TestDataServerManager()
                .uploadPages(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmptyHttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EmptyHttpResult result) {
                        if (result.isSuccess()) {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，页面响应");
                        } else {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，页面响应 -- 失败");
                            pagePlugin.pages.addAll(pages);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        pagePlugin.pages.addAll(pages);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void uploadInterfaces() {
        if (TextUtils.isEmpty(recordID)) {
            startRecord();
            return;
        }

        final ArrayList<PointData> interfaces = new ArrayList<>(interfacePlugin.interfaces);
        if (interfaces.size() <= 0) {
            return;
        }

        interfacePlugin.interfaces.clear();

        PointsBody body = new PointsBody();
        body.recordId = recordID;
        body.context = interfaces;

        new TestDataServerManager()
                .uploadInterfaces(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmptyHttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EmptyHttpResult result) {
                        if (result.isSuccess()) {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，接口响应");
                        } else {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，接口响应 -- 失败");
                            interfacePlugin.interfaces.addAll(interfaces);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        interfacePlugin.interfaces.addAll(interfaces);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void uploadPerformance() {
        if (TextUtils.isEmpty(recordID)) {
            startRecord();
            return;
        }

        final ArrayList<Performance> performances = new ArrayList<>(performancePlugin.performances);
        if (performances.size() <= 0) {
            return;
        }

        performancePlugin.performances.clear();

        PerformancesBody body = new PerformancesBody();
        body.recordId = recordID;
        body.context = performances;

        new TestDataServerManager()
                .uploadPerformances(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EmptyHttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EmptyHttpResult result) {
                        if (result.isSuccess()) {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，性能数据");
                        } else {
                            AFToast.showShort(AFApplication.applicationContext, "评测数据，性能数据 -- 失败");
                            performancePlugin.performances.addAll(performances);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        performancePlugin.performances.addAll(performances);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
