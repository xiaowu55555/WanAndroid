package com.bingo.wanandroid.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.Project;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ProjectAdapter extends BaseQuickAdapter<Project.DatasBean, BaseViewHolder> {

    public ProjectAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Project.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_author, item.getAuthor() + "  " + item.getNiceDate());
        helper.setText(R.id.tv_des, Html.fromHtml(item.getDesc()));
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        Glide.with(mContext).load(item.getEnvelopePic()).placeholder(R.color.color_app_divider).into(iv_cover);

        helper.getView(R.id.tv_top).setVisibility(item.getType() == 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_refresh).setVisibility(item.isFresh() ? View.VISIBLE : View.GONE);
    }
}
