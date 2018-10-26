package com.tzj.http.callback;


import com.tzj.http.response.HoldCall;
import com.tzj.http.response.HoldResponse;

public interface IHttpCallBack {
    /**
     * http 成功返回
     */
    void onResponse(HoldCall call, HoldResponse response);

    /**
     * http 异常返回
     */
    void onFailure(HoldCall call, Exception e);

    /**
     * 无论成功失败,最后调用的
     */
    void onFinish();

}
