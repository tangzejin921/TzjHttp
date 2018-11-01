package com.tzj.http.platform;

/**
 * 这个 为了在 Java 中可以调用 http 请求
 */
public class PlatformHandler {
    private static IPlatformHandler handler;
    public static boolean isAndroid;
    static {
        String property = System.getProperty("http.agent");
        if (property != null && property.contains("Android")) {
            isAndroid = true;
            handler = new AndroidHandler();
        } else {
            handler = new JavaHandler();
        }
    }

    public static void init(IPlatformHandler handler) {
        PlatformHandler.handler = handler;
    }


    public static void execute(Runnable runnable) {
        handler.execute(runnable);
    }

    public static void post(Runnable runnable) {
        handler.post(runnable);
    }
}
