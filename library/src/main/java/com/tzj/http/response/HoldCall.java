package com.tzj.http.response;

public class HoldCall<T> {
    private T call;

    public HoldCall(T call) {
        this.call = call;
    }

    public T getCall() {
        return call;
    }
}
