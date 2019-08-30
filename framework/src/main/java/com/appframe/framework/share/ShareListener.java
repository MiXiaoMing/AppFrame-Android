package com.appframe.framework.share;

import android.util.Log;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 自定义友盟UMShareListener，监听分享回调。
 *
 * @author jiangkun
 * @date 2019/8/14
 */

public class ShareListener implements UMShareListener {
    private static final String TAG = "ShareListener";

    /**
     * 分享开始的回调。
     *
     * @param platform 平台类型
     */
    @Override
    public void onStart(SHARE_MEDIA platform) {
        // 控制台看日志。
        Log.e(TAG, platform + "开始了");
    }

    /**
     * 分享成功的回调。
     *
     * @param platform 平台类型
     */
    @Override
    public void onResult(SHARE_MEDIA platform) {
        // 控制台看日志。
        Log.e(TAG, platform + "成功了");
    }

    /**
     * 分享失败的回调。
     *
     * @param platform 平台类型
     * @param t        错误原因
     */
    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        // 控制台看日志。
        Log.e(TAG, platform + "失败了" + t.getMessage());
    }

    /**
     * 分享取消的回调。
     *
     * @param platform 平台类型
     */
    @Override
    public void onCancel(SHARE_MEDIA platform) {
        // 控制台看日志。
        Log.e(TAG, platform + "取消了");
    }
}
