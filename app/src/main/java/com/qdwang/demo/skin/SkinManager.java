package com.qdwang.demo.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class SkinManager{

    private static SkinManager skinManager;
    private SkinManager(){}

    private Context context;
    private Resources resources;
    private String mDefPackage;

    public static synchronized SkinManager getSkinManager(){
        if(null == skinManager){
            synchronized (SkinManager.class){
                if(null == skinManager){
                    skinManager = new SkinManager();
                }
            }
        }
        return skinManager;
    }

    /**
     *
     * @param context
     */
    public void init(Context context){
        this.context = context.getApplicationContext();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            resources =(Resources) msg.obj;
            if(resources != null){
                mDefPackage = msg.getData().getString("defPackage");
            }else {
                mDefPackage = context.getPackageName();
                resources = context.getResources();
            }
        }
    };

    /**
     *
     * @param skinPkgPath
     */
    public void load(final String skinPkgPath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Bundle bundle = new Bundle();
                try {
                    File file = new File(skinPkgPath);
                    if(file == null || !file.exists()){
                        handler.sendMessage(message);
                        return;
                    }
                    if(context == null){
                        new Throwable("context not is init");
                    }
                    PackageManager pm = context.getPackageManager();
                    PackageInfo packageInfo = pm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                    String defPackage = packageInfo.packageName;
                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, skinPkgPath);
                    Resources superRes = context.getResources();
                    Resources skinResource = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());
                    bundle.putString("defPackage", defPackage);
                    message.setData(bundle);
                    message.obj = skinResource;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Drawable getDrawable(String drawableName){
        Drawable drawable = null;
        if(resources == null){
            new Throwable("resources is null");
            return drawable;
        }
        int id = resources.getIdentifier(drawableName, "drawable", mDefPackage);
        drawable = resources.getDrawable(id);
        return drawable;
    }

    public int getColor(String colorName){
        int color = 0;
        if(resources == null){
            new Throwable("context is null");
            return color;
        }
        int id = resources.getIdentifier(colorName, "color", mDefPackage);
        color = resources.getColor(id);
        return color;
    }
}
