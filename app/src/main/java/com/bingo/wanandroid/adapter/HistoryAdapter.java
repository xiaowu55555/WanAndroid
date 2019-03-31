package com.bingo.wanandroid.adapter;

import com.bingo.wanandroid.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
        helper.addOnClickListener(R.id.right);
        helper.addOnClickListener(R.id.tv_name);
    }
}
