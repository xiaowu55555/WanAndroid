package com.bingo.wanandroid.ui.home;

import android.arch.lifecycle.ViewModelProviders;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ArticleAdapter;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.ui.ArticleDetailActivity;
import com.bingo.wanandroid.viewmodel.ArticleViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListFragment;

public class LastArticleFragment extends BaseListFragment<Article.DatasBean, ArticleViewModel> {

    public static LastArticleFragment newInstance() {
        return new LastArticleFragment();
    }

    @Override
    protected void onItemClick(Article.DatasBean item) {
        ArticleDetailActivity.start(context,item.getId(),item.getLink(),item.getTitle(),item.isCollect());
    }

    @Override
    protected BaseQuickAdapter<Article.DatasBean, BaseViewHolder> getAdapter() {
        return new ArticleAdapter(R.layout.item_article);
    }

    @Override
    protected void requestData() {
        viewModel.getLastArticle(pageIndex).observe(this, article -> {
            if (article != null) {
                pageSize = article.getSize();
                setListData(article.getDatas());
            }
        });
    }

    @Override
    protected ArticleViewModel createViewModel() {
        return ViewModelProviders.of(this).get(ArticleViewModel.class);
    }
}
