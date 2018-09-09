package com.yingu.framework.paramter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.yingu.framework.utils.AppUtils;
import com.yingu.framework.utils.DeviceUtils;
import com.yingu.framework.utils.SharedPreferencesUtils;

/**
 * author: create by qdwang
 * date: 2018/9/6 13:41
 * described：公共参数封装类
 */
public class CommonParams {
    private Context context;

    private static CommonParams instances;
    private CommonParams(){}
    public static synchronized CommonParams getInstances(){
        if(instances == null){
            instances = new CommonParams();
        }
        return instances;
    }

    /**
     * 在调用接口前初始化上下文，供请求接口获取公共参数
     * @param context
     */
    public void initCommonParams(Context context){
        this.context = context;
    }

    /**
     * 获取请求接口的封装的公共参数
     * @return
     */
    public String getCommonParams() {
        String result = null;
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("token", SharedPreferencesUtils.getInstance(context).shareReadString("token", ""));
            jsonObject.addProperty("version", AppUtils.getInstances().getVersionName(context));
            jsonObject.addProperty("deviceCode",DeviceUtils.getInstances().getUniqueId(context));
            jsonObject.addProperty("sourceType", "1");
            jsonObject.addProperty("os", DeviceUtils.getOsVersion());
            jsonObject.addProperty("channel", "");
            result = jsonObject.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }
}
