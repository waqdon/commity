package com.qdwang.demo.utils.uiutil;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @author Toperc
 * @date 2018/6/13
 * @description
 */
public class ScreenUtil {

    private static float ORIGINAL_DENSITY = -1;  //原始屏幕密度比率

    /**
     * 重置屏幕密度
     */
    public static void resetDensity(Context context) {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 750f;
        final float DESIGN_HEIGHT = 1334f;
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

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;
    public static void setCustomDesnsity(Activity activity, final Application application){
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
            final float targetDensity = appDisplayMetrics.widthPixels / 360;
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
}
