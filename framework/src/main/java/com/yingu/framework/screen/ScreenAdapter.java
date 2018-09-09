package com.yingu.framework.screen;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.yingu.framework.net.utils.LogUtils;

/**
 * author: create by qdwang
 * date: 2018/9/5 15:35
 * described：UI屏幕适配类
 */
public class ScreenAdapter {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;
    private static boolean isWidth = false;      //以宽为基准进行缩放
    private static int widthNum = 0;       //设计图宽
    private static boolean isHeight = true;     //以高为基准进行缩放，如果同时设置或同时未设置的时候以宽为准
    private static int heightNum = 0;      //设计图高
    private static boolean isIgnore = true;     //是否忽略状态栏高度
    private static int multiple = 0;       //几倍图，设计师进行设计的时候会确定

    private static float ORIGINAL_DENSITY = -1;  //原始屏幕密度比率

    /**
     * 该方法的适配方案由今日头条技术团队开源提供
     * @param activity
     */
    public static void setCustomDesnsity(Activity activity){
        final Application application = activity.getApplication();
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if(sNoncompatDensity == 0){
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(newConfig != null && newConfig.fontScale > 0){
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });

//            float targetDensity = getTargetDensity(application, appDisplayMetrics);
//            final float targetDensity = appDisplayMetrics.widthPixels / 360;
            final float targetDensity = appDisplayMetrics.heightPixels / 640;
            final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
            final int targetDensityDpi =(int) (160 * targetScaledDensity);

            appDisplayMetrics.density = targetDensity;
            appDisplayMetrics.scaledDensity = targetScaledDensity;
            appDisplayMetrics.densityDpi = targetDensityDpi;

            final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        }
    }

    /**
     * 获取缩放后的Density
     * @param displayMetrics
     * @return
     */
    private static float getTargetDensity(Application application, DisplayMetrics displayMetrics){
        float targetDensity = 0;
        float standardDp;
        if (isWidth){
            standardDp = widthNum / multiple;
            targetDensity = displayMetrics.widthPixels / standardDp;
        }else if (isHeight){
            standardDp = heightNum / multiple;
            if (isIgnore){
                targetDensity = displayMetrics.heightPixels / standardDp;
                android.util.Log.e("SmartLayout","targetDensity_yes = " + targetDensity);
            }else {
                int statusBarHeight = -1;
                //获取status_bar_height资源的ID
                int resourceId = application.getBaseContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    //根据资源ID获取响应的尺寸值
                    statusBarHeight = application.getBaseContext().getResources().getDimensionPixelSize(resourceId);
                }
                LogUtils.e("SmartLayout","statusBarHeight = " + statusBarHeight);
                targetDensity = ((float)(displayMetrics.heightPixels - statusBarHeight)) / (standardDp - statusBarHeight / multiple);
                LogUtils.e("SmartLayout","targetDensity_no = " + targetDensity);
            }
        }
        return targetDensity;
    }

    /**
     * 该方案是由网上的大牛根据今日头条技术团队提供开源适配方案自己改写，其原理和上述方法类似
     * @param context
     */
    public static void resetDensity(Context context) {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 750f;
        final float DESIGN_HEIGHT = 1334f;
        //屏幕尺寸
        final float DESTGN_INCH = 5.0f;
        //大屏调节因子，范围0~1，因屏幕同比例放大视图显示非常傻大憨，用于调节感官度
        final float BIG_SCREEN_FACTOR = 0.8f;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        //确定放大缩小比率
        float rate = Math.min(dm.widthPixels, dm.heightPixels) / Math.min(DESIGN_WIDTH, DESIGN_HEIGHT);
        //确定参照屏幕密度比率
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / DisplayMetrics.DENSITY_DEFAULT;
        //确定最终屏幕密度比率
        float relativeDensity = referenceDensity * rate;
        if (ORIGINAL_DENSITY == -1) {
            ORIGINAL_DENSITY = dm.density;
        }
        if (relativeDensity > ORIGINAL_DENSITY) {
            relativeDensity = ORIGINAL_DENSITY + (relativeDensity - ORIGINAL_DENSITY) * BIG_SCREEN_FACTOR;
        }
        dm.density = relativeDensity;
        dm.densityDpi = (int) (relativeDensity * DisplayMetrics.DENSITY_DEFAULT);
        dm.scaledDensity = relativeDensity;
    }
}
