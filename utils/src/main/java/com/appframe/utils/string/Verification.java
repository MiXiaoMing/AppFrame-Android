package com.appframe.utils.string;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {

    /**
     * 判断手机号是否合法
     *
     * @param number
     * @return
     */
    public static boolean phoneNumber(String number) {
        boolean isValid = false;
        if (number == null || number.length() <= 0) {
            return false;
        }
        Pattern phonePattern = Pattern.compile(// sdd = space, dot, or dash
                "(\\+[0-9]+[\\- \\.]*)?" // +<digits><sdd>*
                        + "(\\([0-9]+\\)[\\- \\.]*)?" // (<digits>)<sdd>*
                        + "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])");
        Matcher matcher = phonePattern.matcher(number);
        isValid = matcher.matches();
        return isValid;
    }


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
