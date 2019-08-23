package com.tzj.http.callback;


import com.alibaba.fastjson.JSONArray;
import com.tzj.http.HttpApplication;
import com.tzj.http.R;
import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.response.HttpResponse;
import com.tzj.http.response.IListKey;
import com.tzj.http.response.IResponse;
import com.tzj.http.util.ClassType;
import com.tzj.http.util.UtilJSON;
import com.tzj.http.util.UtilReplace;
import com.tzj.http.util.UtilToast;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 区分 http 200 的情况
 */
public abstract class OkCallBack<T> implements IOkCallBack<T>, IType {
    protected IPlatformHandler mHandler = PlatformHandler.getInstance();
    protected Type type;
    /**
     * methed,请求方法
     */
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

    /**
     * 将请求的静态内部类Rsp传进来
     */
    @Override
    public void rspType(String key, Type type) {
        this.key = key;
        this.type = type;
    }

    @Override
    public Type rspType() {
        //这里 将泛型T放HttpResponse中无法得到具体类型，所以在这里得到泛型
        Type rspType = ClassType.genericSuperType(getClass());
        if (rspType == Object.class) {
            rspType = type;
        }
        return rspType;
    }

    @Override
    public IResponse response(Response response) throws IOException {
        IResponse<T> httpResponse = new HttpResponse<>(response);
        //解析外层
        httpResponse = fillHttp(httpResponse);
        //解析内层数据
        httpResponse = fillBody(httpResponse);
        return httpResponse;
    }

    @Override
    public void onPreResponse(Call call, IResponse<T> response) {

    }

    @Override
    public void onResponse(Call call, IResponse<T> response) {
        if (response.httpOk()) {
            onOKResponse(call, response);
        } else {
            onNoResponse(call, response);
        }
    }

    @Override
    public void onNoResponse(Call call, IResponse<T> response) {
        UtilToast.showToast(response.httpMsg());
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
     * 解析外层
     */
    protected IResponse fillHttp(IResponse<T> r) throws IOException {
        //网络成功情况下
        if (r.httpOk()){
            //解析外层
             r = r.jsonResponse();
        }
        return r;
    }
    /**
     * 填充 body
     */
    protected IResponse fillBody(IResponse<T> r){
        if (r instanceof HttpResponse && r.isOk()){
            HttpResponse res = (HttpResponse) r;

            Object object = "{}";
            Type type = rspType();
            if (res.tempBody() != null && res.tempBody().toString().startsWith("[")){
                List<Map> list = UtilJSON.toList(res.tempBody().toString());
                if (HttpApplication.mCtx.getResources().getBoolean(R.bool.http_change_response)){
                    object = UtilReplace.replaceOut(list, key);
                }else{
                    object = list;
                }
            }else{
                Map map = UtilJSON.toMap(res.tempBody().toString());
                object = UtilReplace.replaceOut(map, key);
                //map 只有一个key 对应到 list 的情况
                try {
                    //是List
                    if (type instanceof ParameterizedType && ((ParameterizedType)type).getRawType() == List.class){
                        Type itemType = ClassType.genericType(type);
                        //如果item实现了接口 IListKey 将会取出其内容
                        if (IListKey.class.isAssignableFrom((Class<?>) itemType)){
                            IListKey o = ((Class<IListKey>) itemType).newInstance();
                            Object value = map.get(o.listKeyName());
                            if (value != null){
                                object = value;
                            }else{
                                object = new JSONArray();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // FIXME: 2019/3/14 这里string->map->string->clss 没找到方法多转了一次
            String s = UtilJSON.toJson(object);
            T body = UtilJSON.toObj(s, type);
            res.setBody(body);
        }
        return r;
    }
}
