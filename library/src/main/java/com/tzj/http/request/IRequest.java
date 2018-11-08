package com.tzj.http.request;

import com.tzj.http.cache.CacheType;
import com.tzj.http.callback.IHttpCallBack;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

public interface IRequest {
    /**
     * url
     */
    String url();

    /**
     * 生成表单url
     */
    String formUrl();

    /**
     * contentType
     */
    String contentType();

    /**
     * 缓存方式
     */
    CacheType cacheType();

    /**
     * mapBody
     */
    Map<String, Object> mapBody();

    /**
     * okhttp 的 body
     */
    RequestBody okBody();

    /**
     * okhttp 的 Request
     */
    Request request();

    /**
     * http 调用
     */
    void post(IHttpCallBack callBack);
}
