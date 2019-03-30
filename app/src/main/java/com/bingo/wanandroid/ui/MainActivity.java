package com.bingo.wanandroid.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.constant.Constant;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.ui.Tree.TabTreeFragment;
import com.bingo.wanandroid.ui.article.TabWxArticleFragment;
import com.bingo.wanandroid.ui.home.TabHomeFragment;
import com.bingo.wanandroid.ui.project.TabProjectFragment;
import com.bingo.wanandroid.ui.user.LoginActivity;
import com.bingo.wanandroid.ui.user.MyCollectActivity;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.frame.library.base.BaseActivity;
import com.frame.library.utils.FragmentSwitchManager;
import com.frame.library.utils.SPUtils;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.DialogHelp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<UserViewModel> implements View.OnClickListener {

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentSwitchManager switchManager;
    private BottomNavigationView navigation;
    private ImageView iv_avatar;
    private TextView tv_user_name;
    private SwitchCompat swNight;
    private DrawerLayout drawerLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigation = (BottomNavigationView) findViewById(R.id.bottm_view);
        fragments.add(TabHomeFragment.newInstance());
        fragments.add(TabWxArticleFragment.newInstance());
        fragments.add(TabProjectFragment.newInstance());
        fragments.add(TabTreeFragment.newInstance());

        switchManager = new FragmentSwitchManager(getSupportFragmentManager(), fragments, R.id.fl_content);
        switchManager.init();
        navigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    switchManager.switchFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    switchManager.switchFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    switchManager.switchFragment(2);
                    return true;
                case R.id.navigation_tree:
                    switchManager.switchFragment(3);
                    return true;
            }
            return false;
        });
        findViewById(R.id.tv_collect).setOnClickListener(this);
        findViewById(R.id.tv_todo).setOnClickListener(this);
        findViewById(R.id.tv_about).setOnClickListener(this);
        findViewById(R.id.tv_logout).setOnClickListener(this);
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(this);
        tv_user_name = findViewById(R.id.tv_user_name);
        intiUser();

        swNight = findViewById(R.id.sw_night);
        initTheme();
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
            navigation.setSelectedItemId(R.id.navigation_home);
            getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            recreate();
        });
    }


    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                User user = App.getInstance().getUser();
                if (user == null) {
                    LoginActivity.start(context);
                }
                break;
            case R.id.tv_collect:
                if (App.getInstance().isLogin()){
                    MyCollectActivity.start(context);
                } else {
                    LoginActivity.start(context);
                }
                break;
            case R.id.tv_todo:
                if (App.getInstance().isLogin()){

                } else {
                    LoginActivity.start(context);
                }
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

    private void logout() {
        tv_user_name.setText("点击头像登录");
        iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
        viewModel.logout();
    }

    @Subscribe
    public void notifyLogin(User user) {
        tv_user_name.setText(user.getUsername());
        iv_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avadar_login));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
