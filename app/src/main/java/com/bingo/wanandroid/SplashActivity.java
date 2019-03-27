package com.bingo.wanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bingo.wanandroid.ui.MainActivity;

import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ParticleView pv = (ParticleView) findViewById(R.id.pv);
        pv.setOnParticleAnimListener(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        pv.startAnim();
    }

}
