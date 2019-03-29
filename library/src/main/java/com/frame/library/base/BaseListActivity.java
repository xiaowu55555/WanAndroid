package com.frame.library.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.R;
import com.frame.library.Library;
import com.frame.library.utils.NetworkUtils;
import com.frame.library.utils.ToastUtil;

import java.util.List;


public abstract class BaseListActivity<V, T extends BaseViewModel> extends BaseSwipeBackActivity<T> implements
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    protected RecyclerView recyclerView;
    protected BaseQuickAdapter<V, BaseViewHolder> adapter;
    protected int pageIndex = 0;
    protected int pageSize = 20;

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(getLayoutManager());
        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setEnableLoadMore(enableLoadMore());
        if (enableLoadMore()) {
            adapter.setOnLoadMoreListener(this, recyclerView);
            adapter.disableLoadMoreIfNotFullPage();
        }
        requestData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        V item = this.adapter.getItem(position);
        if (item != null) {
            onItemClick(item,position);
        }
    }

    protected abstract void onItemClick(V item, int position);

    protected void setListData(List<V> list) {
        showContent();
        adapter.setEmptyView(getEmptyView());
        if (list != null) {
            if (pageIndex == 0) {
                adapter.setNewData(list);
            } else {
                adapter.addData(list);
            }
            if (list.size() < pageSize) {
                adapter.loadMoreEnd();
            } else {
                adapter.loadMoreComplete();
            }
        }
        adapter.setEnableLoadMore(enableLoadMore());
    }

    @Override
    protected void showError(String message) {
        ToastUtil.showToast(message);
        adapter.setEnableLoadMore(enableLoadMore());
        if (pageIndex == 0 && statusView != null) {
            if (!NetworkUtils.isNetworkAvailable(Library.getInstance().getContext())) {
                statusView.showNoNetwork();
            } else {
                statusView.showError();
            }
        } else {
            adapter.loadMoreFail();
        }
    }

    protected View getEmptyView() {
        return View.inflate(context, R.layout.empty_view, null);
    }

    protected boolean enableRefresh() {
        return true;//默认开启下拉刷新
    }

    protected abstract void requestData();

    protected boolean enableLoadMore() {
        return true;//默认开启加载更多
    }

    protected abstract BaseQuickAdapter<V, BaseViewHolder> getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.base_list_layout;
    }

    @Override
    protected void onRetry() {
        requestData();
    }

    @Override
    public void onLoadMoreRequested() {
        pageIndex++;
        requestData();
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        pageIndex = 0;
        requestData();
    }

    public class ListObserver implements Observer<List<V>> {

        @Override
        public void onChanged(@Nullable List<V> vs) {
            setListData(vs);
        }
    }
}
