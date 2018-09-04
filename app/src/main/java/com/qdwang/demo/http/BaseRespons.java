package com.qdwang.demo.http;

public class BaseRespons<T> {

    public int code;
    public String errorMsg;
    public String showMsg;
    public T data;
}
