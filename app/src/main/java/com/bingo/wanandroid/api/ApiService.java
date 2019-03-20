package com.bingo.wanandroid.api;

import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.HomeBanner;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.WxArticle;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "http://www.wanandroid.com/";

    //获取首页banner
    @GET("banner/json")
    Observable<HttpResult<List<HomeBanner>>> getBanner();

    //置顶文章
//    @GET("article/top/json")

    //获取首页文章列表
    @GET("article/list/{pageIndex}/json")
    Observable<HttpResult<Article>> getLastArticle(@Path("pageIndex") int pageIndex);

    //首页最新项目
    @GET("article/listproject/{pageIndex}/json")
    Observable<HttpResult<Project>> getLastProject(@Path("pageIndex") int pageIndex);

    //获取公众号列表
    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<WxArticle>>> getWxarticleChapters();

}
