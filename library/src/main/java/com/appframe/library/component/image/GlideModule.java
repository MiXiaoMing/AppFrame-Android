package com.appframe.library.component.image;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

/**
 * 修改glide属性值
 */

public class GlideModule implements com.bumptech.glide.module.GlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig());
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }


}
