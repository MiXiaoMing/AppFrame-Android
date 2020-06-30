package com.appframe.framework.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appframe.library.component.notify.AFToast;
import com.appframe.utils.app.AppRuntimeUtil;
import com.appframe.utils.logger.Logger;

import java.util.ArrayList;

/**
 * Activity基类
 * 获取当前Activity：AppRuntimeUtil.getInstance().getCurrentActivity()
 */

public class AFBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {

            }
        });
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

    /**
     * 权限回调Handler
     */
    private PermissionHandler mHandler;
    //权限相关
    private boolean allAllow = true;
    private int index = 0;
    private int length = 0;
    private String[] perms = null;
    private ArrayList<String> defineperms = new ArrayList<>();

    /**
     * 请求权限
     *
     * @param permissions 权限列表
     * @param handler     回调
     */
    public void requestPermission(final String[] permissions, final PermissionHandler handler) {
        mHandler = handler;
        index = 0;
        length = permissions.length;
        perms = permissions;
        allAllow = true;
        defineperms.clear();
        getPermission();
    }

    private void getPermission(){
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this,
                perms[index])
                != PackageManager.PERMISSION_GRANTED) {
            //当用户拒绝掉权限时.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, perms[index])) {
                toSetDialog(perms[index]);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{perms[index]}, 111);
            }
        }else {
            if (++index < length){
                getPermission();
            }else {
                if (allAllow){
                    mHandler.onGranted();
                }else {
                    showDialog(defineperms);
                }
            }
        }
    }

    private void toSetDialog(final String permission){
        ActivityCompat.requestPermissions(AFBaseActivity.this, new String[]{permission}, 111);
    }

    private void showDialog(final ArrayList<String> list) {
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请手动开启权限").setMessage("在设置-应用信息-权限管理中开启权限，以保证功能的正常使用．").setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHandler.onDenied(list);
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", AFBaseActivity.this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                dialog.dismiss();
            }
        }).setNegativeButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                mHandler.onDenied(list);
                dialog.dismiss();
            }
        }).show();
    }



    /**
     * 权限回调接口
     */
    public interface PermissionHandler {
        /**
         * 权限通过
         */
        void onGranted();

        /**
         * 权限拒绝
         */
        void onDenied(ArrayList<String> list);

    }
}
