package com.frxs.dispatch.rest.service.rxjava;


import com.frxs.core.utils.ToastUtils;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.rest.model.ApiResponse;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/07/19
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public abstract class BaseObserver<T> implements Observer<ApiResponse<T>> {
    private final String TAG = BaseObserver.class.getSimpleName();
    private int errorCode = -1;
    private String errorMsg = "未知的错误！";
    private Disposable disposable;

    /**
     * on response return
     * @param result result
     */
    public abstract void onResponse(final ApiResponse<T>  result);

    public void onFailure(Throwable t) {
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorCode = -1;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = -1;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof RuntimeException) {
            errorCode = -1;
            errorMsg = "运行时错误";
        } else if (t instanceof UnknownHostException) {
            errorCode = -1;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = -1;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorCode = -1;
            errorMsg = "没有网络，请检查网络连接";
        }

        ToastUtils.show(MyApplication.getInstance(), errorMsg);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(@NonNull ApiResponse<T> tApiResponse) {
        onResponse(tApiResponse);
    }

    @Override
    public void onError(@NonNull Throwable t) {
        onFailure(t);
    }

    @Override
    public void onComplete() {

    }

}
