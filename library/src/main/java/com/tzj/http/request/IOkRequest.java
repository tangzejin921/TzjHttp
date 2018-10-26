package com.tzj.http.request;



import okhttp3.RequestBody;

/**
 * 用于生成  okHttp 的请求体
 */
public interface IOkRequest extends IRequest {
    /**
     * okhttp 的 body
     */
    RequestBody okBody();

}
