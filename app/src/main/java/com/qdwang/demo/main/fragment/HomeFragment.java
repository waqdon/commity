package com.qdwang.demo.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.qdwang.demo.R;
import com.qdwang.demo.base.BaseFragment;
import com.qdwang.demo.main.adapter.HomeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: create by qdwang
 * date: 2018/8/16 16:31
 * described：
 */
public class HomeFragment extends BaseFragment {

    ImageView imageView;
    @BindView(R.id.list_view)ListView listView;

    private HomeAdapter homeAdapter;
    private Unbinder unbinder;
    String imageUrl = "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4bfc640817d5ad6eb5f962eab1c939a3/8718367adab44aedb794e128bf1c8701a08bfb20.jpg";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment=new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder =ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_listview_header, null);
        imageView = headerView.findViewById(R.id.imageView);
        listView.addHeaderView(headerView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity()).asBitmap().load(imageUrl).into(imageView);
            }
        },  1500);
        homeAdapter = new HomeAdapter(getActivity());
        listView.setAdapter(homeAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
