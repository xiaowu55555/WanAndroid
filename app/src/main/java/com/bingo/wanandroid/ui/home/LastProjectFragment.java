package com.bingo.wanandroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ProjectAdapter;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.ui.DetailActivity;
import com.bingo.wanandroid.viewmodel.ProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;

public class LastProjectFragment extends BaseListFragment<Project.DatasBean, ProjectViewModel> {

    public static LastProjectFragment newInstance() {
        return new LastProjectFragment();
    }

    @Override
    protected void onItemClick(Project.DatasBean item) {
        DetailActivity.start(context,item.getLink(),item.getTitle());
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
}
