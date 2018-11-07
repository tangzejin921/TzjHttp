package com.tzj.http.cache;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public interface ICache {
    int VERSION = 201105;
    int ENTRY_COUNT = 1;
    int ENTRY_METADATA = 0;
    Response get(Request request) throws IOException;

    void put(Response response);

    void close();
}
