package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.api.HttpResult;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.viewmodel.base.SupportViewModel;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends SupportViewModel {
    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<User> register(String userName, String pwd, String rePwd) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.register(userName, pwd, rePwd)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<User>(this, true, "正在注册...") {
                    @Override
                    public void onSuccess(User user) {
                        App.getInstance().setUser(user);
                        data.setValue(user);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showToast(e.getMessage());
                    }
                });
        return data;
    }

    public MutableLiveData<User> login(String userName, String pwd) {
        MutableLiveData<User> data = new MutableLiveData<>();
        apiService.login(userName, pwd)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<User>(this, true, "正在登录") {
                    @Override
                    public void onSuccess(User user) {
                        App.getInstance().setUser(user);
                        data.setValue(user);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showToast(e.getMessage());
                    }
                });
        return data;
    }

    public void logout() {
        apiService.logout()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<HttpResult>(this) {
                    @Override
                    public void onSuccess(HttpResult result) {
                        App.getInstance().logout();
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        App.getInstance().logout();
                    }
                });
    }
}
