package com.qdwang.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.qdwang.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;

    public static void toActivity(Context context){
        Intent intent =new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void createView() {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

}
