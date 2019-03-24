package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.WxArticle;
import com.bingo.wanandroid.viewmodel.base.SupportViewModel;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import java.util.List;

public class TabWxArticleViewModel extends SupportViewModel {

    public TabWxArticleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<WxArticle>> getData() {
        MutableLiveData<List<WxArticle>> data = new MutableLiveData<>();
        apiService.getWxarticleChapters()
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<List<WxArticle>>(this) {
                    @Override
                    public void onSuccess(List<WxArticle> list) {
                        data.setValue(list);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        return data;
    }
}
