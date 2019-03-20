package com.bingo.wanandroid.app;

import android.app.Application;

import com.bingo.wanandroid.api.ApiService;
import com.frame.library.Library;

public class App extends Application {

    private ApiService apiService;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apiService = Library.getInstance().init(this)
                .setBaseUrl(ApiService.BASE_URL)
                .setApiClass(ApiService.class);
    }

    public ApiService getApi() {
        return apiService;
    }

    public static App getInstance() {
        return instance;
    }
}
