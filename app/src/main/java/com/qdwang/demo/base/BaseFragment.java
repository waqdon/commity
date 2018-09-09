package com.qdwang.demo.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * author: create by qdwang
 * date: 2018/8/16 16:31
 * described：
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        ScreenUtil.resetDensity(context.getApplicationContext());
    }
}
