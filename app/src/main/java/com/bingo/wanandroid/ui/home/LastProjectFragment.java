package com.bingo.wanandroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ProjectAdapter;
import com.bingo.wanandroid.entity.CollectionEvent;
import com.bingo.wanandroid.entity.HomeUpdateEvent;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.ui.detail.ArticleDetailActivity;
import com.bingo.wanandroid.viewmodel.ProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LastProjectFragment extends BaseListFragment<Project.DatasBean, ProjectViewModel> {

    private int position = -1;

    public static LastProjectFragment newInstance() {
        return new LastProjectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onItemClick(Project.DatasBean item, int position) {
        this.position = position;
        ArticleDetailActivity.start(context, item.getId(), item.getLink(), item.getTitle(), item.isCollect());
    }

    @Override
    protected BaseQuickAdapter<Project.DatasBean, BaseViewHolder> getAdapter() {
        return new ProjectAdapter(R.layout.item_project);
    }

    @Override
    protected void requestData() {
        viewModel.getLastProject(pageIndex).observe(this, project -> {
            if (project != null) {
                pageSize = project.getSize();
                setListData(project.getDatas());
            }
        });
    }

    @Override
    protected ProjectViewModel createViewModel() {
        return ViewModelProviders.of(this).get(ProjectViewModel.class);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void notifyLogin(User user) {
        onRefresh();
    }

    @Subscribe
    public void setCollect(CollectionEvent event) {
        Project.DatasBean item = adapter.getItem(position);
        if (item != null) {
            item.setCollect(event.isCollect());
        }
    }

    @Subscribe
    public void refresh(HomeUpdateEvent event){
        onRefresh();
    }
}
