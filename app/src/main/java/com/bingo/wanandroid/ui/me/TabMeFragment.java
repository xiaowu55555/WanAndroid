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
import com.frame.library.widget.DialogHelp;
import com.frame.library.widget.LSettingItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabMeFragment extends BaseFragment<UserViewModel> implements View.OnClickListener {

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
        EventBus.getDefault().register(this);
        iv_avatar = rootView.findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(this);
        tv_user_name = rootView.findViewById(R.id.tv_user_name);
        intiUser();
        LSettingItem ls_logout = rootView.findViewById(R.id.ls_logout);
        ls_logout.setmOnLSettingItemClick(isChecked -> {
            if (App.getInstance().isLogin()) {
                DialogHelp.getConfirmDialog(context, "退出当前账号?",
                        (dialog, which) -> logout(),
                        (dialog, which) -> dialog.dismiss()).create().show();
            }
        });
    }

    private void logout() {
        tv_user_name.setText("点击头像登录");
        iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        viewModel.logout();
    }

    private void intiUser() {
        User user = App.getInstance().getUser();
        if (user != null) {
            tv_user_name.setText(user.getUsername());
            iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avadar_login));
        } else {
            tv_user_name.setText("点击头像登录");
            iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        }
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
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

    @Subscribe
    public void notifyLogin(User user){
        tv_user_name.setText(user.getUsername());
        iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avadar_login));
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
