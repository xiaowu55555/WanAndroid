package com.bingo.wanandroid.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.ui.article.TabWxArticleFragment;
import com.bingo.wanandroid.ui.home.TabHomeFragment;
import com.bingo.wanandroid.ui.me.TabMeFragment;
import com.bingo.wanandroid.ui.project.TabProjectFragment;
import com.frame.library.base.BaseActivity;
import com.frame.library.base.BaseViewModel;
import com.frame.library.utils.FragmentSwitchManager;
import com.frame.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentSwitchManager switchManager;
    private BottomNavigationView navigation;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        navigation = (BottomNavigationView) findViewById(R.id.bottm_view);
        fragments.add(TabHomeFragment.newInstance());
        fragments.add(TabWxArticleFragment.newInstance());
        fragments.add(TabProjectFragment.newInstance());
        fragments.add(TabMeFragment.newInstance());

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
                case R.id.navigation_user:
                    switchManager.switchFragment(3);
                    return true;
            }
            return false;
        });
    }

    public void getSwitchManager() {
        navigation.setSelectedItemId(R.id.navigation_home);
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
//        startActivity(new Intent(context, MainActivity.class));
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        finish();
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
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
}
