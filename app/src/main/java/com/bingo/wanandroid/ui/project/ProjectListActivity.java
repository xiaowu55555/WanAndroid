package com.bingo.wanandroid.ui.project;

import com.bingo.wanandroid.adapter.ProjectAdapter;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.viewmodel.ProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListActivity;

public class ProjectListActivity extends BaseListActivity<Project.DatasBean, ProjectViewModel> {
    @Override
    protected void onItemClick(Project.DatasBean item) {
        
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected BaseQuickAdapter<Project.DatasBean, BaseViewHolder> getAdapter() {
        return null;
    }

    @Override
    protected ProjectViewModel createViewModel() {
        return null;
    }
}
