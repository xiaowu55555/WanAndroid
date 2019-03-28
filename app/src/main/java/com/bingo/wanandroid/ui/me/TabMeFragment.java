package com.bingo.wanandroid.ui.me;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.constant.Constant;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.ui.MainActivity;
import com.bingo.wanandroid.ui.user.LoginActivity;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.frame.library.base.BaseFragment;
import com.frame.library.utils.SPUtils;
import com.frame.library.widget.DialogHelp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabMeFragment extends BaseFragment<UserViewModel> implements View.OnClickListener {

    private CircleImageView iv_avatar;
    private TextView tv_user_name;
    private SwitchCompat swNight;

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
        rootView.findViewById(R.id.tv_collect).setOnClickListener(this);
        rootView.findViewById(R.id.tv_todo).setOnClickListener(this);
        rootView.findViewById(R.id.tv_about).setOnClickListener(this);
        rootView.findViewById(R.id.tv_logout).setOnClickListener(this);
        swNight = rootView.findViewById(R.id.sw_night);
        initTheme();
    }

    private void initTheme() {
        if (SPUtils.getInstance().getBoolean(Constant.ISNIGHT)) {
            swNight.setChecked(true);
        } else {
            swNight.setChecked(false);
        }
        swNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SPUtils.getInstance().put(Constant.ISNIGHT, isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            ((MainActivity) getActivity()).getSwitchManager();
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
            case R.id.tv_collect:
                break;
            case R.id.tv_todo:
                break;
            case R.id.tv_logout:
                if (App.getInstance().isLogin()) {
                    DialogHelp.getConfirmDialog(context, "退出当前账号?",
                            (dialog, which) -> logout(),
                            (dialog, which) -> dialog.dismiss()).create().show();
                }
                break;
            case R.id.tv_about:
                AboutActivity.start(context);
                break;
        }
    }

    @Subscribe
    public void notifyLogin(User user) {
        tv_user_name.setText(user.getUsername());
        iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avadar_login));
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
