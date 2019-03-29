package com.bingo.wanandroid.ui.user;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.CollectAdapter;
import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.CollectionEvent;
import com.bingo.wanandroid.entity.HomeUpdateEvent;
import com.bingo.wanandroid.entity.MyCollectionEvent;
import com.bingo.wanandroid.ui.detail.CollectDetailActivity;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.base.BaseListActivity;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyCollectActivity extends BaseListActivity<Article.DatasBean, UserViewModel> {

    private int position;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyCollectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onItemClick(Article.DatasBean item, int position) {
    }

    @Override
    protected void setToolBar() {
        EventBus.getDefault().register(this);
        new TitleBar().bind(this).setTitle("我的收藏").enableBack();
    }

    @Override
    protected void requestData() {
        viewModel.getCollection(pageIndex).observe(this, article -> {
            if (article != null) {
                pageSize = article.getSize();
                setListData(article.getDatas());
            }
        });
    }

    @Override
    protected BaseQuickAdapter<Article.DatasBean, BaseViewHolder> getAdapter() {
        CollectAdapter collectAdapter = new CollectAdapter(R.layout.item_collection);
        collectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseAdapter, View view, int position) {
                if (view.getId() == R.id.right) {
                    Article.DatasBean item = adapter.getItem(position);
                    if (item != null) {
                        viewModel.cancelCollection(item.getId(), item.getOriginId()).observe(MyCollectActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if (aBoolean) {
                                    adapter.remove(position);
                                    EventBus.getDefault().post(new HomeUpdateEvent());
                                }
                            }
                        });
                    }
                } else if (view.getId() == R.id.content) {
                    MyCollectActivity.this.position = position;
                    Article.DatasBean item = adapter.getItem(position);
                    if (item != null) {
                        CollectDetailActivity.start(context, item.getId(), item.getOriginId(), item.getLink(), item.getTitle());
                    }
                }
            }
        });
        return collectAdapter;
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Subscribe
    public void setCollect(CollectionEvent event) {
        if (!event.isCollect()){
            adapter.remove(position);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
