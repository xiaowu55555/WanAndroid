package com.bingo.wanandroid.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.Article;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.library.utils.SPUtils;

public class ArticleAdapter extends BaseQuickAdapter<Article.DatasBean, BaseViewHolder> {

    public ArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article.DatasBean item) {
        boolean isRead = SPUtils.getInstance().getBoolean(String.valueOf(item.getId()), false);
        if (isRead) {
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.item_title_read));
            helper.setTextColor(R.id.tv_des, mContext.getResources().getColor(R.color.item_desc_read));
            helper.setTextColor(R.id.tv_author, mContext.getResources().getColor(R.color.item_author_read));
        } else {
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.item_title));
            helper.setTextColor(R.id.tv_des, mContext.getResources().getColor(R.color.item_desc));
            helper.setTextColor(R.id.tv_author, mContext.getResources().getColor(R.color.item_author));
        }
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
        TextView tv_des = helper.getView(R.id.tv_des);
        if (TextUtils.isEmpty(item.getDesc())) {
            tv_des.setVisibility(View.GONE);
        } else {
            tv_des.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_des, Html.fromHtml(item.getDesc()));
        }
        helper.getView(R.id.tv_top).setVisibility(item.getType() == 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_refresh).setVisibility(item.isFresh() ? View.VISIBLE : View.GONE);
        if (item.getTags() != null && item.getTags().size() > 0) {
            helper.setText(R.id.tv_tag, item.getTags().get(0).getName());
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        }
    }
}
