package com.appframe.api.user;


public class UserInfo {
    public String id, userName, userType, cellphone, region;
    public Float balance;

    public UserInfo(String id, String userName, String userType, String cellphone, float balance, String region) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
        this.cellphone = cellphone;
        this.balance = balance;
        this.region = region;
    }
}
