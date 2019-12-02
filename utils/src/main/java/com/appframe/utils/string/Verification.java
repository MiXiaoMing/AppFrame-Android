package com.appframe.utils.string;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {

    /**
     * 数字 字符 中文
     */
    public static final String regexp_digit_character_chinesecharacter = "^[a-zA-Z0-9\u4e00-\u9fa5]+$";
    /**
     * 数字 字符
     */
    public static final String regexp_digit_character = "^[a-zA-Z0-9]+$";

    public static boolean regexp(String text, String regexp) {
        if (!TextUtils.isEmpty(text) && text.matches(regexp)) {
            return true;
        }
        return false;
    }
}
