package com.bingo.wanandroid.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.bingo.wanandroid.R;
import com.frame.library.base.BaseSwipeBackActivity;
import com.frame.library.base.BaseViewModel;
import com.frame.library.utils.KeyboardUtils;
import com.frame.library.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;

public class SearchActivity extends BaseSwipeBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Toolbar toolBar = findViewById(R.id.tool_bar);
        toolBar.setNavigationIcon(com.frame.library.R.drawable.ic_arrow_back);
        toolBar.setNavigationOnClickListener(view -> ((Activity) context).onBackPressed());
        SearchView searchView = findViewById(R.id.search_view);
        SearchView.SearchAutoComplete tv = searchView.findViewById(R.id.search_src_text);
        tv.setTextColor(Color.WHITE);
        tv.setHintTextColor(getResources().getColor(R.color.bg_gray));
        tv.setTextSize(16);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                KeyboardUtils.hideSoftInput(SearchActivity.this);
                ToastUtil.showToast(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        FlowLayout flowLayout = findViewById(R.id.flow_layout);

    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }
}
