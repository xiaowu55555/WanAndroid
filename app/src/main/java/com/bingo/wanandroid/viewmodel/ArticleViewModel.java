package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.api.ApiService;
import com.bingo.wanandroid.api.HttpClient;
import com.bingo.wanandroid.api.HttpResult;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.Tree;
import com.bingo.wanandroid.entity.TreeChild;
import com.bingo.wanandroid.viewmodel.base.SupportViewModel;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class ArticleViewModel extends SupportViewModel {

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    //首页最新文章
    public MutableLiveData<Article> getLastArticle(int page) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        if (page == 0) {
            //需要合并置顶文章
            Observable.zip(apiService.getLastArticle(page),
                    apiService.getTopArticle(),
                    (articleHttpResult, topArticleHttpResult) -> {
                        articleHttpResult.getData().getDatas().addAll(0, topArticleHttpResult.getData());
                        return articleHttpResult;
                    }).compose(RxTransformer.applySchedulers())
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
        } else {
            apiService.getLastArticle(page)
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
        }
        return data;
    }

    //查看某个公众号历史数据
    public MutableLiveData<Article> getArticleList(long id, int page) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        apiService.getWxarticleList(id, page + 1)
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

    //知识体系
    public MutableLiveData<List<Tree>> getTreeList() {
        MutableLiveData<List<Tree>> data = new MutableLiveData<>();
        apiService.getTree().compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<List<Tree>>(this) {
                    @Override
                    public void onSuccess(List<Tree> trees) {
                        data.setValue(trees);
                    }

                    @Override
                    public void onFailed(Throwable e) {

                    }
                });
        return data;
    }

    private List<Tree> parseData(List<Tree> trees) {
        List<Tree> list = new ArrayList<>();
        if (trees != null && trees.size() > 0) {
            for (int i = 0; i < trees.size(); i++) {
                Tree tree = trees.get(i);
                list.add(new Tree(true, tree.getName()));
                List<TreeChild> treeChild = tree.getChildren();
                if (treeChild != null && treeChild.size() > 0) {
                    for (int j = 0; j < treeChild.size(); j++) {
                        list.add(new Tree(treeChild.get(j)));
                    }
                }
            }
            return list;
        }
        return null;
    }
}
