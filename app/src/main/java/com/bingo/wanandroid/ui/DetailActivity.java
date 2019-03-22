package com.bingo.wanandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.bingo.wanandroid.R;
import com.frame.library.base.WebViewActivity;

public class DetailActivity extends WebViewActivity {
    private String title;

    public static void start(Context context, String url, String title) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra("url", url);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }

    @Override
    protected void getIntentData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected ViewGroup getWebContentView() {
        return findViewById(R.id.fl_content);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }
}
