package com.appframe.utils.data.set;

import java.util.ArrayList;

// 国际国家 工具类
public class CountryUtil {

    // 获取国家电话区号
    public static String getMobileAreaCode(String countryName) {
        int index = countryList.indexOf(countryName);
        if (index == -1) {
            return "";
        } else {
            return mobileAreaCode.get(index);
        }
    }

    private final static ArrayList<String> countryList;
    private final static ArrayList<String> mobileAreaCode;

    static {
        countryList = new ArrayList<>();
        countryList.add("中国");
        countryList.add("中国香港");
        countryList.add("中国澳门");
        countryList.add("中国台湾");
        countryList.add("新加坡");
    }

    static {
        mobileAreaCode = new ArrayList<>();
        mobileAreaCode.add("86");
        mobileAreaCode.add("852");
        mobileAreaCode.add("853");
        mobileAreaCode.add("886");
        mobileAreaCode.add("65");
    }
}