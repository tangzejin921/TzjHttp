package com.tzj.http.callback;


import com.tzj.http.response.IResponse;

import okhttp3.Call;

/**
 * onOKResponse 按照业务再拆分为,
 * 成功，
 * 失败，
 * 没权限
 */
public interface ICallBack<T> extends IOkCallBack<T>{

    /**
     * 拆分至 onOKResponse
     * 业务成功
     */
    void onSuccess(Call call, IResponse<T> response);
    /**
     * 拆分至 onOKResponse
     * 业务失败
     */
    void onErr(Call call, IResponse<T> response);
    /**
     * 拆分至 onOKResponse
     * 没权限，比如没登录
     */
    void onPermission(Call call, IResponse<T> response);
}
