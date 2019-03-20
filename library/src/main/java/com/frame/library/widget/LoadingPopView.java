package com.frame.library.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.frame.library.R;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

public class LoadingPopView extends CenterPopupView {

    private String title;

    public LoadingPopView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.loading_pop;
    }

    @Override
    protected int getMaxWidth() {
        return (int) (XPopupUtils.getWindowWidth(getContext()) * 0.5f);
    }


    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        TextView tv_title = findViewById(R.id.tv_message);
        tv_title.setText(title == null ? "加载中..." : title);
    }

    public LoadingPopView setTitle(String title) {
        this.title = title;
        return this;
    }

}
