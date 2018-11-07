package com.tzj.http.platform;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

public class AndroidHandler implements IPlatformHandler {

    /**
     * 通用ui回调
     */
    public static Handler mHandler = new Handler(Looper.getMainLooper());
    ;

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    @Override
    public void execute(Runnable runnable) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(runnable);
    }
}
