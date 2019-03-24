package com.frame.library.widget;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.library.R;
import com.frame.library.utils.DisplayUtil;

public class TitleBar {

    private Context context;
    private View inflateView;

    //setContentView之后调用
    public TitleBar bind(Context context) {
        //获取activity根布局
        this.context = context;
        ViewGroup rootView = ((Activity) context).findViewById(android.R.id.content);
        View contentView = rootView.getChildAt(0);
        rootView.removeAllViews();
        LinearLayout parentLinearLayout = new LinearLayout(context);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        rootView.addView(parentLinearLayout);
        inflateView = LayoutInflater.from(context).inflate(R.layout.inc_title_bar, parentLinearLayout, true);
        parentLinearLayout.addView(contentView);
        return this;
    }

    public TitleBar bind(Context context, ViewGroup rootView) {
        inflateView = LayoutInflater.from(context).inflate(R.layout.inc_title_bar, rootView, true);
        return this;
    }

    public TitleBar setTitle(String title) {
        if (inflateView != null) {
            TextView tvTitle = inflateView.findViewById(R.id.tv_title);
            tvTitle.setSelected(true);
            tvTitle.setText(title);
        }
        return this;
    }

    //默认左侧返回箭头,点击关掉当前页面
    public TitleBar enableBack() {
        if (inflateView != null) {
            Toolbar toolBar = inflateView.findViewById(R.id.tool_bar);
            toolBar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolBar.setNavigationOnClickListener(view -> ((Activity) context).onBackPressed());
        }
        return this;
    }

    //设置左侧图片
    public TitleBar setLeftIcon(@DrawableRes int res) {
        if (inflateView != null) {
            Toolbar toolBar = inflateView.findViewById(R.id.tool_bar);
            toolBar.setNavigationIcon(res);
        }
        return this;
    }

    //左侧点击事件
    public TitleBar addLeftClickListener(View.OnClickListener listener) {
        if (inflateView != null) {
            Toolbar toolBar = inflateView.findViewById(R.id.tool_bar);
            toolBar.setNavigationOnClickListener(listener);
        }
        return this;
    }

    //menu以及点击事件
    public TitleBar setMenu(int menuRes, Toolbar.OnMenuItemClickListener listener) {
        if (inflateView != null) {
            Toolbar toolBar = inflateView.findViewById(R.id.tool_bar);
            toolBar.inflateMenu(menuRes);
            toolBar.setOnMenuItemClickListener(listener);
        }
        return this;
    }

    //适配状态栏
    public void fitStatusBar() {
        if (inflateView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) inflateView.getLayoutParams();
            layoutParams.setMargins(0,-DisplayUtil.getStatusBarHeight(),0,0);
            inflateView.setLayoutParams(layoutParams);
        }
    }
}
