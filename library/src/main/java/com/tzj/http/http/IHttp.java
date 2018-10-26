package com.tzj.http.http;


import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.request.IRequest;

/**
 * get 可以被 post 替代
 */
public interface IHttp {
    /**
     * post 调用
     */
    void post(IRequest request,IHttpCallBack callBack);

}
