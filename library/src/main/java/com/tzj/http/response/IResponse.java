package com.tzj.http.response;

import java.io.IOException;
import java.util.List;

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
     * http情况
     */
    boolean httpOk();

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
    IResponse<B> jsonResponse() throws IOException;

    /**
     * 当没有具体实体类时
     * 取返回的集合
     */
    <T> List<T> keyList(String key, Class<T> c);

    /**
     * 当没有具体实体类时
     * 取返回中的 key
     */
    String keyString(String key);
}
