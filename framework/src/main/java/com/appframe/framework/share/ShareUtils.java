package com.appframe.framework.share;

import android.app.Activity;
import android.content.Intent;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author jiangkun
 * @date 2019/8/14
 */

public class ShareUtils {
    /**
     * 固定平台的分享面板。
     *
     * @param activity activity
     */
    public static void shareDisplayList(Activity activity) {
        ShareListener shareListener = new ShareListener();
        new ShareAction(activity)
                // 固定平台
                .setDisplayList(SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN)
                // 分享内容
                .withText("hello")
                // 回调监听器
                .setCallback(shareListener)
                .open();
    }

    /**
     * 单平台分享。
     *
     * @param activity   activity
     * @param shareMedia 分享平台--同友盟SHARE_MEDIA，解决app模块无法引用友盟SHARE_MEDIA问题
     */
    public static void sharePlatform(Activity activity, ShareMedia shareMedia) {
        ShareListener shareListener = new ShareListener();
        new ShareAction(activity)
                // 传入平台
                .setPlatform(shareMedia.getmYouMengMedia())
                // 分享内容
                .withText("hello")
                // 回调监听器
                .setCallback(shareListener)
                .share();
    }

    /**
     * 登录。
     *
     * @param activity   activity
     * @param shareMedia 登录平台--同友盟SHARE_MEDIA，解决app模块无法引用友盟SHARE_MEDIA问题
     */
    public static void login(Activity activity, ShareMedia shareMedia) {
        AuthListener authListener = new AuthListener();
        UMShareAPI.get(activity).getPlatformInfo(activity, shareMedia.getmYouMengMedia(), authListener);
    }

    /**
     * 必须调用这个方法--分享的Activity，否则不知道分享是否成功。
     * <p>
     * 以下参数全部使用onActivityResult的默认值即可。
     *
     * @param activity    activity
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        // 必须调用这个方法--分享的Activity，否则不知道分享是否成功。
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }
}
