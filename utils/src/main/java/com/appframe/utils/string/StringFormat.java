package com.appframe.utils.string;

import java.text.DecimalFormat;

public class StringFormat {

    /**
     * 字符串格式化为钱币格式
     * @param data
     * @return
     */
    public static String formatInt(String data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Long.parseLong(data));
    }
}
