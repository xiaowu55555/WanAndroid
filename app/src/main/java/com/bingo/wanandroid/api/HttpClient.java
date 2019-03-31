package com.bingo.wanandroid.api;

import com.bingo.wanandroid.api.cookie.CookieJarImpl;
import com.bingo.wanandroid.api.cookie.SPCookieStore;
import com.bingo.wanandroid.app.App;
import com.frame.library.net.interceptor.HttpInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final int DEFAULT_TIME = 10;
    private static HttpClient mInstance;

    public static HttpClient getInstance() {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) {
                    mInstance = new HttpClient();
                }
            }
        }
        return mInstance;
    }

    private Retrofit getRetrofit() {
//        CacheInterceptor cacheInterceptor = new CacheInterceptor();
//        File httpCacheDirectory = new File(Library.getInstance().getContext().getCacheDir(), "HttpCache");
//        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
//                .addNetworkInterceptor(cacheInterceptor)
//                .addInterceptor(cacheInterceptor)
//                .cache(cache)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieJarImpl(new SPCookieStore(App.getInstance())));

        return new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService getService() {
        return getRetrofit().create(ApiService.class);
    }
}
