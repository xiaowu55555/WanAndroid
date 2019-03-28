package com.bingo.wanandroid.ui.me;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.bingo.wanandroid.R;
import com.frame.library.base.WebViewActivity;
import com.frame.library.widget.TitleBar;

public class AboutActivity extends WebViewActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected ViewGroup getWebContentView() {
        return (ViewGroup) findViewById(R.id.fl_content);
    }

    @Override
    protected void getIntentData() {
        url = "https://www.wanandroid.com/about";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void setToolBar() {
        new TitleBar().bind(this).setTitle("关于我们").enableBack();
    }
}
