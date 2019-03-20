package com.frame.library.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

public class FragmentSwitchManager {
    private List<Fragment> fragments;
    private int lastFragment;//用于记录上个选择的Fragment
    private FragmentManager fragmentManager;
    private int fragmentRes;

    public FragmentSwitchManager(FragmentManager fragmentManager,List<Fragment> fragments, int fragmentRes) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.fragmentRes = fragmentRes;
    }

    //初始化
    public void init(){
        fragmentManager.beginTransaction().replace(fragmentRes, fragments.get(0)).show(fragments.get(0)).commit();
    }

    //切换Fragment
    public void switchFragment(int index) {
        if (lastFragment != index) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragments.get(lastFragment));
            if (!fragments.get(index).isAdded()) {
                transaction.add(fragmentRes, fragments.get(index));
            }
            transaction.show(fragments.get(index)).commitAllowingStateLoss();
            lastFragment = index;
        }
    }
}
