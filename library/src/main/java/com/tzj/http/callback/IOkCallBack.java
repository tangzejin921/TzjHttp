package com.tzj.http.callback;


import com.tzj.http.response.HoldCall;
import com.tzj.http.response.HoldResponse;

/**
 * 将onResponse 拆分 200 非200
 */
public interface IOkCallBack extends IHttpCallBack{
    /**
     * http 200
     */
    void onOKResponse(HoldCall call, HoldResponse response);
    /**
     * http 非200
     */
    void onNoResponse(HoldCall call, HoldResponse response);

}
