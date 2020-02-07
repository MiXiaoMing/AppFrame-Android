package com.appframe.framework.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appframe.framework.dialog.ProgressDialog;
import com.appframe.library.component.notify.AFToast;
import com.appframe.utils.app.AppRuntimeUtil;
import com.appframe.utils.logger.Logger;

/**
 * Activity基类
 * 获取当前Activity：AppRuntimeUtil.getInstance().getCurrentActivity()
 */

public class AFBaseActivity extends AppCompatActivity {
    /**
     * 进度条
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.getLogger().d(getClass().getSimpleName() + " onCreate.");

        this.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {

            }
        });
        mProgressDialog = new ProgressDialog(this);
    }

    public ProgressDialog getDialog() {
        return mProgressDialog;
    }

    public void showProgressDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShow()) {
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShow()) {
            mProgressDialog.dismiss();
        }
    }

    public void myToast(String message) {
        AFToast.showShort(AppRuntimeUtil.getInstance().getCurrentActivity(), message);
    }

    public void myToast(int id) {
        AFToast.showShort(AppRuntimeUtil.getInstance().getCurrentActivity(), id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.getLogger().d(getClass().getSimpleName() + " onDestroy.");
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 16 && Build.VERSION.SDK_INT < 19) {
            // lower api
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
