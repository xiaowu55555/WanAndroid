package com.bingo.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bingo.wanandroid.entity.HomeBanner;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        HomeBanner banner = (HomeBanner) path;
        Glide.with(context).load(banner.getImagePath()).into(imageView);
    }
}
