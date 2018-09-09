package com.yingu.framework.net.base.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yingu.framework.net.base.ResponseInfo;
import com.yingu.framework.net.des.EncipherProxy;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * author: create by qdwang
 * date: 2018/9/4 17:24
 * described：
 */
public class DesGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    DesGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value){
        //TODO 处理出参解密
        try {
            String response = EncipherProxy.decrypt(value.string());
            ResponseInfo<T> responseInfo = gson.fromJson(response, ResponseInfo.class);
            T result =(T) responseInfo;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            value.close();
        }
        return null;
    }
}
