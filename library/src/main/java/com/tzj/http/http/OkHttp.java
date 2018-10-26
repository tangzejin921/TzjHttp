package com.tzj.http.http;

import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.request.IOkRequest;
import com.tzj.http.request.IRequest;
import com.tzj.http.response.HoldCall;
import com.tzj.http.response.HoldResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp implements IHttp {
    private static OkHttpClient okHttpClient;
    static {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    public static void init(OkHttpClient okHttpClient){
        OkHttp.okHttpClient = okHttpClient;
    }

    @Override
    public void post(IRequest request,IHttpCallBack callBack) {
        IOkRequest okRequest = (IOkRequest) request;
        Response response = null;
        HoldCall holdCall = null;
        try {
            Call call = okHttpClient.newCall(
                    new Request.Builder()
                            .url(okRequest.url())
                            .post(okRequest.okBody())
                            .build());
            holdCall = new HoldCall(call);
            response = call.execute();
            if (callBack!=null){
                callBack.onResponse(holdCall, new HoldResponse(response));
            }
        } catch (Exception e) {
            if (callBack!=null){
                callBack.onFailure(holdCall, e);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            if (callBack!=null){
                callBack.onFinish();
            }
        }
    }
}
