package com.bingo.wanandroid.ui.article;

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
import com.frame.library.utils.SPUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ArticleListActivity extends BaseListActivity<Article.DatasBean, ArticleViewModel> {
    private WxArticle wxArticle;
    private int position = -1;

    public static void start(Context context, WxArticle wxArticle) {
        Intent starter = new Intent(context, ArticleListActivity.class);
        starter.putExtra("wxArticle", wxArticle);
        context.startActivity(starter);
    }

    @Override
    protected void getIntentData() {
        wxArticle = getIntent().getParcelableExtra("wxArticle");
        if (wxArticle == null) {
            ToastUtil.showToast("参数错误");
            finish();
        }
    }

    @Override
    protected void setToolBar() {
        EventBus.getDefault().register(this);
        new TitleBar().bind(this).setTitle(Html.fromHtml(wxArticle.getName()).toString()).enableBack();
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
        viewModel.getArticleList(wxArticle.getId(), pageIndex).observe(this, article -> {
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
