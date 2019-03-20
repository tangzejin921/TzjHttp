package com.tzj.http.response;

import java.io.IOException;

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
    /**
     * 返回的外层 json 解析
     */
    <T extends IResponse> T jsonResponse () throws IOException;

}
