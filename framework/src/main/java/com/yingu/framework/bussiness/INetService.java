package com.yingu.framework.bussiness;

import java.util.Map;

import io.reactivex.Observable;

/**
 * author: create by qdwang
 * date: 2018/9/5 09:30
 * described：
 */
public interface INetService {

    /**
     * app启动统计
     * @param map
     * @return
     */
    Observable appStart(Map<String, String> map);

    /**
     * 获取个人信息接口
     * @param map
     * @return
     */
    Observable getDataOfIndex(Map<String, String> map);
}
