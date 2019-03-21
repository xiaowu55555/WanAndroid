package com.frame.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.frame.library.R;
import com.frame.library.event.ActionEvent;
import com.frame.library.Library;
import com.frame.library.utils.NetworkUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.LoadingPopView;
import com.frame.library.widget.MultipleStatusView;
import com.lxj.xpopup.XPopup;

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    protected Context context;
    protected T viewModel;
    protected MultipleStatusView statusView;
    protected SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutRes());
        context = this;
        viewModel = createViewModel();
        observeActionEvent();
        statusView = findViewById(R.id.stateful_layout);
        if (statusView != null) {
            statusView.showLoading();
            statusView.setOnRetryClickListener(v -> {
                statusView.showLoading();
                onRetry();
            });
        }
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        if (enableRefresh() && swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        getIntentData();
        initToolBar();
        initView(savedInstanceState);
    }

    private void initToolBar() {
        TextView tv_title = findViewById(R.id.tv_title);
        if (tv_title != null) {
            tv_title.setSelected(true);
            tv_title.setText(setTitle());
        }
        Toolbar toolbar = findViewById(R.id.tool_bar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
            toolbar.setNavigationOnClickListener(v -> finish());
            setToolBarMemu(toolbar);
        }
    }

    protected void setToolBarMemu(Toolbar toolbar) {

    }

    protected String setTitle() {
        return "";
    }

    protected void getIntentData() {

    }

    protected void onRetry() {

    }

    protected void beforeSetContentView() {
    }

    protected boolean enableRefresh() {
        return false;
    }

    protected abstract int getLayoutRes();

    private void observeActionEvent() {
        if (viewModel != null) {
            viewModel.getAction().observe(this, actionEvent -> {
                showContent();
                switch (actionEvent.getAction()) {
                    case ActionEvent.SHOW_LOADING:
                        showLoading();
                        break;
                    case ActionEvent.HIDE_LOADING:
                        hideLoading();
                        break;
                    case ActionEvent.SHOW_ERROR:
                        showError(actionEvent.getMessage());
                        break;
                    case ActionEvent.SHOW_TOAST:
                        ToastUtil.showToast(actionEvent.getMessage());
                        break;
                }
            });
        }
    }

    protected void hideLoading() {
        XPopup.get(context).dismiss();
    }

    protected void showLoading() {
        XPopup.get(context)
                .asCustom(new LoadingPopView(context))
                .dismissOnTouchOutside(false)
                .show();
    }

    protected void showError(String message) {
        ToastUtil.showToast(message);
        if (statusView != null) {
            if (!NetworkUtils.isNetworkAvailable(Library.getInstance().getContext())) {
                statusView.showNoNetwork();
            } else {
                statusView.showError();
            }
        }
    }

    @Override
    public void onRefresh() {
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract T createViewModel();

    protected void showContent() {
        if (statusView != null && statusView.getViewStatus() == MultipleStatusView.STATUS_LOADING) {
            statusView.showContent();
        }
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


}
