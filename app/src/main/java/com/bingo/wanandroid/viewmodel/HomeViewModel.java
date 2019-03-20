package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.HomeBanner;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import java.util.List;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<HomeBanner>> getData() {
        MutableLiveData<List<HomeBanner>> data = new MutableLiveData<>();
        App.getInstance().getApi().getBanner()
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<List<HomeBanner>>(this) {
                    @Override
                    public void onSuccess(List<HomeBanner> list) {
                        data.setValue(list);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showToast(e.getMessage());
                    }
                });
        return data;
    }
}
