package com.tzj.http.response;

public class HoldResponse<T> {
    private T response;

    public HoldResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }
}
