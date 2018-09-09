package com.yingu.framework.bussiness;
import com.yingu.framework.net.base.RequestBodyParams;
import com.yingu.framework.net.base.ResponseInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * author: create by qdwang
 * date: 2018/9/4 14:05
 * described：
 */
public interface NetworkServiceApi {

    @POST("gle/api/manager/appStart")
    Observable<ResponseInfo<Void>> appStart(@Body RequestBodyParams params);

    @POST("cms/api/getDataOfIndex")
    Observable<ResponseInfo<Void>> getDataOfIndex(@Body RequestBodyParams params);
}
