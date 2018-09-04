package com.qdwang.demo.net.interceptor;

import android.content.Context;

import com.qdwang.demo.net.data.CommonData;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe
 */
public class HeaderInterceptor implements Interceptor{

    private Context context;
    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("sessionId", CommonData.getUserInfo(context).sessionId); //添加sessionId
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
