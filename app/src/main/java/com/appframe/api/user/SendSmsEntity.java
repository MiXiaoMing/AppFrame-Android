package com.appframe.api.user;


import com.appframe.framework.http.HttpResult;

import java.io.Serializable;

public class SendSmsEntity extends HttpResult<SendSmsEntity> implements Serializable {
    private String token, smsCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
