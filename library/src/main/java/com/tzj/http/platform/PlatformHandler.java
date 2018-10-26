package com.tzj.http.platform;

/**
 *
 */
public class PlatformHandler {
    private static IPlatformHandler handler;

    static {
        String property = System.getProperty("http.agent");
        if (property != null && property.contains("Android")) {
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
