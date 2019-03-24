package com.bingo.wanandroid.app;

import android.app.Application;

import com.bingo.wanandroid.api.ApiService;
import com.frame.library.Library;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Library.getInstance().init(this);
    }

    public static App getInstance() {
        return instance;
    }

    public boolean isLogin() {
        return false;
    }
}
