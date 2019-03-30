package com.bingo.wanandroid.ui.user;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.ui.AboutActivity;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.frame.library.base.BaseSwipeBackActivity;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseSwipeBackActivity<UserViewModel> implements View.OnClickListener {

    private EditText tv_user_name;
    private EditText tv_pwd;

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
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        tv_user_name = (EditText) findViewById(R.id.tv_user_name);
        tv_pwd = (EditText) findViewById(R.id.tv_pwd);

    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivityForResult(new Intent(context, RegisterActivity.class), 100);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        String userName = tv_user_name.getText().toString().trim();
        String pwd = tv_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("请输入密码");
            return;
        }
        viewModel.login(userName, pwd).observe(this, user -> {
            EventBus.getDefault().post(user);
            ToastUtil.showToast("登录成功");
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            finish();
        }
    }
}
