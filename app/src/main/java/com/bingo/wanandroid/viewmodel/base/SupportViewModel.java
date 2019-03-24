package com.bingo.wanandroid.viewmodel.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.api.ApiService;
import com.bingo.wanandroid.api.HttpClient;
import com.frame.library.base.BaseViewModel;

public class SupportViewModel extends BaseViewModel {
    protected ApiService apiService;

    public SupportViewModel(@NonNull Application application) {
        super(application);
        apiService = HttpClient.getInstance().getService();
    }
}
