package com.qdwang.demo.skin;

import android.content.res.Resources;

/**
 * author: create by qdwang
 * date: 2018/8/24 16:41
 * described：观察者接口
 */
public interface ObserverListener {
    void getResource(Resources resources, String defPackage);
}
