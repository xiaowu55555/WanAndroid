package com.frame.library.net;

public interface IApiResult<T> {

    int getErrorCode();

    String getErrorMsg();

    T getData();

    boolean isSuccess();
}
