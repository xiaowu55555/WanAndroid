package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.api.HttpResult;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.viewmodel.base.SupportViewModel;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;
import com.frame.library.utils.ToastUtil;

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

    public MutableLiveData<Article> getCollection(int pageIndex) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        apiService.getCollection(pageIndex)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<Article>(this) {
                    @Override
                    public void onSuccess(Article article) {
                        data.setValue(article);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> cancelCollection(long id, long originId) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        apiService.cancelCollection(id, originId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<HttpResult>(this) {
                    @Override
                    public void onSuccess(HttpResult result) {
                        data.setValue(true);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showToast(e.getMessage());
                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> collect(long id) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        apiService.collect(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<HttpResult>(this) {
                    @Override
                    public void onSuccess(HttpResult result) {
                        data.setValue(true);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showToast(e.getMessage());
                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> articleCancelCollection(long id) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        apiService.articleCancel(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<HttpResult>(this) {
                    @Override
                    public void onSuccess(HttpResult result) {
                        data.setValue(true);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showToast(e.getMessage());
                    }
                });
        return data;
    }


}
