package com.tzj.http.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Cookie 静态保存
 */
public class StaticCookie implements CookieJar {
    public static final Map<String,List<Cookie>> cookies = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> list) {
        List<Cookie> temp = cookies.get(url.host());
        if (temp == null){
            temp = new ArrayList<>();
        }
        if (list != null){
            temp.addAll(list);
        }
        cookies.put(url.host(),temp);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> list = cookies.get(url.host());
        if (list == null){
            list = new ArrayList<>();
            cookies.put(url.host(),list);
        }
        return list;
    }
}
