package com.qdwang.demo.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qdwang.demo.R;
import com.qdwang.demo.utils.uiutil.ScreenUtil;

/**
 * author: create by qdwang
 * date: 2018/8/16 20:08
 * described：
 */
public class HomeAdapter extends BaseAdapter {

    String url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=984980726,1592050432&fm=200&gp=0.jpg";
    private Context context;
    public HomeAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ScreenUtil.resetDensity(context.getApplicationContext());
        convertView =LayoutInflater.from(context).inflate(R.layout.item_listview_home, null);
        TextView tvMin = convertView.findViewById(R.id.tv_min);
        ImageView imageView = convertView.findViewById(R.id.image_view);
        Glide.with(context).asBitmap().load(url).into(imageView);
        tvMin.setText(position+1+"分钟前");
        return convertView;
    }
}
