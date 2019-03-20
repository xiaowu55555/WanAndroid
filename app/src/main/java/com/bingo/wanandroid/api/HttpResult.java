package com.bingo.wanandroid.api;

import com.frame.library.net.IApiResult;

public class HttpResult<T> implements IApiResult<T> {

    private T data;
    private int errorCode;
    private String errorMsg;

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return errorCode == 0;
    }
}
