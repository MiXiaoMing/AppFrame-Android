package com.appframe.testdata.api;

import com.appframe.framework.http.EmptyHttpResult;
import com.appframe.testdata.api.input.PerformancesBody;
import com.appframe.testdata.api.input.PointsBody;
import com.appframe.testdata.api.input.StartRecordBody;
import com.appframe.testdata.api.input.StopRecordBody;
import com.appframe.testdata.api.output.StartRecordResult;

import io.reactivex.Observable;


/**
 * 测评 服务
 */
public class TestDataServerManager {
    private TestDataService service;

    public TestDataServerManager() {
        service = MobileServerRetrofit.getInstance().getRetrofit().create(TestDataService.class);
    }


    /********** scheme  **********/

    public Observable<StartRecordResult> startRecord(StartRecordBody body) {
        return service.startRecord(body);
    }

    public Observable<EmptyHttpResult> stopRecord(StopRecordBody body) {
        return service.stopRecord(body);
    }

    /**********  上传  **********/

    public Observable<EmptyHttpResult> uploadPages(PointsBody body) {
        return service.uploadPages(body);
    }

    public Observable<EmptyHttpResult> uploadInterfaces(PointsBody body) {
        return service.uploadInterfaces(body);
    }

    public Observable<EmptyHttpResult> uploadPerformances(PerformancesBody body) {
        return service.uploadPerformances(body);
    }
}
