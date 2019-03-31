package com.bingo.wanandroid.ui.article;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.ArticleAdapter;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.CollectionEvent;
import com.bingo.wanandroid.ui.detail.ArticleDetailActivity;
import com.bingo.wanandroid.viewmodel.SearchViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListActivity;
import com.frame.library.utils.SPUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ArticleSearchActivity extends BaseListActivity<Article.DatasBean, SearchViewModel> {

    private int position = -1;
    private String keyword;
    private long id;

    public static void start(Context context, String keyword,long id) {
        Intent starter = new Intent(context, ArticleSearchActivity.class);
        starter.putExtra("keyword", keyword);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }

    @Override
    protected void getIntentData() {
        keyword = getIntent().getStringExtra("keyword");
        id = getIntent().getLongExtra("id",0);
    }

    @Override
    protected void setToolBar() {
        EventBus.getDefault().register(this);
        new TitleBar().bind(this).setTitle(keyword).enableBack();
    }

    @Override
    protected void onItemClick(Article.DatasBean item, int position) {
        this.position = position;
        ArticleDetailActivity.start(context, item.getId(), item.getLink(), item.getTitle(), item.isCollect());
        if (!SPUtils.getInstance().getBoolean(String.valueOf(item.getId()), false)) {
            SPUtils.getInstance().put(String.valueOf(item.getId()), true);
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    protected void requestData() {
        viewModel.searchInWxArticel(id,pageIndex,keyword).observe(this, new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                if (article != null) {
                    pageSize = article.getSize();
                    setListData(article.getDatas());
                }
            }
        });
    }

    @Override
    protected BaseQuickAdapter<Article.DatasBean, BaseViewHolder> getAdapter() {
        return new ArticleAdapter(R.layout.item_article);
    }

    @Override
    protected SearchViewModel createViewModel() {
        return ViewModelProviders.of(this).get(SearchViewModel.class);
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
