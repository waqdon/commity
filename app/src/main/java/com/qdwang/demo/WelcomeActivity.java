package com.qdwang.demo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.qdwang.demo.base.BaseActivity;
import com.qdwang.demo.skin.ObserverListener;
import com.qdwang.demo.skin.SkinManager;

import java.io.File;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.image_view)ImageView imageView;

    private String defPackage;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/theme-debug.zip";

    public static void toActivity(Context context){
        Intent intent =new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void createView() {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        SkinManager.getSkinManager().load(path);
//                int id = resources.getIdentifier("banner_ygph_sy", "drawable", defPackage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageDrawable(SkinManager.getSkinManager().getDrawable("banner_ygph_sy"));
            }
        }, 200);
    }

    @Override
    protected void requestPermissions() {
        super.requestPermissions();
        Resources resources = getResurces(this, path);
//        Log.e("yg=================", "Environment.getExternalStorageDirectory().getAbsolutePath()+\"theme-debug.apk\" = " + Environment.getExternalStorageDirectory().getAbsolutePath()+"/theme-debug.skin");
        Log.e("yg=================", "resources = " + resources);
    }

    /**
     * 皮肤包的位置
     */
    public Resources getResurces(Context context, String skinPkgPath){
        try {
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
