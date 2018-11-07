package com.tzj.http.callback;

import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.request.IRequest;
import com.tzj.http.response.IResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 决定那个线程调用
 */
public final class ThreadCallBack implements IHttpCallBack {
    private IHttpCallBack callBack;
    private IRequest request;

    public ThreadCallBack(IRequest request,IHttpCallBack callBack) {
        this.callBack = callBack;
        this.request = request;
    }

    @Override
    public IResponse response(Response response) throws IOException {
        if (callBack != null){
            //当前线程
            return callBack.response(response);
        }else{
            return null;
        }
    }

    @Override
    public void onResponse(final Call call, final IResponse response) {
        if (callBack != null){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callBack.onResponse(call, response);
                }
            };
            if (request.handler()!=null){
                request.handler().post(runnable);
            }else{
                PlatformHandler.post(runnable);
            }
        }
    }

    @Override
    public void onFailure(final Call call, final Exception e) {
        if (callBack != null){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callBack.onFailure(call, e);
                }
            };
            if (request.handler()!=null){
                request.handler().post(runnable);
            }else{
                PlatformHandler.post(runnable);
            }
        }
    }

    @Override
    public void onFinish() {
        if (callBack != null){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callBack.onFinish();
                }
            };
            if (request.handler()!=null){
                request.handler().post(runnable);
            }else{
                PlatformHandler.post(runnable);
            }
        }
    }
}
