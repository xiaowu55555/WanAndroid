package com.bingo.wanandroid.adapter;


import android.graphics.Color;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.WxArticle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Random;

public class WxArticleAdapter extends BaseQuickAdapter<WxArticle, BaseViewHolder> {

    public WxArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WxArticle item) {
        helper.setText(R.id.tv_name, item.getName());
        TextView tvName = helper.getView(R.id.tv_name);
        String[] colors = mContext.getResources().getStringArray(R.array.random_color);
        tvName.setBackgroundColor(Color.parseColor(colors[new Random().nextInt(colors.length)]));
    }
}
