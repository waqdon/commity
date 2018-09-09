package com.yingu.framework.bussiness;

import com.yingu.framework.net.RxHttp;
import com.yingu.framework.net.base.BaseApi;
import com.yingu.framework.net.base.converter.DesGsonConverterFactory;

import java.util.Map;

import io.reactivex.Observable;

/**
 * author: create by qdwang
 * date: 2018/9/5 09:34
 * described：
 */
public class MobileService extends BaseApi implements INetService {

    private static NetworkServiceApi networkServiceApi;

    private static INetService intances;
    private MobileService(){}
    public static synchronized INetService getIntances(){
        if(intances == null){
            synchronized (MobileService.class){
                if(intances == null){
                    intances = new MobileService();
                }
            }
        }
        return intances;
    }

    private static NetworkServiceApi getNetworkServiceApi(){
        if(networkServiceApi == null){
            networkServiceApi = new RxHttp.NetworkApiBuilder()
                    .setConvertFactory(DesGsonConverterFactory.create())
                    .build()
                    .create(NetworkServiceApi.class);
        }
        return networkServiceApi;
    }

    @Override
    public Observable appStart(Map<String, String> map) {
        return getObservable(getNetworkServiceApi().appStart(toReBody(map)));
    }

    @Override
    public Observable getDataOfIndex(Map<String, String> map) {
        return getObservable(getNetworkServiceApi().getDataOfIndex(toReBody(map)));
    }
}
