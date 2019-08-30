package com.appframe.library.component.image;

import android.content.Context;
import android.widget.ImageView;

import com.appframe.library.Constants;
import com.appframe.utils.logger.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {


    /**
     * 圆形图片：本地资源
     * @param context
     * @param resID
     * @param view
     */
    public static void circle(Context context, int resID, ImageView view) {
        if (context != null && view != null) {
            Glide.with(context).load(resID).apply((new RequestOptions()).dontAnimate().placeholder(Constants.default_image).error(Constants.default_image).transform(new GlideCircleTransform())).into(view);
        } else {
            Logger.getLogger().e("参数错误，有空指针");
        }

    }

    /**
     * 圆形图片：网络资源
     * @param context
     * @param filePath
     * @param view
     */
    public static void circle(Context context, String filePath, ImageView view) {
        if (context != null && filePath != null && !filePath.isEmpty() && view != null) {
            if (filePath.toLowerCase().endsWith("gif")) {
                Glide.with(context).asGif().load(filePath).apply((new RequestOptions()).dontAnimate().placeholder(Constants.default_image).error(Constants.default_image).transform(new GlideCircleTransform())).into(view);
            } else {
                Glide.with(context).load(filePath).apply((new RequestOptions()).dontAnimate().placeholder(Constants.default_image).error(Constants.default_image).transform(new GlideCircleTransform())).into(view);
            }
        } else {
            Logger.getLogger().e("参数错误，有空指针");
        }

    }
}
