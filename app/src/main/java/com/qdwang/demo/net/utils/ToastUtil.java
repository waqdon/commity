package com.qdwang.demo.net.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe
 */
public class ToastUtil {

    public static void showToast(Context context, int strRes){
        Toast.makeText(context, context.getString(strRes), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
