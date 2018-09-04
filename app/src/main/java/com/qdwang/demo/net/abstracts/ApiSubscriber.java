package com.qdwang.demo.net.abstracts;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.qdwang.demo.R;
import com.qdwang.demo.net.RtHttp;
import com.qdwang.demo.net.exception.ApiException;
import com.qdwang.demo.net.utils.Log;
import com.qdwang.demo.net.utils.ToastUtil;
import com.qdwang.demo.net.widget.WaitingDialog;

import org.json.JSONException;
import org.reactivestreams.Subscriber;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import retrofit2.HttpException;


/**
 * @author qdwang$
 * @date 2018/8/31$
 * @describe 封装了是否显示加载动画和对onError（）的默认处理
 */
public abstract class ApiSubscriber<T> implements Observer<T> {

    private  Context mCtx;
    private WaitingDialog waitingDialog;  //加载dialog
    private boolean isShowWaitDialog;

    public void setShowWaitDialog(boolean showWaitDialog) {
        isShowWaitDialog = showWaitDialog;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(isShowWaitDialog){
//            showWaitDialog();
//        }
//    }


    public void setmCtx(Context mCtx) {
        this.mCtx = mCtx;
    }

    @Override
    public void onComplete() {
        if(isShowWaitDialog){
            dismissDialog();
        }
    }

    /**
     * 对 onError进行处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if(isShowWaitDialog){
            dismissDialog();
        }
        Throwable throwable = e;
        if(Log.isDebuggable()){
            Log.i(RtHttp.TAG,throwable.getMessage().toString());
        }
        /**
         * 获取根源 异常
         */
        while (throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }
        if(e instanceof HttpException){//对网络异常 弹出相应的toast
            HttpException httpException = (HttpException) e;
            if(TextUtils.isEmpty(httpException.getMessage())){
                ToastUtil.showToast(mCtx, R.string.imi_toast_common_net_error);
            }else {
                String errorMsg = httpException.getMessage();
                if(TextUtils.isEmpty(errorMsg)){
                    ToastUtil.showToast(mCtx, R.string.imi_toast_common_net_error);
                }else {
                    ToastUtil.showToast(mCtx, errorMsg);
                }

            }
        }else if(e instanceof ApiException){//服务器返回的错误
            onResultError((ApiException) e);
        }else if(e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){//解析异常
            ToastUtil.showToast(mCtx, R.string.imi_toast_common_parse_error);
        }else if(e instanceof UnknownHostException){
            ToastUtil.showToast(mCtx, R.string.imi_toast_common_server_error);
        }else if(e instanceof SocketTimeoutException) {
            ToastUtil.showToast(mCtx, R.string.imi_toast_common_net_timeout);
        }else {
            e.printStackTrace();
            ToastUtil.showToast(mCtx, R.string.imi_toast_common_net_error);
        }
    }
    /**
     * 服务器返回的错误
     * @param ex
     */
    protected  void onResultError(ApiException ex){
        switch (ex.getCode()){  //服务器返回code默认处理
            case 10021:
                ToastUtil.showToast(mCtx, R.string.imi_login_input_mail_error);
                break;
            case 10431:
                ToastUtil.showToast(mCtx, R.string.imi_const_tip_charge);
                break;
            default:
                String msg = ex.getMessage();
                if(TextUtils.isEmpty(msg)){
                    ToastUtil.showToast(mCtx, R.string.imi_toast_common_net_error);
                }else {
                    ToastUtil.showToast(mCtx, msg);
                }
        }

    }

    private void dismissDialog(){
        if(waitingDialog!=null) {
            if(waitingDialog.isShowing()) {
                waitingDialog.dismiss();
            }
        }
    }

    private void showWaitDialog(){
        if (waitingDialog == null) {
            waitingDialog = new WaitingDialog(mCtx);
            waitingDialog.setDialogWindowStyle();
            waitingDialog.setCanceledOnTouchOutside(false);
        }
        waitingDialog.show();
    }
}
