package com.yingu.framework.net.base.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.yingu.framework.net.base.RequestBodyParams;
import com.yingu.framework.net.des.EncipherProxy;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * author: create by qdwang
 * date: 2018/9/4 17:25
 * described：
 */
public class DesGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    DesGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public RequestBody convert(T value) {
        //TODO 处理入参加密
        RequestBodyParams params = (RequestBodyParams) value;
        try {
            params.jsonParams = EncipherProxy.encrypt(params.jsonParams);
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, (T) params);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
