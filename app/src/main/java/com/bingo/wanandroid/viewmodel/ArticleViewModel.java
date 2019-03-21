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

public class ArticleViewModel extends BaseViewModel {
    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    //首页最新文章
    public MutableLiveData<Article> getLastArticle(int page) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        App.getInstance().getApi().getLastArticle(page)
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

    //查看某个公众号历史数据
    public MutableLiveData<Article> getArticleList(long id, int page) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        App.getInstance().getApi().getWxarticleList(id, page+1)
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
}
