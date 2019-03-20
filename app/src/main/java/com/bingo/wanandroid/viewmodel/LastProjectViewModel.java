package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.Project;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import java.util.List;

public class LastProjectViewModel extends BaseViewModel {
    public LastProjectViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Project.DatasBean>> getData(int page) {
        MutableLiveData<List<Project.DatasBean>> data = new MutableLiveData<>();
        App.getInstance().getApi().getLastProject(page)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<Project>(this) {
                    @Override
                    public void onSuccess(Project project) {
                        data.setValue(project.getDatas());
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        return data;
    }
}
