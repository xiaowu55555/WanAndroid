package com.bingo.wanandroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ProjectAdapter;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.viewmodel.LastProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;

public class LastProjectFragment extends BaseListFragment<Project.DatasBean, LastProjectViewModel> {

    public static LastProjectFragment newInstance() {
        return new LastProjectFragment();
    }

    @Override
    protected void onItemClick(Project.DatasBean item) {

    }

    @Override
    protected BaseQuickAdapter<Project.DatasBean, BaseViewHolder> getAdapter() {
        return new ProjectAdapter(R.layout.item_project);
    }

    @Override
    protected void requestData() {
        viewModel.getData(pageIndex).observe(this, new ListObserver());
    }

    @Override
    protected LastProjectViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LastProjectViewModel.class);
    }
}
