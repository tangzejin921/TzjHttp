package com.tzj.http.callback;


import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.response.HttpResponse;
import com.tzj.http.response.IResponse;
import com.tzj.http.util.ClassType;
import com.tzj.http.util.UtilJSON;
import com.tzj.http.util.UtilToast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */
public abstract class OkCallBack<T> implements IOkCallBack<T> {
    protected IPlatformHandler mHandler = PlatformHandler.getInstance();

    /**
     * 强制传 Handler 为了可以关闭
     */
    public OkCallBack(IPlatformHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public void onStart() {

    }

    @Override
    public IResponse response(Response response) throws IOException {
        HttpResponse<T> r = new HttpResponse<T>(response);
        if (r.httpCode() == 200) {
            String string = response.body().string();
            //这里 将泛型T放HttpResponse中无法得到具体类型，所以在这里都到泛型
            T t = UtilJSON.toObj(string, ClassType.genericSuperType(getClass()));
            r.setBody(t);
        }
        return r;
    }

    @Override
    public void onNoResponse(Call call, IResponse<T> response) {
        UtilToast.showToast(response.httpMsg());
    }

    @Override
    public void onResponse(Call call, IResponse<T> response) {
        if (response.httpCode() == 200) {
            onOKResponse(call, response);
        } else {
            onNoResponse(call, response);
        }
    }

    @Override
    public void onFailure(Call call, Exception e) {
        if (e != null) {
            UtilToast.showToast(e.getMessage());
        }
    }

    @Override
    public IPlatformHandler handler() {
        return mHandler;
    }
}
