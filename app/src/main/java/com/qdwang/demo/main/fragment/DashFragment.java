package com.qdwang.demo.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qdwang.demo.R;
import com.qdwang.demo.base.BaseFragment;

/**
 * author: create by qdwang
 * date: 2018/8/16 16:33
 * described：
 */
public class DashFragment extends BaseFragment {

    public static DashFragment newInstance() {
        DashFragment fragment=new DashFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash, container, false);
    }
}
