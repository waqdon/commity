package com.yingu.framework.net.base;

import com.yingu.framework.net.parser.GsonParser;
import com.yingu.framework.paramter.CommonParams;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * author: create by qdwang
 * date: 2018/9/4 15:15
 * described：
 */
public class BaseApi {

    private static Observable observable;
    /**
     * 处理入参在这里，公参也统一在该方法中处理
     * @param map 业务入参
     * @return
     */
    public static RequestBodyParams toReBody(Map<String, String> map) {
        RequestBodyParams params;
        Map<String, String> mm = new HashMap<>();
        Map<String, String> allMap = new HashMap<>();
        String commonParams = CommonParams.getInstances().getCommonParams();
        String dataParame = GsonParser.getInstance().getGson().toJson(map);
        mm.put("commonParams", commonParams);
        mm.put("dataParams", dataParame);
        allMap.put("jsonParams", GsonParser.getInstance().mapToJson(mm));
        String json = GsonParser.getInstance().mapToJson(allMap);
        params =(RequestBodyParams) GsonParser.getInstance().fromJson(json, RequestBodyParams.class);
        return params;
    }

    public Observable getObservable(Observable obsvable){
        return observable = new ObserableBuilder(obsvable)
//                    .addApiException()
                    .build();
    }
}
