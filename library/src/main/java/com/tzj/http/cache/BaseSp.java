package com.tzj.http.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.tzj.http.HttpApplication;
import com.tzj.http.util.UtilJSON;

/**
 * Copyright © 2019 健康无忧网络科技有限公司<br>
 * Author:      唐泽金 tangzejin921@qq.com<br>
 * Version:     1.0.0<br>
 * Date:        2019/4/11 10:38<br>
 * Description: SharedPreferences
 */
public abstract class BaseSp {
    /**
     * 文件名
     * 默认 package 路径
     */
    public String fileName(){
        return getClass().getPackage().toString();
    }

    /**
     * SharedPreferences 存放的 key
     */
    public String spName(){
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return UtilJSON.toJson(this);
    }

    public void save(){
        SharedPreferences sp = HttpApplication.mCtx.getSharedPreferences(fileName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(spName(),toString());
        edit.commit();
    }

    public <T extends BaseSp> T read(){
        SharedPreferences sp = HttpApplication.mCtx.getSharedPreferences(fileName(), Context.MODE_PRIVATE);
        String str = sp.getString(spName(), null);
        if(str!=null){
            return (T) JSON.parseObject(str, getClass());
        }
        try {
            return (T) getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("请给个无参构造方法");
        }
    }

}
