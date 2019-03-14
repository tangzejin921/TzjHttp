package com.tzj.http.callback;


import com.alibaba.fastjson.JSON;
import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.response.HttpResponse;
import com.tzj.http.response.IResponse;
import com.tzj.http.util.ClassType;
import com.tzj.http.util.UtilJSON;
import com.tzj.http.util.UtilReplace;
import com.tzj.http.util.UtilToast;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 区分 http 200 的情况
 */
public abstract class OkCallBack<T> implements IOkCallBack<T> {
    protected IPlatformHandler mHandler = PlatformHandler.getInstance();

    /**
     * 强制传 Handler 为了可以关闭
     */
    public OkCallBack(IPlatformHandler handler) {
        if (handler != null){
            this.mHandler = handler;
        }
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
            Map map = UtilJSON.toMap(string);
            map = UtilReplace.replaceOut(map,getClass().getSimpleName());
            String s = JSON.toJSONString(map);// FIXME: 2019/3/14 这里string->map->string->clss 没找到方法多转了一次
            T t = UtilJSON.toObj(s, ClassType.genericSuperType(getClass()));
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
