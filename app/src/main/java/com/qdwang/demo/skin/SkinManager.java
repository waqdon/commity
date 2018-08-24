package com.qdwang.demo.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;

import java.io.File;
import java.lang.reflect.Method;


public class SkinManager{

    private static SkinManager skinManager;
    private SkinManager(){}

    private Context context;

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

    /**
     *
     * @param skinPkgPath
     * @param callBack
     */
    private void load(String skinPkgPath, final CallBack callBack){
        if(callBack == null){
            return;
        }
        new AsyncTask<String, Void, Resources>(){

            private String defPackage;

            @Override
            protected Resources doInBackground(String... strings) {
                try {
                    if(strings.length == 1){
                        String skinPkgPath = strings[0];
                        File file = new File(skinPkgPath);
                        if(file == null || !file.exists()){
                            return null;
                        }
                        PackageManager pm = context.getPackageManager();
                        PackageInfo packageInfo = pm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                        defPackage = packageInfo.packageName;
                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, skinPkgPath);
                        Resources superRes = context.getResources();
                        Resources skinResource = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());
                        return skinResource;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Resources resources) {
                if(resources == null){
                    defPackage = context.getPackageName();
                    resources = context.getResources();
                }
                callBack.getResource(resources, defPackage);
            }
        }.execute(skinPkgPath);
    }
}
