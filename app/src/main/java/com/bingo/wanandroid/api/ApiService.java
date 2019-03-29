package com.bingo.wanandroid.api;

import com.bingo.wanandroid.entity.Article;
import com.bingo.wanandroid.entity.HomeBanner;
import com.bingo.wanandroid.entity.Project;
import com.bingo.wanandroid.entity.ProjectTree;
import com.bingo.wanandroid.entity.User;
import com.bingo.wanandroid.entity.WxArticle;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://www.wanandroid.com/";

    //获取首页banner
    @GET("banner/json")
    Observable<HttpResult<List<HomeBanner>>> getBanner();

    //置顶文章
    @GET("article/top/json")
    Observable<HttpResult<List<Article.DatasBean>>> getTopArticle();

    //获取首页文章列表
    @GET("article/list/{pageIndex}/json")
    Observable<HttpResult<Article>> getLastArticle(@Path("pageIndex") int pageIndex);

    //首页最新项目
    @GET("article/listproject/{pageIndex}/json")
    Observable<HttpResult<Project>> getLastProject(@Path("pageIndex") int pageIndex);

    //获取公众号列表
    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<WxArticle>>> getWxarticleChapters();

    //查看某个公众号历史数据
    @GET("wxarticle/list/{id}/{pageIndex}/json")
    Observable<HttpResult<Article>> getWxarticleList(@Path("id") long id, @Path("pageIndex") int pageIndex);

    //项目分类
    @GET("project/tree/json")
    Observable<HttpResult<List<ProjectTree>>> getProjectTree();

    //项目列表数据
    @GET("project/list/{pageIndex}/json")
    Observable<HttpResult<Project>> getProjectList(@Path("pageIndex") int pageIndex, @Query("cid") long cid);

    //注册
    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpResult<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResult<User>> login(@Field("username") String username, @Field("password") String password);

    //退出登录
    @GET("user/logout/json")
    Observable<HttpResult> logout();

    //我的收藏列表
    @GET("lg/collect/list/{pageIndex}/json")
    Observable<HttpResult<Article>> getCollection(@Path("pageIndex") int pageIndex);

    //我的收藏取消收藏
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<HttpResult> cancelCollection(@Path("id") long id, @Field("originId") long originId);

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<HttpResult> collect(@Path("id") long id);

    //文章列表取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    Observable<HttpResult> articleCancel(@Path("id") long id);


}
