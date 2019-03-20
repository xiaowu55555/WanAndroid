package com.bingo.wanandroid.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.ui.home.TabHomeFragment;
import com.frame.library.base.BaseActivity;
import com.frame.library.base.BaseViewModel;
import com.frame.library.utils.FragmentSwitchManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentSwitchManager switchManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        BottomNavigationView navigation = findViewById(R.id.bottm_view);
        fragments.add(TabHomeFragment.newInstance());
//        fragments.add(Fragment1.newInstance());
//        fragments.add(Fragment1.newInstance());
//        fragments.add(Fragment1.newInstance());
        switchManager = new FragmentSwitchManager(getSupportFragmentManager(), fragments, R.id.fl_content);
        switchManager.init();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            }
        });
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }
}
