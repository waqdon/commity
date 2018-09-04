package com.qdwang.demo.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {


    private final static String BASE_URL = "";
    private final static long mConnectTimeOut = 15;
    private final static long mReadTimeOut = 10;
    private final static long mWriteTimeOut = 15;

    private static ApiService apiService;
    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(mConnectTimeOut, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(mReadTimeOut, TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(mWriteTimeOut, TimeUnit.SECONDS)//写取超时时间
                .retryOnConnectionFailure(false)//错误是否重连
//                .connectionPool()
                .build();
    }

    public static ApiService getApiService(){
        if(apiService == null){
            apiService = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//设置rxjava适配器
                    .addConverterFactory(GsonConverterFactory.create())//设置json转换器
                    .client(client)
                    .build()
                    .create(ApiService.class);
        }
        return apiService;
    }
}
