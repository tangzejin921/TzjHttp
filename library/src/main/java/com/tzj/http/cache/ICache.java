package com.tzj.http.cache;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public interface ICache {
    int VERSION = 201105;
    int ENTRY_COUNT = 1;
    int ENTRY_METADATA = 0;

    /**
     *
     * @param request
     * @param key 如果不为 NULL 将用 key ,不然用 请求体
     * @return
     * @throws IOException
     */
    Response get(Request request,String key) throws IOException;

    /**
     *
     * @param response
     * @param key 如果不为 NULL 将用 key ,不然用 请求体
     */
    void put(Response response,String key);

    void close();
}
