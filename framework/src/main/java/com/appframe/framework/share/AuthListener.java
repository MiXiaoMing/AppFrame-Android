package com.appframe.framework.share;

import android.util.Log;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 自定义友盟UMAuthListener，监听登录回调。
 *
 * @author jiangkun
 * @date 2019/8/15
 */

public class AuthListener implements UMAuthListener {
    private static final String TAG = "AuthListener";

    @Override
    public void onStart(SHARE_MEDIA shareMedia) {
        // 控制台看日志。
        Log.e(TAG, "开始了");
    }

    @Override
    public void onComplete(SHARE_MEDIA shareMedia, int i, Map<String, String> map) {
        // 控制台看日志。
        Log.e(TAG, "完成了");
    }

    @Override
    public void onError(SHARE_MEDIA shareMedia, int i, Throwable throwable) {
        // 控制台看日志。
        Log.e(TAG, "失败：" + throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA shareMedia, int i) {
        // 控制台看日志。
        Log.e(TAG, "取消了");
    }
}
