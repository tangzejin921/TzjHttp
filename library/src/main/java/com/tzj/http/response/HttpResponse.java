package com.tzj.http.response;

/**
 * http 返回类
 */
public class HttpResponse<T> {
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
    protected T body;

    /**
     * http 协议的 code
     */
    public int httpCode() {
        return httpCode;
    }

    /**
     * http 协议的 msg
     */
    public String httpMsg() {
        return httpMsg;
    }

    /**
     * 业务的 code
     */
    public int code() {
        return code;
    }

    /**
     * 业务的 msg
     */
    public String msg() {
        return msg;
    }

    public T body() {
        return body;
    }

}
