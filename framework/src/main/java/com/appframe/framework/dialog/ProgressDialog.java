package com.appframe.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appframe.framework.R;

/**
 * 进度条
 *
 * @author jiangkun
 * @date 2020/2/7
 */

public class ProgressDialog {
    private Dialog mDialog;
    private ImageView ImageAnimation;
    private LinearLayout llBg;

    public ProgressDialog(Context context) {
        mDialog = new Dialog(context, R.style.Theme_progress_dialog);
        mDialog.setCancelable(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    dialog.dismiss();
                }
                return false;
            }
        });
        mDialog.setContentView(R.layout.progress_dialog);
        ImageAnimation = mDialog.findViewById(R.id.id_animate);
        llBg = mDialog.findViewById(R.id.ll_bg);
    }

    /**
     * glide 透明背景变黑， 使用动画代替loading
     */
    private void showAnimation() {
        RotateAnimation animation = new RotateAnimation(0, 360 * 10000, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(-1);
        animation.setDuration(1000 * 10000);
        if (ImageAnimation != null) {
            ImageAnimation.setAnimation(animation);
        }
    }

    private void hiddenAnimation() {
        if (ImageAnimation != null) {
            ImageAnimation.clearAnimation();
        }
    }

    /**
     * 隐藏白色背景
     *
     * @param isShowBG
     */
    public void showBG(boolean isShowBG) {
        if (llBg != null) {
            if (!isShowBG) {
                llBg.setBackgroundResource(0);
            } else {
                llBg.setBackgroundResource(R.drawable.block_loading);
            }
        }
    }

    public void show() {
        try {
            // 防止dialog的崩溃
            if (!mDialog.isShowing()) {
                mDialog.show();
                showAnimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            // 防止dialog的崩溃
            if (mDialog != null) {
                hiddenAnimation();
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShow() {
        return mDialog.isShowing();
    }
}