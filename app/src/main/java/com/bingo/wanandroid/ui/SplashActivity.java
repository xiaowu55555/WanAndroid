package com.bingo.wanandroid.ui;

import android.content.Intent;
import android.os.Bundle;

import com.bingo.wanandroid.R;
import com.frame.library.base.BaseActivity;
import com.frame.library.base.BaseViewModel;

import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ParticleView pv = (ParticleView) findViewById(R.id.pv);
        pv.setOnParticleAnimListener(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        pv.startAnim();
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

}
