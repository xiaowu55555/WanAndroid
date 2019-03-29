package com.bingo.wanandroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
                        switch (menuItem.getItemId()) {
                            case R.id.action_share:
                                showShare();
                                break;
                            case R.id.action_browser:
                                Uri uri = Uri.parse(url);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                }).getMenuItem(0);
    }

    private void showShare() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, title);//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, url);//添加分享内容
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
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
