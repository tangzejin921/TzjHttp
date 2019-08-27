package com.tzj.http.response;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzj.http.util.UtilJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Response;

/**
 * http 返回类
 */
public class HttpResponse<B> implements IResponse<B>{
    protected Response response;
    /**
     * http返回码
     */
    protected int httpCode;
    /**
     * http返回信息
     */
    protected String httpMsg;
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回信息
     */
    protected String msg;
    /**
     *
     */
    protected B body;
    /**
     * 临时存放，
     * 用于先解析外层然后解析内层
     */
    protected Object tempBody = "{}";

    public HttpResponse() {

    }
    public HttpResponse(Response response) {
        if (response != null){
            this.response = response;
            httpCode = response.code();
            httpMsg = response.message();
        }
    }

    /**
     * http 协议的 code
     */
    @Override
    public int httpCode() {
        return httpCode;
    }

    /**
     * http 协议的 msg
     */
    @Override
    public String httpMsg() {
        return httpMsg;
    }

    /**
     * 业务的 code
     */
    @Override
    public int code() {
        return code;
    }

    @Override
    public boolean httpOk() {
        return httpCode == 200;
    }

    /**
     * 业务的
     */
    @Override
    public boolean isOk() {
        return code == 1;
    }

    /**
     * 业务的 msg
     */
    @Override
    public String msg() {
        return msg;
    }

    public void setBody(B body) {
        tempBody = null;
        response = null;
        this.body = body;
    }
    @Override
    public B body() {
        return body;
    }

    public Object tempBody(){
        return tempBody;
    }

    public void httpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public void httpMsg(String httpMsg) {
        this.httpMsg = httpMsg;
    }

    /**
     * 解析 返回的外层
     */
    @Override
    public IResponse<B> jsonResponse() throws IOException {
        if (response != null && httpOk()){
            HttpResponse<B> res = UtilJSON.toObj(response.body().string(), getClass());
            return res.copyFrom(this);
        }else{
            return this;
        }
    }
    public HttpResponse<B> copyFrom(HttpResponse<B> res){
        httpCode = res.httpCode;
        httpMsg = res.httpMsg;
        response = res.response;
        return this;
    }
    @Override
    public <T> List<T> keyList(String key, Class<T> c) {
        JSONObject jsonObject;
        if (body() instanceof JSONObject){
            jsonObject = (JSONObject) body();
        }else{
            jsonObject = JSON.parseObject(body().toString());
        }
        if(jsonObject!=null && jsonObject.containsKey(key)){
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            Iterator iterator = jsonArray.iterator();
            List<T> list = new ArrayList<>();
            while (iterator.hasNext()){
                JSONObject next = (JSONObject) iterator.next();
                T t = JSONObject.toJavaObject(next,c);
                list.add(t);
            }
            return list;
        }
        return new ArrayList();
    }

    @Override
    public String keyString(String key) {
        JSONObject temp;
        if (!(body() instanceof JSONObject)){
            temp = JSON.parseObject(body().toString());
        }else{
            temp = (JSONObject) body();
        }
        return temp.getString(key);
    }
}
