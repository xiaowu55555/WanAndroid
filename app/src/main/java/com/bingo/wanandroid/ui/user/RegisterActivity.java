package com.bingo.wanandroid.ui.user;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.frame.library.base.BaseActivity;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

public class RegisterActivity extends BaseActivity<UserViewModel> {

    private EditText tv_user_name;
    private EditText tv_pwd;
    private EditText tv_re_pwd;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void setToolBar() {
        new TitleBar().bind(this).setTitle("注册").enableBack();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.btn_register).setOnClickListener(v -> regist());
        tv_user_name = (EditText) findViewById(R.id.tv_user_name);
        tv_pwd = (EditText) findViewById(R.id.tv_pwd);
        tv_re_pwd = (EditText) findViewById(R.id.tv_re_pwd);
    }

    private void regist() {
        String userName = tv_user_name.getText().toString().trim();
        String pwd = tv_pwd.getText().toString().trim();
        String rePwd = tv_re_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(rePwd)) {
            ToastUtil.showToast("请再次输入密码");
            return;
        }
        if (!pwd.equals(rePwd)) {
            ToastUtil.showToast("两次密码输入不一致");
            return;
        }

        viewModel.register(userName, pwd, rePwd).observe(this, user -> {
            EventBus.getDefault().post(user);
            ToastUtil.showToast("注册成功");
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }
}
