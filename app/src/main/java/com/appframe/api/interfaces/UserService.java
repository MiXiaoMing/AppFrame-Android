package com.appframe.api.interfaces;

import com.appframe.api.user.LoginEntity;
import com.appframe.api.user.SendSmsEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 与用户相关接口
 */
public interface UserService {

    /**********  登录  **********/

    @FormUrlEncoded
    @POST("user/sendSms")
    Observable<SendSmsEntity> sendSms(@Field("cellphone") String cellphone);

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginEntity> login(@Field("cellphone") String cellphone, @Field("smscode") String smscode, @Field("cid") String cid);
}
