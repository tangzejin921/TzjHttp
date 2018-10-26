package com.tzj.http.request;

import java.util.Map;

public interface IRequest {
    /**
     * url
     */
    String url();

    /**
     * 生成表单url
     */
    String formUrl();

    /**
     * contentType
     */
    String contentType();

    /**
     * mapBody
     */
    Map<String,Object> mapBody();

}
