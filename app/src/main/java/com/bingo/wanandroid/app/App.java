package com.bingo.wanandroid.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.bingo.wanandroid.constant.Constant;
import com.bingo.wanandroid.entity.User;
import com.frame.library.Library;
import com.frame.library.utils.SPUtils;
import com.orhanobut.hawk.Hawk;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Library.getInstance().init(this);
        Hawk.init(instance).build();
        if (SPUtils.getInstance().getBoolean(Constant.ISNIGHT)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static App getInstance() {
        return instance;
    }


    public User getUser() {
        return (User) Hawk.get(Constant.USER);
    }

    public boolean isLogin() {
        return Hawk.get(Constant.USER) != null;
    }

    public void setUser(User user) {
        if (user != null) {
            Hawk.put(Constant.USER, user);
        }
    }

    public void logout() {
        Hawk.delete(Constant.USER);
    }
}
