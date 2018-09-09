package com.yingu.framework.net.base.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.yingu.framework.net.parser.GsonParser;
import java.lang.annotation.Annotation;

import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author: create by qdwang
 * date: 2018/9/6 09:48
 * described：
 */
public class WebConverterFactory extends Converter.Factory {

    public static WebConverterFactory create() {
        return create(GsonParser.getInstance().getGson());
    }

    public static WebConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new WebConverterFactory(gson);
    }

    private final Gson gson;

    private WebConverterFactory(Gson gson) {
        this.gson=gson;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter=gson.getAdapter(TypeToken.get(type));
        return new DesGsonRequestBodyConverter<>(gson, adapter);
    }
}
