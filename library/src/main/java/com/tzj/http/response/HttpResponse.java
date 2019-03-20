package com.tzj.http.response;


import com.tzj.http.util.UtilJSON;

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
        this.body = body;
    }
    @Override
    public B body() {
        return body;
    }

    public Object tempBody(){
        return tempBody;
    }

    /**
     * 解析 返回的外层
     */
    @Override
    public <T extends IResponse> T jsonResponse(){
        if (response != null){
            HttpResponse res = UtilJSON.toObj(response.body().toString(), getClass());
            res.httpCode = httpCode;
            res.httpMsg = httpMsg;
        }
        return null;
    }
}
