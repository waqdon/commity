package com.qdwang.demo.net;

import android.content.Context;
import android.text.TextUtils;

import com.qdwang.demo.net.abstracts.ApiSubscriber;
import com.qdwang.demo.net.data.Mobile;
import com.qdwang.demo.net.interceptor.DynamicParameterInterceptor;
import com.qdwang.demo.net.interceptor.HeaderInterceptor;
import com.qdwang.demo.net.interceptor.LogInterceptor;
import com.qdwang.demo.net.interceptor.ParameterInterceptor;
import com.qdwang.demo.net.interfaces.NetworkApi;
import com.qdwang.demo.net.utils.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe 网络请求总入口
 */
public class RtHttp {
    public static final String TAG = "RtHttp";
    private static RtHttp instance = new RtHttp();
    private Observable observable;
    private static WeakReference<Context> wrContext;
    private boolean isShowWaitingDialog;

    /**
     * 设置Context，使用弱引用
     * @param context
     * @return
     */
    public static RtHttp with(Context context){
        wrContext = new WeakReference<>(context);
        return instance;
    }

    /**
     * 设置是否显示加载动画
     * @param showWaitingDialog
     * @return
     */
    public RtHttp setShowWaitingDialog(boolean showWaitingDialog){
        isShowWaitingDialog = showWaitingDialog;
        return instance;
    }

    /**
     * 设置observable
     * @param observable
     * @return
     */
    public RtHttp setObservable(Observable observable){
        this.observable = observable;
        return instance;
    }

    /**设置ApiSubscriber
     * @param subscriber
     * @return
     */
    public RtHttp subscriber(ApiSubscriber subscriber){
        subscriber.setmCtx(wrContext.get());  //给subscriber设置Context，用于显示网络加载动画
        subscriber.setShowWaitDialog(isShowWaitingDialog); //控制是否显示动画
        //RxJava 方法
        observable.subscribe(subscriber);
        return instance;
    }

    /**
     * 使用Retrofit.Builder和OkHttpClient.Builder构建NetworkApi
     */
    public static class NetworkApiBuilder{
        private String baseUrl;  //根地址
        private boolean isAddSession; //是否添加sessionid
        private HashMap<String,String> addDynamicParameterMap; //url动态参数
        private boolean isAddParameter; //url是否添加固定参数
        private Retrofit.Builder rtBuilder;
        private OkHttpClient.Builder okBuild;
        private Converter.Factory convertFactory;

        public NetworkApiBuilder setConvertFactory(Converter.Factory convertFactory) {
            this.convertFactory = convertFactory;
            return this;
        }

        public NetworkApiBuilder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public NetworkApiBuilder addParameter(){
            isAddParameter = true;
            return this;
        }


        public NetworkApiBuilder addSession() {
            isAddSession = true;
            return this;
        }

        public NetworkApiBuilder addDynamicParameter(HashMap map) {
            addDynamicParameterMap = map;
            return this;
        }


        public NetworkApi build(){
            rtBuilder= new Retrofit.Builder();
            okBuild = new OkHttpClient().newBuilder();
            if(!TextUtils.isEmpty(baseUrl)){
                rtBuilder.baseUrl(baseUrl);
            }else{
                rtBuilder.baseUrl(Mobile.getBaseUrl());
            }
            if(isAddSession){
                okBuild.addInterceptor(new HeaderInterceptor(wrContext.get()));
            }
            if(isAddParameter){
                okBuild.addInterceptor(new ParameterInterceptor());
            }
            if(addDynamicParameterMap!=null){
                okBuild.addInterceptor(new DynamicParameterInterceptor(addDynamicParameterMap));
            }
            //warning:must in the last intercepter to log the network;
            if(Log.isDebuggable()){ //改成自己的显示log判断逻辑
                okBuild.addInterceptor(new LogInterceptor());
            }
            if(convertFactory!=null){
                rtBuilder.addConverterFactory(convertFactory);
            }else{
                rtBuilder.addConverterFactory(GsonConverterFactory.create());
            }
//            rtBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .client(okBuild.build());
            rtBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okBuild.build());
            return rtBuilder.build().create(NetworkApi.class);
        }
    }
}
