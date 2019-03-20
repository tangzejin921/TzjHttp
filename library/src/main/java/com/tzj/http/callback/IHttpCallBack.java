package com.tzj.http.callback;



import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.response.IResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 进一步将http 200 分为业务成功,失败
 */
public interface IHttpCallBack<T> extends IType{
    /**
     * 开始调用
     */
    void onStart();
    /**
     * 创建 IResponse
     */
    IResponse response(Response response) throws IOException;
    /**
     * http 成功返回
     */
    void onResponse(Call call, IResponse<T> response);

    /**
     * http 异常返回
     */
    void onFailure(Call call, Exception e);

    /**
     * 无论成功失败,最后调用的
     */
    void onFinish();

    IPlatformHandler handler();
}
