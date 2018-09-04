package com.qdwang.demo.net;

import com.qdwang.demo.net.abstracts.BaseApi;
import com.qdwang.demo.net.interfaces.NetworkApi;
import com.qdwang.demo.net.utils.Web;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe webApi跟MobileApi请求地址以及返回数据的数据都不一样，WebApi返回的数据类型是String
 */
public class WebApi extends BaseApi{

    public static final int ROLLER = 1;
    public static final int FRUIT = 2;
    public static final int WX = 3;
    public static NetworkApi networkApi;
    public static Observable observable;

    public static NetworkApi getNetworkApi(String baseUrl, HashMap map) {
        networkApi = new RtHttp.NetworkApiBuilder()
                .setBaseUrl(baseUrl)
                .addDynamicParameter(map)
//                .setConvertFactory(StringConverFactory.create())
                .setConvertFactory(GsonConverterFactory.create())
                .build();
        return networkApi;
    }

    public static NetworkApi getRollerApi(HashMap map) {
        return getNetworkApi(Web.getRollerUrl(), map);
    }

    public static NetworkApi getFruitApi(HashMap map) {
        return getNetworkApi(Web.getFruitUrl(), map);
    }

    public static NetworkApi getWxApi(HashMap map) {
        return getNetworkApi(Web.getWXUrl(), map);
    }

    public static Observable getObserable(Observable observable) {
        observable = new ObserableBuilder(observable)
                .isWeb()
                .build();
        return observable;
    }

    public static Observable webPost(HashMap map, String action, int type) {
        NetworkApi networkApi = null;
        if (type == ROLLER) {
            networkApi = getRollerApi(map);
        } else if (type == FRUIT) {
            networkApi = getFruitApi(map);
        } else if (type == WX) {
            networkApi = getWxApi(map);
        }
        String[] str = action.split("/");
        if (str.length == 1) {
            observable = networkApi.webPost(str[0]);
        } else if (str.length == 2) {
            observable = networkApi.webPost(str[0], str[1]);
        } else {
            return null;
        }
        return getObserable(observable);
    }
}
