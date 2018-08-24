package com.qdwang.demo.base;

import com.qdwang.demo.skin.SkinManager;
import com.qdwang.demo.utils.uiutil.ScreenUtil;

/**
 * author: create by qdwang
 * date: 2018/8/16 15:59
 * described：
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
//        ScreenUtil.resetDensity(this);
        SkinManager.getSkinManager().init(this);
    }
}
