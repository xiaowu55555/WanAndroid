package com.bingo.wanandroid.ui.user;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.viewmodel.LoginViewModel;
import com.frame.library.base.BaseActivity;
import com.frame.library.widget.TitleBar;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void setToolBar() {
        new TitleBar().bind(this).setTitle("登录").enableBack();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected LoginViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }
}
