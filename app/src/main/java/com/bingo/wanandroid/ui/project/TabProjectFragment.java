package com.bingo.wanandroid.ui.project;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.ProjectTree;
import com.bingo.wanandroid.viewmodel.ProjectViewModel;
import com.frame.library.base.BaseFragment;
import com.frame.library.widget.MultipleStatusView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabProjectFragment extends BaseFragment<ProjectViewModel> {

    private List<ProjectTree> treeList = new ArrayList<>();
    private TagAdapter<ProjectTree> adapter;

    public static TabProjectFragment newInstance() {
        return new TabProjectFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_project;
    }

    @Override
    protected void initView(View rootView) {
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        tvTitle.setText("项目分类");
        TagFlowLayout flowLayout = rootView.findViewById(R.id.flow_layout);
        adapter = new TagAdapter<ProjectTree>(treeList) {
            @Override
            public View getView(FlowLayout parent, int position, ProjectTree projectTree) {
                View tagView = View.inflate(context, R.layout.item_peoject_tree, null);
                TextView tvTag = tagView.findViewById(R.id.tv_tag);
                tvTag.setText(Html.fromHtml(projectTree.getName()));
                String[] colors = context.getResources().getStringArray(R.array.random_color);
                tvTag.setBackgroundColor(Color.parseColor(colors[new Random().nextInt(colors.length)]));
                return tagView;
            }
        };
        flowLayout.setAdapter(adapter);
        flowLayout.setOnTagClickListener((view, position, parent) -> {
            ProjectTree tree = treeList.get(position);
            if (tree != null) {
                ProjectListActivity.start(context, tree);
            }
            return false;
        });
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }

    @Override
    protected void onRetry() {
        onLazyLoad();
    }

    @Override
    public void onRefresh() {
        onLazyLoad();
    }

    @Override
    protected ProjectViewModel createViewModel() {
        return ViewModelProviders.of(this).get(ProjectViewModel.class);
    }

    @Override
    protected void onLazyLoad() {
        viewModel.getProjectTree().observe(this, projectTrees -> {
            showContent();
            if (projectTrees != null) {
                treeList.clear();
                treeList.addAll(projectTrees);
                adapter.notifyDataChanged();
            }
        });
    }
}
