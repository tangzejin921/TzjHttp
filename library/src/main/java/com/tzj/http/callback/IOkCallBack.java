package com.tzj.http.callback;


import com.tzj.http.response.IResponse;

import okhttp3.Call;

/**
 * 将onResponse 拆分 200 非200
 */
public interface IOkCallBack<T> extends IHttpCallBack<T>{
    /**
     * http 200
     * 拆分至onResponse
     */
    void onOKResponse(Call call, IResponse<T> response);
    /**
     * http 非200
     * 拆分至onResponse
     */
    void onNoResponse(Call call, IResponse<T> response);

}
