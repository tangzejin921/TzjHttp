package com.tzj.http.platform;

/**
 * Java 情况下不分 ui 非 ui
 */
public interface IPlatformHandler {

    /**
     * UI线程
     */
    void post(Runnable runnable);

    /**
     * 非UI线程
     */
    void execute(Runnable runnable);

}
