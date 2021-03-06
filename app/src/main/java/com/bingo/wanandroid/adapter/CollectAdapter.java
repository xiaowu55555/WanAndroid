package com.bingo.wanandroid.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.Article;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class CollectAdapter extends BaseQuickAdapter<Article.DatasBean, BaseViewHolder> {

    public CollectAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
        TextView tv_des = helper.getView(R.id.tv_des);
        tv_des.setVisibility(View.GONE);
        helper.setText(R.id.tv_des, Html.fromHtml(item.getDesc()));
        helper.getView(R.id.tv_top).setVisibility(item.getType() == 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_refresh).setVisibility(item.isFresh() ? View.VISIBLE : View.GONE);
        if (item.getTags() != null && item.getTags().size() > 0) {
            helper.setText(R.id.tv_tag, item.getTags().get(0).getName());
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.right);
        helper.addOnClickListener(R.id.content);
    }
}
