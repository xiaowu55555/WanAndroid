package com.bingo.wanandroid.ui.me;

import android.view.View;

import com.bingo.wanandroid.R;
import com.frame.library.base.BaseFragment;
import com.frame.library.base.BaseViewModel;

import qiu.niorgai.StatusBarCompat;

public class TabMeFragment extends BaseFragment {

    public static TabMeFragment newInstance() {
        return new TabMeFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_me;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onLazyLoad() {

    }
}
