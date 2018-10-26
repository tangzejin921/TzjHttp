//package com.tzj.http.http;
//
//import com.tzj.http.response.HoldCall;
//import com.tzj.http.response.HoldResponse;
//import com.tzj.http.callback.IHttpCallBack;
//
//public interface ICacheHttp extends IHttp {
//
//    /**
//     * 返回将缓存,
//     * 并且会先调用缓存，然后调用 {@link IHttpCallBack#onResponse(HoldCall, HoldResponse)},失败将不会调用
//     */
//    IHttp cache();
//
//}
