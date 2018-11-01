package com.tzj.http.request;

import android.os.Handler;

import com.tzj.http.callback.IHttpCallBack;

import java.util.Map;

import okhttp3.RequestBody;

public interface IRequest {
    /**
     * 回调用的 handler
     */
    Handler handler();
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
     * mapBody
     */
    Map<String,Object> mapBody();
    /**
     * okhttp 的 body
     */
    RequestBody okBody();

    /**
     * http 调用
     */
    void post(IHttpCallBack callBack);
}
