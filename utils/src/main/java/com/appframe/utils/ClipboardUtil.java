package com.appframe.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtil {

    public static boolean copy(Context context, String data) {
        try {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", "这里是要复制的文字");
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
