package com.qdwang.demo.net;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe
 */
public class ResponseInfo<T> {

    public int code;
    public String message;
    public T data;
    public String responsestamp;
}
