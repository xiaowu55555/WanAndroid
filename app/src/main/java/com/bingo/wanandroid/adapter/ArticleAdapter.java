package com.bingo.wanandroid.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.Article;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ArticleAdapter extends BaseQuickAdapter<Article.DatasBean, BaseViewHolder> {

    public ArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_author, "@" + item.getAuthor() + "  " + item.getNiceDate());
        TextView tv_des = helper.getView(R.id.tv_des);
        if (TextUtils.isEmpty(item.getDesc())) {
            tv_des.setVisibility(View.GONE);
        } else {
            tv_des.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_des, Html.fromHtml(item.getDesc()));
        }
    }
}
