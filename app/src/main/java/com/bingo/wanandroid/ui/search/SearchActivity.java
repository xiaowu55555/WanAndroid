package com.bingo.wanandroid.ui.search;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.adapter.HistoryAdapter;
import com.bingo.wanandroid.entity.SearchKey;
import com.bingo.wanandroid.viewmodel.SearchViewModel;
import com.frame.library.base.BaseSwipeBackActivity;
import com.frame.library.utils.KeyboardUtils;
import com.frame.library.widget.DialogHelp;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseSwipeBackActivity<SearchViewModel> {

    private TagFlowLayout flowLayout;
    private HistoryAdapter historyAdapter;
    private List<String> mList = new ArrayList<>();


    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected SearchViewModel createViewModel() {
        return ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initSearchView();
        initHoeKey();
        initHistory();
    }

    private void initSearchView() {
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
                viewModel.addHistory(s);
                SearchResultActivity.start(context, s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initHoeKey() {
        flowLayout = findViewById(R.id.flow_layout);
        viewModel.getHotKey().observe(this, searchKeys -> {
            TagAdapter adapter = new TagAdapter<SearchKey>(searchKeys) {
                @Override
                public View getView(FlowLayout parent, int position, SearchKey searchKey) {
                    View tagView = View.inflate(context, R.layout.item_hot_tag, null);
                    TextView tvTag = tagView.findViewById(R.id.tv_tag);
                    tvTag.setText(Html.fromHtml(searchKey.getName()));
                    return tagView;
                }
            };
            flowLayout.setAdapter(adapter);
            flowLayout.setOnTagClickListener((view, position, parent) -> {
                viewModel.addHistory(searchKeys.get(position).getName());
                SearchResultActivity.start(context, searchKeys.get(position).getName());
                return false;
            });
        });
    }

    private void initHistory() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        historyAdapter = new HistoryAdapter(R.layout.item_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(historyAdapter);
        viewModel.getHistoryList().observe(this, strings -> {
            mList.clear();
            mList.addAll(strings);
            historyAdapter.setNewData(strings);
        });
        findViewById(R.id.iv_delete).setOnClickListener(view -> {
            if (mList.size() > 0) {
                DialogHelp.getConfirmDialog(context, "删除所有历史记录?",
                        (dialog, which) -> viewModel.deleteHistory(),
                        (dialog, which) -> dialog.dismiss()).create().show();
            }
        });
        historyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.right) {
                viewModel.deleteHistoryByIndex(position);
            } else if (view.getId() == R.id.tv_name) {
                String item = historyAdapter.getItem(position);
                SearchResultActivity.start(context, item);
            }
        });
    }

}
