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
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 区分 http 200 的情况
 */
public abstract class OkCallBack<T> implements IOkCallBack<T>, IType {
    protected IPlatformHandler mHandler = PlatformHandler.getInstance();
    protected Type type;
    protected String key;
    /**
     * 强制传 Handler 为了可以关闭
     */
    public OkCallBack(IPlatformHandler handler) {
        if (handler != null) {
            this.mHandler = handler;
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void setRspType(String key, Type type) {
        this.key = key;
        this.type = type;
    }

    @Override
    public Type getRspType() {
        //这里 将泛型T放HttpResponse中无法得到具体类型，所以在这里得到泛型
        Type rspType = ClassType.genericSuperType(getClass());
        if (rspType == Object.class) {
            rspType = type;
        }
        return rspType;
    }

    @Override
    public IResponse response(Response response) throws IOException {
        return new HttpResponse<T>(response);
    }



    @Override
    public void onNoResponse(Call call, IResponse<T> response) {
        UtilToast.showToast(response.httpMsg());
    }

    @Override
    public void onResponse(Call call, IResponse<T> response) {
        if (response.httpCode() == 200) {
            response = response.jsonResponse();
            response = fillBody(response);
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

    /**
     * 填充 body
     */
    protected IResponse fillBody(IResponse<T> r){
        if (r instanceof HttpResponse){
            HttpResponse res = (HttpResponse) r;
            Map map = UtilJSON.toMap(res.tempBody().toString());
            map = UtilReplace.replaceOut(map, key);
            // FIXME: 2019/3/14 这里string->map->string->clss 没找到方法多转了一次
            String s = JSON.toJSONString(map);
            T body = UtilJSON.toObj(s, getRspType());
            res.setBody(body);
        }
        return r;
    }
}
