package com.yingu.framework.net.observer;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.yingu.framework.net.RxHttp;
import com.yingu.framework.net.exceptions.ApiException;
import com.yingu.framework.net.observer.dialog.WaitingDialog;
import com.yingu.framework.net.utils.DebugConfigs;
import com.yingu.framework.net.utils.LogUtils;
import com.yingu.framework.net.utils.ToastUtil;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * author: create by qdwang
 * date: 2018/9/4 14:29
 * described：封装了是否显示加载动画和对onError（）的默认处理
 */
public abstract class ApiObserver<T> implements Observer<T>{

    private Context context;
    private boolean isShowWaitingDialog;
    private WaitingDialog waitingDialog;

    public abstract void onSuccess(T t);

//    public abstract void onFail();

    public void setCtx(Context context) {
        this.context = context;
    }

    public void setShowWaitDialogIsVisible(boolean isShowWaitingDialog) {
        this.isShowWaitingDialog = isShowWaitingDialog;
    }

    public void setShowWaitingDialog(WaitingDialog waitingDialog){
        this.waitingDialog = waitingDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(isShowWaitingDialog){
            showWaitDialog();
        }
    }

    @Override
    public void onNext(T t) {
        if(isShowWaitingDialog){
            dismissDialog();
        }
        //TODO 处理成功返回到业务层
        onSuccess(t);
//        onFail();
    }

    @Override
    public void onError(Throwable e) {
        if(isShowWaitingDialog){
            dismissDialog();
        }
        //TODO 处理错误情况，在这里可以进行统一处理
        Throwable throwable = e;
        if(DebugConfigs.isDebug){
            Log.i(RxHttp.TAG,throwable.getMessage().toString());
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
                ToastUtil.showToast(context, "网络连接异常");
            }else {
                String errorMsg = httpException.getMessage();
                if(TextUtils.isEmpty(errorMsg)){
                    ToastUtil.showToast(context, "网络连接异常");
                }else {
                    ToastUtil.showToast(context, errorMsg);
                }
            }
        }else if(e instanceof ApiException){//服务器返回的错误
            onResultError((ApiException) e);
        }else if(e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){//解析异常
            ToastUtil.showToast(context, "数据解析错误");
        }else if(e instanceof UnknownHostException){
            ToastUtil.showToast(context, "服务器连接异常");
        }else if(e instanceof SocketTimeoutException) {
            ToastUtil.showToast(context, "服务器连接超时");
        }else {
            e.printStackTrace();
            ToastUtil.showToast(context, "网络错误");
        }
    }

    @Override
    public void onComplete() {
        LogUtils.e("qdwang", "onComplete()");
        if(isShowWaitingDialog){
            dismissDialog();
        }
    }

    private synchronized void dismissDialog(){
        if(waitingDialog!=null) {
            if(waitingDialog.isShowing()) {
                waitingDialog.dismiss();
            }
        }
    }

    private synchronized void showWaitDialog(){
        if (waitingDialog == null) {
            waitingDialog = new WaitingDialog(context);
            waitingDialog.setCanceledOnTouchOutside(false);
        }
        waitingDialog.show();
    }

    /**
     * 服务器返回的错误
     * @param ex
     */
    protected  void onResultError(ApiException ex){
        switch (ex.getCode()){  //服务器返回code默认处理
            case 10021:
//                ToastUtil.showToast(context, R.string.imi_login_input_mail_error);
                break;
            case 10431:
//                ToastUtil.showToast(context, R.string.imi_const_tip_charge);
                break;
            default:
                String msg = ex.getMessage();
                if(TextUtils.isEmpty(msg)){
//                    ToastUtil.showToast(context, R.string.imi_toast_common_net_error);
                }else {
                    ToastUtil.showToast(context, msg);
                }
        }
    }
}
