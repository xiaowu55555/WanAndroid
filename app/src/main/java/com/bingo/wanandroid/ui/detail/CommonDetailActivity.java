package com.bingo.wanandroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.bingo.wanandroid.R;
import com.frame.library.base.BaseViewModel;
import com.frame.library.base.WebViewActivity;
import com.frame.library.widget.TitleBar;

public class CommonDetailActivity extends WebViewActivity {
    private String title;

    public static void start(Context context, String url, String title) {
        Intent starter = new Intent(context, CommonDetailActivity.class);
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
    protected void setToolBar() {
        MenuItem menuItem = new TitleBar().bind(this).setTitle(Html.fromHtml(title).toString()).enableBack()
                .setMenu(R.menu.menu_common_detail, new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                }).getMenuItem(0);
    }

    @Override
    protected ViewGroup getWebContentView() {
        return (ViewGroup) findViewById(R.id.fl_content);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }
}
