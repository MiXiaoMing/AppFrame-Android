package com.appframe.testdata.api;

import com.appframe.framework.http.EmptyHttpResult;
import com.appframe.testdata.api.input.PerformancesBody;
import com.appframe.testdata.api.input.PointsBody;
import com.appframe.testdata.api.input.StartRecordBody;
import com.appframe.testdata.api.input.StopRecordBody;
import com.appframe.testdata.api.output.StartRecordResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 测评数据
 */
public interface TestDataService {


    /**********  scheme  **********/

    /**
     * 开始记录
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("client/monitor/beginRecord")
    Observable<StartRecordResult> startRecord(@Body StartRecordBody body);

    /**
     * 结束记录
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("client/monitor/endRecord")
    Observable<EmptyHttpResult> stopRecord(@Body StopRecordBody body);


    /**********  upload  **********/

    /**
     * 页面响应时间
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("client/monitor/pageResponse")
    Observable<EmptyHttpResult> uploadPages(@Body PointsBody body);

    /**
     * 接口响应时间
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("client/monitor/urlResponse")
    Observable<EmptyHttpResult> uploadInterfaces(@Body PointsBody body);

    /**
     * 性能数据
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("client/monitor/cpuAndMemory")
    Observable<EmptyHttpResult> uploadPerformances(@Body PerformancesBody body);
}
