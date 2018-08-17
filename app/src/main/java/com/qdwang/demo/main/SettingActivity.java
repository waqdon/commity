package com.qdwang.demo.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.qdwang.demo.R;
import com.qdwang.demo.base.BaseActivity;
import com.qdwang.demo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: create by qdwang
 * date: 2018/8/16 17:39
 * described：
 */
public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback{

    @BindView(R.id.toolbar)Toolbar toolbar;

    public static void toActivity(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void createView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn)
    public void changeTheme(){
        new ColorChooserDialog.Builder(SettingActivity.this, R.string.choose_theme_color)
                .backButton(R.string.back)
                .cancelButton(R.string.cancel)
                .doneButton(R.string.done)
                .customButton(R.string.custom)
                .presetsButton(R.string.back)
                .allowUserColorInputAlpha(false)
                .show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        ToastUtils.showToast(this, "onColorSelection");
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {
        ToastUtils.showToast(this, "onColorChooserDismissed");
    }
}
