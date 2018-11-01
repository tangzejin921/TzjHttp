package com.tzj.http.http;

import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.callback.ThreadCallBack;
import com.tzj.http.request.IRequest;
import com.tzj.http.response.IResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 的实现
 */
public class OkHttp implements IHttp {
    private static OkHttpClient okHttpClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    public static void init(OkHttpClient okHttpClient) {
        OkHttp.okHttpClient = okHttpClient;
    }

    @Override
    public void post(IRequest request, IHttpCallBack callBack) {
        Response response = null;
        Call call = null;
        callBack = new ThreadCallBack(request, callBack);
        try {
            call = okHttpClient.newCall(
                    new Request.Builder()
                            .url(request.url())
                            .post(request.okBody())
                            .build());
            response = call.execute();
            //构建返回体
            IResponse iResponse = callBack.response(response);
            //调用返回
            callBack.onResponse(call, iResponse);
        } catch (Exception e) {
            //异常
            callBack.onFailure(call, e);
        } finally {
            if (response != null) {
                response.close();
            }
            //结束
            callBack.onFinish();
        }
    }
}
