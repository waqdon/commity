package com.yingu.framework.net.base;

import com.yingu.framework.net.exceptions.ApiThrowExcepitionFun1;
import com.yingu.framework.net.parser.GsonParser;
import com.yingu.framework.paramter.CommonParams;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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

    public static Observable getObservable(Observable obsvable){
        if(observable == null){
            observable = new ObserableBuilder(obsvable)
//                    .addApiException()
                    .build();
        }
        return observable;
    }

    public static class ObserableBuilder {

        private Observable observable;
        private boolean apiException;
        private Scheduler subscribeScheduler;
        private Scheduler obscerveScheduler;

        public void setObscerveScheduler(Scheduler obscerveScheduler) {
            this.obscerveScheduler = obscerveScheduler;
        }

        public void setSubscribeScheduler(Scheduler subscribeScheduler) {
            this.subscribeScheduler = subscribeScheduler;
        }

        public ObserableBuilder(Observable o) {
            this.observable = o;
        }

        public ObserableBuilder addApiException() {
            apiException = true;
            return this;
        }

        public Observable build() {
            if (apiException) {
                observable = observable.flatMap(new ApiThrowExcepitionFun1());
            }
            if (subscribeScheduler != null) {
                observable = observable.subscribeOn(subscribeScheduler);
            } else {
                observable = observable.subscribeOn(Schedulers.io());
            }
            if (obscerveScheduler != null) {
                observable = observable.observeOn(obscerveScheduler);
            } else {
                observable = observable.observeOn(AndroidSchedulers.mainThread());
            }
            return observable;
        }
    }
}
