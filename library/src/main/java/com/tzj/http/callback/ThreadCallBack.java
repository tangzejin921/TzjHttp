package com.tzj.http.callback;

import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.response.IResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 决定那个线程调用
 */
public final class ThreadCallBack implements IHttpCallBack {
    private IHttpCallBack callBack;

    public ThreadCallBack(IHttpCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        if (handler() == null || handler().isClsed()) {
            callBack = null;
            return ;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callBack.onStart();
            }
        };
        callBack.handler().post(runnable);
    }

    @Override
    public IResponse response(Response response) throws IOException {
        if (handler() == null || handler().isClsed()) {
            callBack = null;
            return null;
        }
        //当前线程
        return callBack.response(response);
    }

    @Override
    public void onResponse(final Call call, final IResponse response) {
        if (handler() == null || handler().isClsed()) {
            callBack = null;
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callBack.onResponse(call, response);
            }
        };
        callBack.handler().post(runnable);
    }

    @Override
    public void onFailure(final Call call, final Exception e) {
        if (handler() == null || handler().isClsed()) {
            callBack = null;
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        };
        callBack.handler().post(runnable);
    }

    @Override
    public void onFinish() {
        if (handler() == null || handler().isClsed()) {
            callBack = null;
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callBack.onFinish();
            }
        };
        callBack.handler().post(runnable);
    }

    @Override
    public IPlatformHandler handler() {
        if (callBack == null) {
            return null;
        } else if (callBack.handler() == null) {
            return PlatformHandler.getInstance();
        } else {
            return callBack.handler();
        }
    }
}
