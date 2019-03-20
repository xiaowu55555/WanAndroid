package com.bingo.wanandroid.ui.blog;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.internal.BaselineLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.WxArticleAdapter;
import com.bingo.wanandroid.entity.WxArticle;
import com.bingo.wanandroid.viewmodel.TabWxArticleViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;

public class TabWxArticleFragment extends BaseListFragment<WxArticle, TabWxArticleViewModel> {

    public static TabWxArticleFragment newInstance() {
        return new TabWxArticleFragment();
    }

    @Override
    protected void onItemClick(WxArticle item) {

    }

    @Override
    protected boolean enableLoadMore() {
        return false;
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
    protected void initView(View rootView) {
        super.initView(rootView);
        TextView tv_title = rootView.findViewById(R.id.tv_title);
        tv_title.setText("公众号列表");
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
