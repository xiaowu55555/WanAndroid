package com.bingo.wanandroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ArticleAdapter;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.viewmodel.LastArticleViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;

public class LastArticleFragment extends BaseListFragment<Article.DatasBean, LastArticleViewModel> {

    public static LastArticleFragment newInstance() {
        return new LastArticleFragment();
    }

    @Override
    protected void onItemClick(Article.DatasBean item) {

    }

    @Override
    protected BaseQuickAdapter<Article.DatasBean, BaseViewHolder> getAdapter() {
        return new ArticleAdapter(R.layout.item_article);
    }

    @Override
    protected void requestData() {
        viewModel.getData(pageIndex).observe(this, new ListObserver());
    }

    @Override
    protected LastArticleViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LastArticleViewModel.class);
    }
}
