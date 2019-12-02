package com.appframe.library.component.image;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.appframe.library.Constants;
import com.appframe.utils.logger.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void normal(final Context context, final String filePath, final int defaultRes, final ImageView imageView) {
        if (context != null && filePath != null && !filePath.isEmpty() && imageView != null) {
            ViewTreeObserver vto = imageView.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int height = imageView.getMeasuredHeight();
                    int width = imageView.getMeasuredWidth();
                    Logger.getLogger().d("ImageUtil + 测量控件大小：" + width + "..." + height);
                    if (width != 0 && height != 0) {
                        String finalFilePath = filePath;
                        if (filePath.startsWith("http")) {
                            finalFilePath = finalFilePath + "?rw=" + width + "&rh=" + height;
                        }

                        if (filePath.toLowerCase().endsWith("gif")) {
                            Glide.with(context).asGif().load(finalFilePath).apply((new RequestOptions()).override(width, height).centerCrop().dontAnimate().placeholder(defaultRes).error(defaultRes)).into(imageView);
                        } else {
                            Glide.with(context).load(finalFilePath).apply((new RequestOptions()).override(width, height).centerCrop().dontAnimate().placeholder(defaultRes).error(defaultRes)).into(imageView);
                        }

                        return true;
                    } else {
                        if (filePath.toLowerCase().endsWith("gif")) {
                            Glide.with(context).asGif().load(filePath).apply((new RequestOptions()).centerCrop().dontAnimate().placeholder(defaultRes).error(defaultRes)).into(imageView);
                        } else {
                            Glide.with(context).load(filePath).apply((new RequestOptions()).centerCrop().dontAnimate().placeholder(defaultRes).error(defaultRes)).into(imageView);
                        }

                        return true;
                    }
                }
            });
        } else {
            Logger.getLogger().e("参数错误，有空指针");
        }
    }


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
