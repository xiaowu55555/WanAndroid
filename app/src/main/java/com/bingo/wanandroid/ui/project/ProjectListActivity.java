package com.bingo.wanandroid.ui.project;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ProjectAdapter;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.ProjectTree;
import com.bingo.wanandroid.ui.detail.ArticleDetailActivity;
import com.bingo.wanandroid.viewmodel.ProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListActivity;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;


public class ProjectListActivity extends BaseListActivity<Project.DatasBean, ProjectViewModel> {

    private ProjectTree tree;

    public static void start(Context context, ProjectTree tree) {
        Intent starter = new Intent(context, ProjectListActivity.class);
        starter.putExtra("tree", tree);
        context.startActivity(starter);
    }

    @Override
    protected void getIntentData() {
        tree = getIntent().getParcelableExtra("tree");
        if (tree == null) {
            finish();
            ToastUtil.showToast("参数错误");
        }
    }

    @Override
    protected void setToolBar() {
        new TitleBar().bind(this).setTitle(Html.fromHtml(tree.getName()).toString()).enableBack();
    }

    @Override
    protected void onItemClick(Project.DatasBean item, int position) {
        ArticleDetailActivity.start(context,item.getId(),item.getLink(),item.getTitle(),item.isCollect());
    }


    @Override
    protected void requestData() {
        viewModel.getProject(pageIndex, tree.getId()).observe(this, project -> {
            if (project != null) {
                pageSize = project.getSize();
                setListData(project.getDatas());
            }
        });
    }

    @Override
    protected BaseQuickAdapter<Project.DatasBean, BaseViewHolder> getAdapter() {
        return new ProjectAdapter(R.layout.item_project);
    }

    @Override
    protected ProjectViewModel createViewModel() {
        return ViewModelProviders.of(this).get(ProjectViewModel.class);
    }
}
