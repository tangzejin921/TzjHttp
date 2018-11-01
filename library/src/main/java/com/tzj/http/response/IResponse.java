package com.tzj.http.response;

public interface IResponse<B> {

    /**
     * http 协议的 code
     */
    int httpCode();

    /**
     * http 协议的 msg
     */
    String httpMsg();

    /**
     * 业务的 code
     */
    int code();

    /**
     * 业务的
     */
    boolean isOk();

    /**
     * 业务的 msg
     */
    String msg();

    /**
     * body 体实例
     */
    B body();

}
