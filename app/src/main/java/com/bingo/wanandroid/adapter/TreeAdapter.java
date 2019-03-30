package com.bingo.wanandroid.adapter;

import android.support.design.internal.FlowLayout;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.bingo.wanandroid.R;
import com.bingo.wanandroid.entity.Tree;
import com.bingo.wanandroid.entity.TreeChild;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TreeAdapter extends BaseQuickAdapter<Tree, BaseViewHolder> {

    public TreeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tree item) {
        helper.setText(R.id.tv_header, Html.fromHtml(item.getName()));

        FlowLayout layoutFlow = helper.getView(R.id.layout_flow);
        layoutFlow.removeAllViews();
        List<TreeChild> children = item.getChildren();
        if (children == null || children.isEmpty()) {
            layoutFlow.setVisibility(View.GONE);
            return;
        }
        layoutFlow.setVisibility(View.VISIBLE);
        for (int i = 0; i < children.size(); i++) {
            TreeChild tree = children.get(i);
            View child = View.inflate(mContext, R.layout.layout_tag_knowledge_tree, null);
            TextView textView = child.findViewById(R.id.tv_tag);

            String name = Html.fromHtml(tree.getName()).toString();
            SpannableString content = new SpannableString(name);
            content.setSpan(new UnderlineSpan(), 0, name.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(content);
            final int pos = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    KnowledgeFragment.startKnowledgeTreeTabActivity(mContext, item, pos);
                }
            });

            layoutFlow.addView(child);
        }
    }
}
