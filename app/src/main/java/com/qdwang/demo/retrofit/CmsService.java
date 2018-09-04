package com.qdwang.demo.retrofit;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;

public interface CmsService<T> {

    @GET("banner/json")
    Observable<ResponseBody> getBanner();

    @GET("banner/json")
    Observable<Response<T>> getArict();

}
