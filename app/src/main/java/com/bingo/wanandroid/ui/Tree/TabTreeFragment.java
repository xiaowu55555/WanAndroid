package com.bingo.wanandroid.ui.Tree;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.TreeAdapter;
import com.bingo.wanandroid.entity.Tree;
import com.bingo.wanandroid.ui.search.SearchActivity;
import com.bingo.wanandroid.viewmodel.ArticleViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;
import com.frame.library.widget.TitleBar;

public class TabTreeFragment extends BaseListFragment<Tree, ArticleViewModel> {

    public static TabTreeFragment newInstance() {
        TabTreeFragment fragment = new TabTreeFragment();
        return fragment;
    }

    @Override
    protected void onItemClick(Tree item, int position) {

    }

    @Override
    protected void setToolBar(LinearLayout rootView) {
        new TitleBar().bind(context, rootView).setTitle("知识体系").setMenu(R.menu.menu_search, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_search) {
                    SearchActivity.start(context);
                }
                return false;
            }
        });
    }

    @Override
    protected BaseQuickAdapter<Tree, BaseViewHolder> getAdapter() {
        return new TreeAdapter(R.layout.item_tree_header);
    }

    @Override
    protected void requestData() {
        viewModel.getTreeList().observe(this, new ListObserver());
    }

    @Override
    protected ArticleViewModel createViewModel() {
        return ViewModelProviders.of(this).get(ArticleViewModel.class);
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }
}
