package com.qdwang.demo.http;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST
    Observable<BaseRespons<BaseModel>> getBase(@Body RequestBody requestBody);
}
