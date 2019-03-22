package com.frame.library.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AbsTitleBar {

    protected Builder mBuilder;

    public AbsTitleBar(Builder builder) {
        mBuilder = builder;
        onCreate();
    }

    private void onCreate() {
        //防止继承Activity获取不到相应的根视图
        if (mBuilder.mParent == null) {
            //获取Activity的根视图，这个是AppCompatActivity才有的
            mBuilder.mParent = ((Activity) mBuilder.mContext).findViewById(android.R.id.content);
        }

        if (mBuilder.mParent == null) {
            return;
        }
        //1，创建导航视图
        View mNavigationView = LayoutInflater.from(mBuilder.mContext).inflate(bindLayout(), mBuilder.mParent, false);
        //2,添加到父控件
        mBuilder.mParent.addView(mNavigationView);
        bindView();
    }

    /**
     * 自己封装的Toolbar布局
     *
     * @return
     */
    protected abstract int bindLayout();

    /**
     * 设置Toolbar布局中的文本，图片，点击事件
     */
    protected abstract void bindView();

    /**
     * 给图片控件赋值
     *
     * @param id
     * @param resId
     */
    protected void setIcon(int id, int resId) {
        ImageView iv = mBuilder.mParent.findViewById(id);
        if (resId != 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(resId);
        }
    }

    /**
     * 给文本控件赋值
     *
     * @param id
     * @param text
     */
    protected void setText(int id, String text) {
        TextView tv = mBuilder.mParent.findViewById(id);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }


    public abstract static class Builder {
        public Context mContext;

        public String mTitle;
        public String mLeftText;
        public int mLeftIcon;
        public String mRightText;
        public int mRightIcon;
        public OnTitleListener mListener;
        public ViewGroup mParent;


        public Builder(Context context, ViewGroup parent) {
            mContext = context;
            mParent = parent;
        }


        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setLeftText(String leftText) {
            mLeftText = leftText;
            return this;
        }

        public Builder setRightText(String rightText) {
            mRightText = rightText;
            return this;
        }


        public Builder setLeftIcon(int leftIcon) {
            mLeftIcon = leftIcon;
            return this;
        }

        public Builder setRightIcon(int rightIcon) {
            mRightIcon = rightIcon;
            return this;
        }


        public Builder addListener(OnTitleListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 将Builder的值通过构造方法传给BaseTitleBar
         * 在BaseTitleBar中获取参数的值
         *
         * @return
         */
        public abstract AbsTitleBar build();

    }

    interface OnTitleListener {
        /**
         * 左边文字点击
         */
        void onLeftText();

        /**
         * 左边图标的点击
         */
        void onLeftIcon();

        /**
         * 右边文字点击
         */
        void onRightText();

        /**
         * 右边图标的点击
         */
        void onRightIcon();
    }

}
