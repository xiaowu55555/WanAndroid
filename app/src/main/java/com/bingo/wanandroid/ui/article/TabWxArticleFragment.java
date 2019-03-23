package com.bingo.wanandroid.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.WxArticleAdapter;
import com.bingo.wanandroid.entity.WxArticle;
import com.bingo.wanandroid.viewmodel.TabWxArticleViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;
import com.frame.library.widget.TitleBar;

public class TabWxArticleFragment extends BaseListFragment<WxArticle, TabWxArticleViewModel> {

    public static TabWxArticleFragment newInstance() {
        return new TabWxArticleFragment();
    }

    @Override
    protected void onItemClick(WxArticle item) {
        ArticleListActivity.start(context, item);
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected void setToolBar(LinearLayout rootView) {
        new TitleBar().bind(context, rootView).setTitle("公众号列表");

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(context, 2);
    }

    @Override
    protected BaseQuickAdapter<WxArticle, BaseViewHolder> getAdapter() {
        return new WxArticleAdapter(R.layout.item_wxarticle_chapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_blog;
    }

    @Override
    protected void requestData() {
        viewModel.getData().observe(this, new ListObserver());
    }

    @Override
    protected TabWxArticleViewModel createViewModel() {
        return ViewModelProviders.of(this).get(TabWxArticleViewModel.class);
    }
}