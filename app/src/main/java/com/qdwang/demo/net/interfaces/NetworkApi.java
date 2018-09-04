package com.qdwang.demo.net.interfaces;

import com.qdwang.demo.net.ResponseInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe
 */
public interface NetworkApi {

    /**
     * acid是用于区分接口功能，RequestBody为请求的body参数。
     * @param acid
     * @param entery
     * @return
     */
    @POST("open/open.do")
    Observable<Object> post(@Query("ACID") int acid, @Body RequestBody entery);

    @POST("open/open.do")
    Observable<ResponseInfo<Object>> response(@Query("ACID") int acid, @Body RequestBody entery);

    @POST
    Observable<Object> webPost(@Body  String s);

    @POST
    Observable<Object> webPost(@Body  String s1, @Body String s2);
}
