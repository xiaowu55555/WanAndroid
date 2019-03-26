package com.bingo.wanandroid.app;

import android.app.Application;
import android.widget.Button;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.bingo.wanandroid.entity.User;
import com.frame.library.Library;
import com.orhanobut.hawk.Hawk;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Library.getInstance().init(this);
        Hawk.init(instance).build();
        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
    }

    public static App getInstance() {
        return instance;
    }


    public User getUser() {
        return (User) Hawk.get("login_user");
    }

    public boolean isLogin() {
        return Hawk.get("login_user") != null;
    }

    public void setUser(User user) {
        if (user != null) {
            Hawk.put("login_user", user);
        }
    }

    public void logout(){
        Hawk.delete("login_user");
    }
}
