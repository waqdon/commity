package com.qdwang.demo.net.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe 等待加载框
 */
public class WaitingDialog extends Dialog{
    public WaitingDialog(@NonNull Context context) {
        super(context);
    }

    public WaitingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setDialogWindowStyle() {

    }
}
