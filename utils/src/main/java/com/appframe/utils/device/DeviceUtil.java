package com.appframe.utils.device;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class DeviceUtil {

    // 获取设备品牌
    public static String getBrand() {
        return Build.BRAND;
    }

    // 获取设备型号
    public static String getModel() {
        return Build.MODEL;
    }

    // 获取设备唯一标志，如果是手机，取imie，如果是pad，取mac地址
    public static String getUUID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();

        if (!TextUtils.isEmpty(deviceId)) {
            try {
                deviceId = URLEncoder.encode(deviceId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
            return deviceId;
        }

        //7.0版本以上方法：
        deviceId = android.provider.Settings.Secure.getString(context.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(deviceId))
            return deviceId;

        //没取到-取mac地址-
        String mac = getMacAddress(context);
        if (!TextUtils.isEmpty(mac)) {
            return "mac" + mac;
        }

        return "";
    }

    // 获取设备mac地址
    private static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (null == wifiInfo || null == wifiInfo.getMacAddress()) {
            return "";
        }
        return wifiInfo.getMacAddress().replaceAll(":", "");
    }

    // 获取手机号码
    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = mTelephonyMgr.getLine1Number();
        if (!TextUtils.isEmpty(number)) {
            return number;
        }
        return "";
    }

    // 获取SIM卡的ismi码，SIM卡的唯一标识
    public static String getImsi(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String scriber = tm.getSubscriberId();
        if (!TextUtils.isEmpty(scriber)) {
            try {
                scriber = URLEncoder.encode(scriber, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
            return scriber;
        }

        return "";
    }

    // 判断设备是否有sd卡
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getCpuABI() {
        return Build.CPU_ABI;
    }
}
