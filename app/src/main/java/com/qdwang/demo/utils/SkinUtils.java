package com.qdwang.demo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

/**
 * author: create by qdwang
 * date: 2018/8/23 14:05
 * described：
 */
public class SkinUtils {

    /**
     * 皮肤包的位置
     */
    public Resources getResurces(Context context, String skinPkgPath){
        try {
            File file = new File(skinPkgPath);
            if(file == null || !file.exists()){
                return null;
            }

            PackageManager mPm = context.getPackageManager();
            PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
//            skinPackageName = mInfo.packageName;

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);

            Resources superRes = context.getResources();
            Resources skinResource = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());

            PreferencesUtils.putString(context, "", skinPkgPath);

//            skinPath = skinPkgPath;
//            isDefaultSkin = false;
            return skinResource;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
