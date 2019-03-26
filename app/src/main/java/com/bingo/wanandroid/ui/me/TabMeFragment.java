package com.bingo.wanandroid.ui.me;

import android.arch.lifecycle.ViewModelProviders;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.ui.user.LoginActivity;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.frame.library.base.BaseFragment;
import com.frame.library.base.BaseViewModel;
import com.frame.library.widget.LSettingItem;
import com.lxj.xpopup.XPopup;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabMeFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView iv_avatar;
    private TextView tv_user_name;

    public static TabMeFragment newInstance() {
        return new TabMeFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_me;
    }

    @Override
    protected void initView(View rootView) {
        iv_avatar = rootView.findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(this);
        tv_user_name = rootView.findViewById(R.id.tv_user_name);
        LSettingItem ls_logout = rootView.findViewById(R.id.ls_logout);
        ls_logout.setmOnLSettingItemClick(isChecked -> {
            User user = App.getInstance().getUser();
            if (user != null) {
                XPopup.get(context).asConfirm(
                        "退出当前账号",
                        null,
                        this::logout,
                        () -> XPopup.get(context).dismiss(),
                        false).show();
            }
        });
    }

    private void logout() {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.logout().observe(this, s -> {
            intiUser();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        intiUser();
    }

    private void intiUser() {
        User user = App.getInstance().getUser();
        if (user != null) {
            tv_user_name.setText(user.getUsername());
            iv_avatar.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        } else {
            tv_user_name.setText("点击头像登录");
            iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        }
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onLazyLoad() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                User user = App.getInstance().getUser();
                if (user == null) {
                    LoginActivity.start(context);
                }
                break;
        }
    }
}
