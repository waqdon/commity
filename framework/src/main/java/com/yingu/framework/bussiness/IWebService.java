package com.yingu.framework.bussiness;

import java.util.Map;

import io.reactivex.Observable;

/**
 * author: create by qdwang
 * date: 2018/9/5 18:37
 * described：
 */
public interface IWebService {

    /**
     *
     * @param map
     * @return
     */
    Observable h5Request(Map<String, String> map);
}
