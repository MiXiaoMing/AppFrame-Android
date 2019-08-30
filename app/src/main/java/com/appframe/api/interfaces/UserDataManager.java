package com.appframe.api.interfaces;



import com.appframe.api.MobileServerRetrofit;
import com.appframe.api.user.LoginEntity;
import com.appframe.api.user.SendSmsEntity;

import io.reactivex.Observable;

/**
 * 用户服务
 */
public class UserDataManager {
    private UserService service;

    public UserDataManager() {
        service = MobileServerRetrofit.getInstance().getUserService();
    }

    /**********  登录  **********/

    public Observable<SendSmsEntity> sendSms(String cellphone) {
        return service.sendSms(cellphone);
    }

    public Observable<LoginEntity> login(String cellphone, String smsCode, String cid) {
        return service.login(cellphone, smsCode, cid);
    }

}
