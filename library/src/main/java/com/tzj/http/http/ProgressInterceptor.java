package com.tzj.http.http;

import com.tzj.http.request.ProgressRequestBody;
import com.tzj.http.response.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Deprecated
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        if (!(requestBody instanceof ProgressRequestBody)) {
            requestBody = new ProgressRequestBody(requestBody, null);
        }
        request = request.newBuilder().post(requestBody).build();

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (!(responseBody instanceof ProgressResponseBody)) {
            responseBody = new ProgressResponseBody(responseBody, null);
        }
        response = response.newBuilder().body(responseBody).build();
        return response;
    }
}
