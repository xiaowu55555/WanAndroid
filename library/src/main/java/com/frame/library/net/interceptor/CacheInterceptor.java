package com.frame.library.net.interceptor;

import com.frame.library.Library;
import com.frame.library.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/8.
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //方案一：有网和没有网都是先读缓存
//                Request request = chain.request();
//                Log.i(TAG, "request=" + request);
//                Response response = chain.proceed(request);
//                Log.i(TAG, "response=" + response);
//
//                String cacheControl = request.cacheControl().toString();
//                if (TextUtils.isEmpty(cacheControl)) {
//                    cacheControl = "public, max-age=60";
//                }
//                return response.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();

        //方案二：无网读缓存，有网根据过期时间重新请求
        boolean netWorkConection = NetworkUtils.isNetworkAvailable(Library.getInstance().getContext().getApplicationContext());
        Request request = chain.request();
        if (!netWorkConection) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (netWorkConection) {
            // 有网络时, 不缓存, 最大保存时长为0
            int maxAge = 0;
            response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            //无网络时，读取缓存
            int maxStale = 60 * 60 * 24 * 7;
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
