package com.bingo.wanandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.bingo.wanandroid.R;
import com.frame.library.base.WebViewActivity;
import com.frame.library.widget.TitleBar;

public class DetailActivity extends WebViewActivity {
    private String title;

    public static void start(Context context, String url, String title) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra("url", url);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }

    @Override
    protected int getPageType() {
        return PAGE_TYPE_TOOLBAR;
    }

    @Override
    protected void getIntentData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void setToolBar() {
        MenuItem menuItem = new TitleBar().bind(this).setTitle(Html.fromHtml(title).toString()).enableBack()
                .setMenu(R.menu.menu_detail, new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                }).getMenuItem(0);
        menuItem.setIcon(R.drawable.ic_favorite_black_24dp);
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
