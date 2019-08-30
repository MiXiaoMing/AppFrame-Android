package com.appframe.api;

import com.appframe.api.interfaces.UserService;
import com.appframe.library.network.http.AFHttpClient;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * mobile sever 接口请求
 */
public class MobileServerRetrofit {

    private static class MobileServerRetrofitInstance {
        private static MobileServerRetrofit instance = new MobileServerRetrofit();
    }

    public static MobileServerRetrofit getInstance() {
        return MobileServerRetrofitInstance.instance;
    }

    private Retrofit retrofit = null;

    private MobileServerRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerHosts.service_user)
                .client(AFHttpClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }

}
