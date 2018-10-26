package com.tzj.http.callback;

import com.tzj.http.response.HoldCall;
import com.tzj.http.response.HoldResponse;

/**
 * onOKResponse 按照业务再拆分为,
 * 成功，
 * 失败，
 * 没权限
 */
public interface ICallBack extends IOkCallBack{

    /**
     * 业务成功
     */
    void onSuccess(HoldCall call, HoldResponse response);
    /**
     * 业务失败
     */
    void onErr(HoldCall call, HoldResponse response);
    /**
     * 没权限，比如没登录
     */
    void onPermission(HoldCall call, HoldResponse response);
}
