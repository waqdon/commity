package com.yingu.framework.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.yingu.framework.net.utils.LogUtils;

import java.util.ArrayList;

/**
 * 创建时间：2017/11/29
 * 编写人：qdwang
 * 功能描述：网络监测
 */
public class NetWorkManager {

    private static NetWorkManager manager;
    private boolean isWifi;
    private boolean isConnected;
    private Context context;
    private ArrayList<OnNetWorkChangeListener> listeners = new ArrayList<OnNetWorkChangeListener>();

    public interface OnNetWorkChangeListener {
        public void onNetWorkChanged(boolean isConnected, boolean isWifi);
    }

    public NetWorkManager(Context context) {
        this.context = context.getApplicationContext();
        regeist();
    }

    public static synchronized NetWorkManager getInstance(Context context) {
        if (manager == null) {
            manager = new NetWorkManager(context);
        }
        return manager;
    }

    private void regeist() {
        IntentFilter networkconnectFilter = new IntentFilter();
        networkconnectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(connectionReceiver, networkconnectFilter);
    }

    public void destory() {
        try {
            context.unregisterReceiver(connectionReceiver);
        } catch (Exception e) {
        }
    }

    public void addListener(OnNetWorkChangeListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(OnNetWorkChangeListener listener) {
        listeners.remove(listener);
        destory();
    }

    private void changed() {
        for (OnNetWorkChangeListener listener : listeners) {
            if (listener != null) {
                listener.onNetWorkChanged(isConnected, isWifi);
            }
        }
    }

    public boolean isWifi(){
        return isWifi;
    }
    public boolean isConnected(){
        return isConnected;
    }

    BroadcastReceiver connectionReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkManager.this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info == null) {
                    isWifi = false;
                    isConnected = false;
                    changed();
                    return;
                }
                LogUtils.d1("info.getState() =" + info.getState());

                if (info.isAvailable() && info.getState() == State.CONNECTED) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE && info.isConnected()) {
                        isWifi = false;
                    } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        isWifi = true;
                    }
                    isConnected = true;
                } else {
                    isWifi = false;
                    isConnected = false;
                }
                changed();
            }
        }
    };
}
