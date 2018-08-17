package com.qdwang.demo.base;

import android.app.Application;
import android.content.Context;

/**
 * author: create by qdwang
 * date: 2018/8/16 16:00
 * described：
 */
public class MultiDexApplication extends Application {

    public MultiDexApplication() {
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}
