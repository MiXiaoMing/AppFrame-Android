package com.appframe.utils.aoglrithm;

import java.util.UUID;

/**
 * 随机数
 */
public class Random {

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
