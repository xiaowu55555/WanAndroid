package com.bingo.wanandroid.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.app.App;
import com.bingo.wanandroid.ui.user.LoginActivity;
import com.bingo.wanandroid.viewmodel.UserViewModel;
import com.frame.library.base.WebViewActivity;
import com.frame.library.utils.ToastUtil;
import com.frame.library.widget.TitleBar;

public class ArticleDetailActivity extends WebViewActivity<UserViewModel> {
    private String title;
    private boolean isCollect;
    private MenuItem menuItem;
    private long id;

    public static void start(Context context, long id, String url, String title, boolean collect) {
        Intent starter = new Intent(context, ArticleDetailActivity.class);
        starter.putExtra("url", url);
        starter.putExtra("title", title);
        starter.putExtra("collect", collect);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }

    @Override
    protected void getIntentData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        isCollect = getIntent().getBooleanExtra("collect", false);
        id = getIntent().getLongExtra("id", 0);
    }

    @Override
    protected void setToolBar() {
        menuItem = new TitleBar().bind(this).setTitle(Html.fromHtml(title).toString()).enableBack()
                .setMenu(R.menu.menu_detail, new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_like:
                                if (App.getInstance().isLogin()){
                                    setCollect();
                                } else {
                                    LoginActivity.start(context);
                                }
                                break;
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
        if (App.getInstance().isLogin()){
            menuItem.setTitle(isCollect ? "已收藏" : "收藏");
        } else {
            menuItem.setTitle("收藏");
        }
    }

    private void setCollect() {
        if (isCollect) {
            viewModel.articleCancelCollection(id).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean) {
                        isCollect = false;
                        menuItem.setTitle("收藏");
                    }
                }
            });
        } else {
            viewModel.collect(id).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean) {
                        ToastUtil.showToast("收藏成功");
                        isCollect = true;
                        menuItem.setTitle("已收藏");
                    }
                }
            });
        }
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
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }
}