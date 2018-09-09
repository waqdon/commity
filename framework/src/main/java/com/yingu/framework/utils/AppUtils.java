package com.yingu.framework.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.ref.WeakReference;

/**
 * author: create by qdwang
 * date: 2018/9/6 14:10
 * described：
 */
public class AppUtils {

    private static WeakReference<AppUtils> instances;
    private AppUtils(){}
    public static synchronized AppUtils getInstances(){
        if(instances == null){
            instances = new WeakReference<>(new AppUtils());
        }
        return instances.get();
    }

    public String getVersionName(Context context){
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
