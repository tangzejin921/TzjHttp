package com.tzj.http.request;


import com.tzj.http.cache.CacheType;
import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.http.IHttp;
import com.tzj.http.http.OkHttp;
import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.util.UtilJSON;

import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BaseLibHttp implements IRequest {
    private static IHttp http = new OkHttp();

    public static void init(IHttp http) {
        BaseLibHttp.http = http;
    }

    protected CacheType cacheType = CacheType.DEFAULT;

    public BaseLibHttp cacheType(CacheType type) {
        this.cacheType = type;
        return this;
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public String formUrl() {
        Map<String, Object> map = mapBody();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        StringBuffer sb = new StringBuffer(url()).append("?");
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            sb.append(next.getKey()).append("=").append(next.getValue()).append("&");
        }
        return sb.toString();
    }

    @Override
    public String contentType() {
        return "text/plain; charset=utf-8";
    }

    @Override
    public CacheType cacheType() {
        return cacheType;
    }

    @Override
    public Map<String, Object> mapBody() {
        return UtilJSON.toMap(this);
    }

    @Override
    public RequestBody okBody() {
        MediaType parse = MediaType.parse(contentType());
        String s = UtilJSON.toJson(this);
        if (UtilJSON.NULLJSON.equals(s)) {
            s = "";
        }
        return RequestBody.create(parse, s);
    }

    @Override
    public Request request() {
        return new Request.Builder()
                .url(url())
                .post(okBody())
                .build();
    }

    @Override
    public void post(final IHttpCallBack callBack) {
        postDelay(callBack,0);
    }

    @Override
    public void postDelay(final IHttpCallBack callBack,int time) {
        final IRequest request = this;
        IPlatformHandler handler = null;
        if (callBack == null) {
            handler = PlatformHandler.getInstance();
        }else{
            handler = callBack.handler();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                http.post(request, callBack);
            }
        },time);
    }
}
