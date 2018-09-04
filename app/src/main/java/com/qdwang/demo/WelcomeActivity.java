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
import com.qdwang.demo.http.BaseModel;
import com.qdwang.demo.http.BaseRespons;
import com.qdwang.demo.http.RetrofitHelper;
import com.qdwang.demo.retrofit.CmsService;
import com.qdwang.demo.retrofit.DataParams;
import com.qdwang.demo.skin.SkinManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http:wwww.wanandroid.com/").build();
        CmsService service = retrofit.create(CmsService.class);
        service.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //为请求提供一个取消的手段
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //请求成功
                    }

                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                    }

                    @Override
                    public void onComplete() {
                        //请求完成
                    }
                });
//        Call<ResponseBody> call = service.getBanner();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.e("yg ==" ,"yg ==" + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("yg ==" ,"call ==" + call);
//            }
//        });


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded, utf-8"), "");
        RetrofitHelper.getApiService().getBase(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseRespons<BaseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseRespons<BaseModel> baseModelBaseRespons) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
