package com.tzj.http.http;

import com.tzj.http.cache.CacheImp;
import com.tzj.http.cache.CacheType;
import com.tzj.http.cache.ICache;
import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.callback.ThreadCallBack;
import com.tzj.http.request.IRequest;
import com.tzj.http.response.IResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 的实现
 */
public class OkHttp implements IHttp {
    private static OkHttpClient okHttpClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    public static void init(OkHttpClient okHttpClient) {
        OkHttp.okHttpClient = okHttpClient;
    }

    @Override
    public void post(IRequest iRequest, IHttpCallBack callBack) {
        Call call = null;
        ICache cache = null;
        Response cacheResponse = null;
        Response response = null;
        callBack = new ThreadCallBack(callBack);
        callBack.onStart();
        try {
            String methed = iRequest.methed();
            //返回类型放到 callBack 里
            callBack.rspType(methed,iRequest.rspType());
            Request request = iRequest.request();
            call = okHttpClient.newCall(request);
            cache = new CacheImp(request.url(),methed);
            //返回缓存
            if (iRequest.cacheType() == CacheType.ONLY_USER_CACHE ||
                    iRequest.cacheType() == CacheType.USER_CACHE ||
                    iRequest.cacheType() == CacheType.USER_CACHE_AND_RESPONSE) {
                try {
                    cacheResponse = cache.get(request,iRequest.cacheKey());
                    if (cacheResponse != null) {
                        //构建返回体
                        IResponse iResponse = callBack.response(cacheResponse);
                        //调用返回
                        callBack.onResponse(call, iResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //网络请求
            if (iRequest.cacheType() == CacheType.DEFAULT ||
                    (iRequest.cacheType() == CacheType.USER_CACHE && cacheResponse == null) ||
                    iRequest.cacheType() == CacheType.USER_CACHE_AND_RESPONSE) {
                response = call.execute();
                //更新缓存
                if (iRequest.cacheType() == CacheType.USER_CACHE ||
                        iRequest.cacheType() == CacheType.USER_CACHE_AND_RESPONSE) {
                    cache.put(response,iRequest.cacheKey());
                }
                //构建返回体
                IResponse iResponse = callBack.response(response);
                //调用返回
                callBack.onResponse(call, iResponse);
            }

        } catch (Exception e) {
            //异常
            callBack.onFailure(call, e);
        } finally {
            if (cacheResponse != null) {
                cacheResponse.close();
            }
            if (response != null) {
                response.close();
            }
            if (cache != null) {
                cache.close();
            }
            //结束
            callBack.onFinish();
        }
    }
}
