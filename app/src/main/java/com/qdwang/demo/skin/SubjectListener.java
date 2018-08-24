package com.qdwang.demo.skin;

import android.content.res.Resources;

/**
 * author: create by qdwang
 * date: 2018/8/24 16:41
 * described：被观察者接口
 */
public interface SubjectListener {
    void add(ObserverListener observerListener);
    void notifyObserver(Resources resources, String defPackage);
    void remove(ObserverListener observerListener);
}
