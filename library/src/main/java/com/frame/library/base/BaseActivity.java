package com.frame.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.frame.library.R;
import com.frame.library.event.ActionEvent;
import com.frame.library.Library;
import com.frame.library.utils.NetworkUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.LoadingPopView;
import com.frame.library.widget.MultipleStatusView;
import com.lxj.xpopup.XPopup;

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {
    protected Context context;
    protected T viewModel;
    protected MultipleStatusView statusView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutRes());
        context = this;
        viewModel = createViewModel();
        observeActionEvent();
        statusView = findViewById(R.id.stateful_layout);
        initView(savedInstanceState);
    }

    protected void beforeSetContentView() {
    }

    protected abstract int getLayoutRes();

    private void observeActionEvent() {
        if (viewModel != null) {
            viewModel.getAction().observe(this, actionEvent -> {
                if (statusView != null && statusView.getViewStatus() == MultipleStatusView.STATUS_LOADING) {
                    statusView.showContent();
                }
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

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract T createViewModel();


}
