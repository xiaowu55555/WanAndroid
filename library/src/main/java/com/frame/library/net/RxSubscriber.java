package com.frame.library.net;


import com.frame.library.base.BaseViewModel;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class RxSubscriber<T> implements Observer<T> {
    private BaseViewModel viewModel;
    private String message = "加载中...";
    private boolean showLoading;


    public RxSubscriber(BaseViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public RxSubscriber(BaseViewModel viewModel, boolean showLoading) {
        this.viewModel = viewModel;
        this.showLoading = showLoading;
    }

    public RxSubscriber(BaseViewModel viewModel, boolean showLoading, String message) {
        this.viewModel = viewModel;
        this.message = message;
        this.showLoading = showLoading;
    }

    @Override
    public void onSubscribe(Disposable d) {
        viewModel.addDisposable(d);
        if (showLoading) {
            viewModel.showLoading(message);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (showLoading) {
            viewModel.hideLoading();
        }
        ApiException ex;
        if (e instanceof ApiException) {
            ex = (ApiException) e;
            if (ex.isNeedLogin()) {
                viewModel.needLogin();
            }
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException("没有网络");
        } else if (e instanceof HttpException) {
            ex = new ApiException("网络错误");
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException("网络连接超时");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            ex = new ApiException("解析错误");
        } else if (e instanceof ConnectException) {
            ex = new ApiException("连接失败");
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException("证书验证失败");
        } else {
            ex = new ApiException(e.getMessage());
        }
        onFailed(ex);
    }

    @Override
    public void onNext(T t) {
        if (showLoading) {
            viewModel.hideLoading();
        }
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(Throwable e);
}
