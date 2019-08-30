package com.appframe.api.user;


import com.appframe.framework.http.HttpResult;

import java.io.Serializable;

public class LoginEntity extends HttpResult<LoginEntity> implements Serializable {
    private String token;
    private UserInfo userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
