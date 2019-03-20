package com.frame.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.library.R;
import com.frame.library.event.ActionEvent;
import com.frame.library.Library;
import com.frame.library.utils.NetworkUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.LoadingPopView;
import com.frame.library.widget.MultipleStatusView;
import com.lxj.xpopup.XPopup;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {

    private boolean isPrepared;
    private boolean isLazyLoaded;//是否已经加载过数据
    protected Context context;
    protected T viewModel;
    protected View rootView;
    protected MultipleStatusView statusView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        statusView = rootView.findViewById(R.id.stateful_layout);
        viewModel = createViewModel();
        observeActionEvent();
        initView(rootView);
        return rootView;
    }

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

    protected abstract T createViewModel();

    protected abstract void initView(View rootView);

    protected abstract int getLayoutRes();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            isLazyLoaded = true;
            onLazyLoad();
        }
    }

    protected abstract void onLazyLoad();
}
