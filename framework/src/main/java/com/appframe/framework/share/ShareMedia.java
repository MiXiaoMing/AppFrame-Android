package com.appframe.framework.share;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 同友盟SHARE_MEDIA，解决app模块无法引用友盟SHARE_MEDIA问题。
 *
 * @author jiangkun
 * @date 2019/8/15
 */

public enum ShareMedia {
    /**
     * 同友盟QQ。
     */
    QQ(SHARE_MEDIA.QQ),
    /**
     * 同友盟QQ空间。
     */
    QZONE(SHARE_MEDIA.QZONE),;

    /**
     * 解决app模块无法引用友盟SHARE_MEDIA问题。
     */
    private SHARE_MEDIA mYouMengMedia;

    /**
     * 枚举构造方法。
     *
     * @param youMengMedia 友盟SHARE_MEDIA
     */
    ShareMedia(SHARE_MEDIA youMengMedia) {
        this.mYouMengMedia = youMengMedia;
    }

    /**
     * 用于友盟分享时，提供友盟SHARE_MEDIA。
     *
     * @return 友盟SHARE_MEDIA
     */
    public SHARE_MEDIA getmYouMengMedia() {
        return mYouMengMedia;
    }
}
