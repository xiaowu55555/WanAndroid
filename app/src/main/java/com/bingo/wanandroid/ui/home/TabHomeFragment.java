package com.bingo.wanandroid.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.TabFragmentAdapter;
import com.bingo.wanandroid.entity.HomeBanner;
import com.bingo.wanandroid.utils.GlideImageLoader;
import com.bingo.wanandroid.viewmodel.HomeViewModel;
import com.frame.library.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TabHomeFragment extends BaseFragment<HomeViewModel> {

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private List<HomeBanner> mBannerList = new ArrayList<>();
    private List<String> mBannerTitles = new ArrayList<>();
    private Banner mBanner;

    public static TabHomeFragment newInstance() {
        return new TabHomeFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_home;
    }

    @Override
    protected void initView(View rootView) {
        mBanner = rootView.findViewById(R.id.banner);
        TabLayout mTabLayout = rootView.findViewById(R.id.tablayout);
        ViewPager mViewPager = rootView.findViewById(R.id.viewpager);
        fragments.add(LastArticleFragment.newInstance());
        fragments.add(LastProjectFragment.newInstance());
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        titles.add("最新博文");
        titles.add("最新项目");
        mViewPager.setAdapter(new TabFragmentAdapter(getFragmentManager(), titles, fragments));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected HomeViewModel createViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    protected void onLazyLoad() {
        viewModel.getData().observe(this, list -> {
            if (list != null) {
                mBannerList.addAll(list);
                for (int i = 0; i < list.size(); i++) {
                    mBannerTitles.add(list.get(i).getTitle());
                }
            }
            initBanner();
        });
    }

    private void initBanner() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .setImages(mBannerList)
                .setBannerTitles(mBannerTitles)
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
    }
}
