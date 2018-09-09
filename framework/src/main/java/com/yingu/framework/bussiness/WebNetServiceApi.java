package com.yingu.framework.bussiness;

import com.yingu.framework.net.base.RequestBodyParams;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * author: create by qdwang
 * date: 2018/9/5 18:32
 * described：
 */
public interface WebNetServiceApi {

    @POST("gle/api/manager/appStart")
    Observable<ResponseBody> h5Request(@Body RequestBodyParams params);}
