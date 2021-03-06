package com.bingo.wanandroid.ui.project;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ProjectAdapter;
import com.bingo.wanandroid.entity.CollectionEvent;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.ProjectTree;
import com.bingo.wanandroid.ui.detail.ArticleDetailActivity;
import com.bingo.wanandroid.viewmodel.ProjectViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListActivity;
import com.frame.library.utils.SPUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class ProjectListActivity extends BaseListActivity<Project.DatasBean, ProjectViewModel> {
    private int position = -1;
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
        EventBus.getDefault().register(this);
        new TitleBar().bind(this).setTitle(Html.fromHtml(tree.getName()).toString()).enableBack();
    }

    @Override
    protected void onItemClick(Project.DatasBean item, int position) {
        this.position = position;
        ArticleDetailActivity.start(context,item.getId(),item.getLink(),item.getTitle(),item.isCollect());
        if (!SPUtils.getInstance().getBoolean(String.valueOf(item.getId()), false)) {
            SPUtils.getInstance().put(String.valueOf(item.getId()), true);
            adapter.notifyItemChanged(position);
        }
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void setCollect(CollectionEvent event) {
        Project.DatasBean item = adapter.getItem(position);
        if (item != null) {
            item.setCollect(event.isCollect());
        }
    }
}
