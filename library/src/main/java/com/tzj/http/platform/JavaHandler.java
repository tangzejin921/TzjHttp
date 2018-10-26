package com.tzj.http.platform;

public class JavaHandler implements IPlatformHandler{
    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
