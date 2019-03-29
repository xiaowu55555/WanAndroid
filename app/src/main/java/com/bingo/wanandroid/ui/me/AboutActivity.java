package com.bingo.wanandroid.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bingo.wanandroid.BuildConfig;
import com.bingo.wanandroid.R;
import com.frame.library.base.BaseActivity;
import com.frame.library.base.BaseSwipeBackActivity;
import com.frame.library.base.BaseViewModel;
import com.frame.library.base.WebViewActivity;
import com.frame.library.widget.TitleBar;

public class AboutActivity extends BaseSwipeBackActivity {

    private TextView tv_content;

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tv_content = findViewById(R.id.tv_content);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content.setText(Html.fromHtml(getString(R.string.about_content)));
        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText("V " + BuildConfig.VERSION_NAME);
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected void setToolBar() {
        new TitleBar().bind(this).setTitle("关于我们").enableBack();
    }
}
