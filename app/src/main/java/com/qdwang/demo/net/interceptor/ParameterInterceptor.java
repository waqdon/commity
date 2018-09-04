package com.qdwang.demo.net.interceptor;

import com.qdwang.demo.net.data.CommonData;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe
 */
public class ParameterInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //get请求后面追加共同的参数
        HttpUrl httpUrl = request.url().newBuilder()   //使用addQueryParameter()在url后面添加参数
                .addQueryParameter("userId", CommonData.getUid()+"")
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
