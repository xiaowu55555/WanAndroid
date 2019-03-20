package com.bingo.wanandroid.adapter;


import android.graphics.Color;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.WxArticle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Random;

public class WxArticleAdapter extends BaseQuickAdapter<WxArticle, BaseViewHolder> {

    private String[] colors = {"#FF4CAF50","#FF009688","#FF2196F3",
            "#FFFF5722", "#FF00BCD4", "#FFA45E", "#FFF57A", "#C9FF82",
            "#75FFDA", "#70C2FF", "#6F81FF", "#A561FF", "#FF6B6B"};

    public WxArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WxArticle item) {
        helper.setText(R.id.tv_name, item.getName());
        TextView tvName = helper.getView(R.id.tv_name);
        tvName.setBackgroundColor(Color.parseColor(colors[new Random().nextInt(colors.length)]));
    }
}
