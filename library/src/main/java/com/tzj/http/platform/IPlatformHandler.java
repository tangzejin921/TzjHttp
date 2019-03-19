package com.tzj.http.platform;

/**
 * 1.android 情况下 分 ui 非 ui
 * 2.可关闭
 */
public interface IPlatformHandler {
    /**
     * 非UI线程
     */
    boolean execute(Runnable r,long delayMillis);
    /**
     * UI线程
     */
    boolean post(Runnable r);
    boolean postDelayed(Runnable r, long delayMillis);
    boolean isClsed();

    /**
     * @param b 是否关闭
     */
    void close(boolean b);
}
