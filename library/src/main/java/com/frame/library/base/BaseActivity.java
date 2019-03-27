package com.frame.library.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import com.frame.library.Library;
import com.frame.library.R;
import com.frame.library.event.ActionEvent;
import com.frame.library.utils.NetworkUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.DialogHelp;
import com.frame.library.widget.MultipleStatusView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivity<T extends BaseViewModel> extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener {
    protected Context context;
    protected T viewModel;
    protected MultipleStatusView statusView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected static final int PAGE_TYPE_TOOLBAR = 0;
    protected static final int PAGE_TYPE_NO_TOOLBAR = 1;
    protected static final int PAGE_TYPE_FULL_SCREEN = 2;
    private ProgressDialog waitDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        viewModel = createViewModel();
        observeActionEvent();
        getIntentData();
        setContentView(getLayoutRes());
        setSwipeBackEnable(enableSwipeBack());
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setToolBar();
        statusView = (MultipleStatusView) findViewById(R.id.stateful_layout);
        if (statusView != null) {
            statusView.showLoading();
            statusView.setOnRetryClickListener(v -> {
                statusView.showLoading();
                onRetry();
            });
        }
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        if (enableRefresh() && swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        initView(savedInstanceState);
    }

    protected boolean enableSwipeBack() {
        return true;
    }

    protected void setToolBar() {

    }

    protected void getIntentData() {

    }

    protected void onRetry() {

    }

    protected int getPageType() {
        return PAGE_TYPE_TOOLBAR;//默认返回带toolbar
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
                        showLoading(actionEvent.getMessage());
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
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    protected void showLoading(String message) {
        waitDialog = DialogHelp.getWaitDialog(context, message);
        waitDialog.show();
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
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
        super.onDestroy();
    }
}
