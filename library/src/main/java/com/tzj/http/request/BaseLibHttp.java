package com.tzj.http.request;


import com.tzj.http.cache.CacheType;
import com.tzj.http.callback.IHttpCallBack;
import com.tzj.http.http.IHttp;
import com.tzj.http.http.OkHttp;
import com.tzj.http.platform.IPlatformHandler;
import com.tzj.http.platform.PlatformHandler;
import com.tzj.http.util.UtilJSON;
import com.tzj.http.util.UtilReplace;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BaseLibHttp implements IRequest {
    private static IHttp http = new OkHttp();

    public static void init(IHttp http) {
        BaseLibHttp.http = http;
    }

    protected CacheType cacheType = CacheType.DEFAULT;

    public BaseLibHttp cacheType(CacheType type) {
        this.cacheType = type;
        return this;
    }

    @Override
    public String methed() {
        String temp = getClass().getSimpleName().split("\\$")[0];
        temp = temp.replaceFirst(temp.charAt(0)+"",(temp.charAt(0)+"").toLowerCase());
        //方法替换
        String str = UtilReplace.getStrFromAssets("api/methed.json");
        Map map = UtilJSON.toMap(str);
        str = (String) map.get(temp);
        if (str != null){
            temp = str;
        }
        return temp;
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public String formUrl() {
        Map<String, Object> map = mapBody();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        StringBuffer sb = new StringBuffer(url()).append("?");
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            sb.append(next.getKey()).append("=").append(next.getValue()).append("&");
        }
        return sb.toString();
    }

    @Override
    public String contentType() {
        return "text/plain; charset=utf-8";
    }

    @Override
    public CacheType cacheType() {
        return cacheType;
    }

    @Override
    public Map<String, Object> mapBody() {
        return UtilJSON.toMap(this);
    }

    @Override
    public Map<String, Object> replaceMap(Map<String, Object> map){
        return UtilReplace.replaceIn(map,methed());
    }

    @Override
    public String cacheKey() {
        return null;
    }

    @Override
    public RequestBody okBody() {
        MediaType parse = MediaType.parse(contentType());
        String s = UtilJSON.toJson(replaceMap(mapBody()));
        if (UtilJSON.NULLJSON.equals(s)) {
            s = "";
        }
        return RequestBody.create(parse, s);
    }

    @Override
    public Request request() {
        return new Request.Builder()
                .url(url())
                .post(okBody())
                .build();
    }

    @Override
    public void post(final IHttpCallBack callBack) {
        postDelay(callBack,0);
    }

    @Override
    public void postDelay(final IHttpCallBack callBack,int time) {
        final IRequest request = this;
        IPlatformHandler handler = null;
        if (callBack == null) {
            handler = PlatformHandler.getInstance();
        }else{
            handler = callBack.handler();
        }
        handler.execute(new Runnable() {
            @Override
            public void run() {
                http.post(request, callBack);
            }
        },time);
    }

    @Override
    public Type rspType() {
        Class<?>[] declaredClasses = getClass().getDeclaredClasses();
        if (declaredClasses != null){
            for (Class c: declaredClasses) {
                int mod = c.getModifiers();
                if ((Modifier.STATIC & mod) > 0 && c.getSimpleName().endsWith("Rsp")) {
                    return c;
                }
            }
        }
        return null;
    }
}
