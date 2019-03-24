package com.bingo.wanandroid.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bingo.wanandroid.api.ApiService;
import com.bingo.wanandroid.api.HttpClient;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.ProjectTree;
import com.bingo.wanandroid.viewmodel.base.SupportViewModel;
import com.frame.library.base.BaseViewModel;
import com.frame.library.net.RxSubscriber;
import com.frame.library.net.RxTransformer;

import java.util.List;

public class ProjectViewModel extends SupportViewModel {

    public ProjectViewModel(@NonNull Application application) {
        super(application);
    }

    //最新文章
    public MutableLiveData<Project> getLastProject(int page) {
        MutableLiveData<Project> data = new MutableLiveData<>();
        apiService.getLastProject(page)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<Project>(this) {
                    @Override
                    public void onSuccess(Project project) {
                        data.setValue(project);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        return data;
    }

    //项目分类
    public MutableLiveData<List<ProjectTree>> getProjectTree() {
        MutableLiveData<List<ProjectTree>> data = new MutableLiveData<>();
        apiService.getProjectTree()
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<List<ProjectTree>>(this) {
                    @Override
                    public void onSuccess(List<ProjectTree> projectTrees) {
                        data.setValue(projectTrees);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });

        return data;
    }

    //项目列表数据 返回size为15 要单独处理TT
    public MutableLiveData<Project> getProject(int page, long cid) {
        MutableLiveData<Project> data = new MutableLiveData<>();
        apiService.getProjectList(page + 1, cid)
                .compose(RxTransformer.applySchedulers())
                .subscribe(new RxSubscriber<Project>(this) {
                    @Override
                    public void onSuccess(Project project) {
                        data.setValue(project);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        return data;
    }
}
