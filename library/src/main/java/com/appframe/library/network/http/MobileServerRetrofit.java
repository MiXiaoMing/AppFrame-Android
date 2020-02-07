package com.appframe.library.network.http;

import com.appframe.library.network.http.adapter.NullStringToEmptyAdapterFactory;
import com.appframe.library.network.http.interceptor.HeaderInterceptor;
import com.appframe.utils.logger.Logger;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * mobile sever 接口请求
 */
public class MobileServerRetrofit {

    private Retrofit retrofit = null;

    private static MobileServerRetrofit instance = new MobileServerRetrofit();

    public static MobileServerRetrofit getInstance() {
        return instance;
    }

    private MobileServerRetrofit() {
        resetApp();
    }

    public void resetApp() {
        if (retrofit != null) {
            retrofit = null;
        }

        Logger.getLogger().d("服务地址：\n" +
                "url：" + ServerHosts.service_yundian);

        OkHttpClient.Builder builder = AFHttpClient.getInstance().newBuilder();

        List<Interceptor> interceptors = builder.interceptors();
        // 添加header信息
        interceptors.add(0, new HeaderInterceptor());
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerHosts.service_yundian)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
