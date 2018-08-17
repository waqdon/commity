package com.qdwang.demo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * author: create by qdwang
 * date: 2018/8/16 15:56
 * described：土司管理工具类
 */
public class ToastUtils {

    /**
     * 弹出默认土司
     * @param context 上下文
     * @param msg 弹出内容
     */
    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
