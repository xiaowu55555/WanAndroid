package com.bingo.wanandroid.ui.Tree;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ArticleAdapter;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.CollectionEvent;
import com.bingo.wanandroid.entity.WxArticle;
import com.bingo.wanandroid.ui.detail.ArticleDetailActivity;
import com.bingo.wanandroid.viewmodel.ArticleViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListActivity;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TreeArticleListActivity extends BaseListActivity<Article.DatasBean, ArticleViewModel> {
    private int position = -1;
    private long cid;
    private String title;

    public static void start(Context context, String title, long cid) {
        Intent starter = new Intent(context, TreeArticleListActivity.class);
        starter.putExtra("title", title);
        starter.putExtra("cid", cid);
        context.startActivity(starter);
    }

    @Override
    protected void getIntentData() {
        cid = getIntent().getLongExtra("cid", 0);
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void setToolBar() {
        EventBus.getDefault().register(this);
        new TitleBar().bind(this).setTitle(Html.fromHtml(title).toString()).enableBack();
    }

    @Override
    protected void onItemClick(Article.DatasBean item, int position) {
        this.position = position;
        ArticleDetailActivity.start(context, item.getId(), item.getLink(), item.getTitle(), item.isCollect());
    }

    @Override
    protected void requestData() {
        viewModel.getTreeArticle(cid, pageIndex).observe(this, article -> {
            if (article != null) {
                pageSize = article.getSize();
                setListData(article.getDatas());
            }
        });
    }

    @Override
    protected BaseQuickAdapter<Article.DatasBean, BaseViewHolder> getAdapter() {
        return new ArticleAdapter(R.layout.item_article);
    }

    @Override
    protected ArticleViewModel createViewModel() {
        return ViewModelProviders.of(this).get(ArticleViewModel.class);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void setCollect(CollectionEvent event) {
        Article.DatasBean item = adapter.getItem(position);
        if (item != null) {
            item.setCollect(event.isCollect());
        }
    }
}
