package com.yingu.framework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.yingu.framework.net.utils.LogUtils;

import org.json.JSONStringer;

/**
 * author: create by qdwang
 * date: 2018/9/6 13:48
 * described：
 */
@SuppressLint("WrongConstant")
public class NetWorkUtils {

    private static String deviceType = "AndroidMobile";

    public NetWorkUtils() {
    }

    public static void setDeviceType(String deviceType) {
        deviceType = deviceType;
    }

    public static String getDeviceType() {
        return deviceType;
    }

    public static boolean isNetworkValidate(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
        return cm.getActiveNetworkInfo() != null ? cm.getActiveNetworkInfo().isAvailable() : false;
    }

    public static boolean isConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
        return cm.getActiveNetworkInfo() != null ? cm.getActiveNetworkInfo().isConnected() : false;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager conMan = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        if (wifi == State.CONNECTED) {
            LogUtils.LogOut("isWifi=true");
            return true;
        } else {
            LogUtils.LogOut("isWifi=false");
            return false;
        }
    }
}
