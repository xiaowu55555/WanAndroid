package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Article;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import java.util.List;

public class LastArticleViewModel extends BaseViewModel {
    public LastArticleViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Article.DatasBean>> getData(int page) {
        MutableLiveData<List<Article.DatasBean>> data = new MutableLiveData<>();
        App.getInstance().getApi().getLastArticle(page)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<Article>(this) {
                    @Override
                    public void onSuccess(Article article) {
                        data.setValue(article.getDatas());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        return data;
    }
}
