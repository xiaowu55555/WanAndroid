package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.SearchKey;
import com.bingo.wanandroid.viewmodel.base.SupportViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends SupportViewModel {

    private MutableLiveData<List<String>> historyList = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }


    //搜索热词
    public MutableLiveData<List<SearchKey>> getHotKey() {
        MutableLiveData<List<SearchKey>> data = new MutableLiveData<>();
        apiService.getHotKey().compose(RxTransformer.applySchedulers()).subscribe(new RxSubscriber<List<SearchKey>>(this) {
            @Override
            public void onSuccess(List<SearchKey> searchKeys) {
                data.setValue(searchKeys);
            }

            @Override
            public void onFailed(Throwable e) {
                showToast(e.getMessage());
            }
        });
        return data;
    }

    //获取所有记录
    public MutableLiveData<List<String>> getHistoryList() {
        List<String> keyList = Hawk.get("history_key");
        if (keyList == null) {
            keyList = new ArrayList<>();
        }
        historyList.setValue(keyList);
        return historyList;
    }

    //添加一条记录
    public void addHistory(String key) {
        List<String> keyList = Hawk.get("history_key");
        if (keyList == null) {
            keyList = new ArrayList<>();
        }
        keyList.remove(key);
        keyList.add(0, key);
        Hawk.put("history_key", keyList);
        historyList.setValue(keyList);
    }

    //删除一条记录
    public void deleteHistoryByIndex(int positon) {
        List<String> keyList = Hawk.get("history_key");
        if (keyList != null) {
            keyList.remove(positon);
            historyList.setValue(keyList);
            Hawk.put("history_key", keyList);
        }
    }

    //删除所有记录
    public void deleteHistory() {
        Hawk.delete("history_key");
        historyList.setValue(new ArrayList());
    }

    //搜索
    public MutableLiveData<Article> getSearchResult(int pageIndex, String key) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        apiService.getSearchResult(pageIndex, key)
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

    //在某个公众号中搜索历史文章
    public MutableLiveData<Article> searchInWxArticel(long id, int page, String key) {
        MutableLiveData<Article> data = new MutableLiveData<>();
        apiService.searchInWxArticel(id, page + 1, key)
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
