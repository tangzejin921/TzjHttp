package com.tzj.http.http;


import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.request.IRequest;


public class HttpImp implements IHttp {
//    protected Handler mHandler;
//
//    /**
//     * 将用这个handler 返回 UI线程
//     * 为了可以关闭界面时清除回调
//     */
//    public void handler(Handler handler){
//        mHandler = handler;
//    }

    private static IHttp http = new OkHttp();

    public static void init(IHttp http) {
        HttpImp.http = http;
    }


    @Override
    public void post(final IRequest request, final IHttpCallBack callBack) {
        PlatformHandler.execute(new Runnable() {
            @Override
            public void run() {
                http.post(request,callBack);
            }
        });
    }
}
