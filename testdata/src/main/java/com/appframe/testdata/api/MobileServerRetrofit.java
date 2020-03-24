package com.appframe.testdata.api;

import com.appframe.library.network.http.AFHttpClient;
import com.appframe.library.network.http.adapter.NullStringToEmptyAdapterFactory;
import com.google.gson.GsonBuilder;

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

    }

    public void resetApp(String host) {
        if (retrofit != null) {
            retrofit = null;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(AFHttpClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
