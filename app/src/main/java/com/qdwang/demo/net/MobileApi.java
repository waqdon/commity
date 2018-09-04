package com.qdwang.demo.net;

import com.qdwang.demo.net.abstracts.BaseApi;
import com.qdwang.demo.net.interfaces.NetworkApi;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe
 */
public class MobileApi extends BaseApi{

    public static NetworkApi networkApi;
    public static Observable obserable;

    public static NetworkApi getNetworkApi() { //使用NetworkApiBuilder创建networkApi
        if(networkApi==null ){
            networkApi = new RtHttp.NetworkApiBuilder()
                    .addSession()               //添加sessionId
                    .addParameter()             //添加固定参数
                    .build();
        }
        return networkApi;
    }

    public static Observable getObserable(Observable observable) {
        obserable = new ObserableBuilder(observable)
                .addApiException()   //添加apiExcetion过滤
                .build();
        return obserable;
    }

    public static Observable response(HashMap map, int protocolId) {
        RequestBody body = toBody(map);
        return getObserable(getNetworkApi().response(protocolId, body));
    }
}
