package com.qdwang.demo.base;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.qdwang.demo.utils.uiutil.ScreenUtil;

/**
 * author: create by qdwang
 * date: 2018/8/16 15:53
 * described：基础类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView();
        requestPermission();
    }

    protected abstract void createView();

    /**
     * 在某种情况下需要Activity的视图初始完毕Application中DisplayMetrics相关参数才能起效果，例如toast.
     * @param
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ScreenUtil.resetDensity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Fragment 逐个出栈
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void finish() {
        super.finish();
//        if (mTipView != null && mTipView.parent != null) {
//            mWindowManager.removeView(mTipView)
//        }
    }

    /**
     * 请求授权
     */
    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){ //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            //TODO
            requestPermissions();
        }
    }

    /**
     * 权限申请返回结果
     * @param requestCode 请求码
     * @param permissions 权限数组
     * @param grantResults  申请结果数组，里面都是int类型的数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //同意权限申请
                    //TODO
                    requestPermissions();
                }else { //拒绝权限申请
                    Toast.makeText(this,"权限被拒绝了",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    protected void requestPermissions(){}
}
