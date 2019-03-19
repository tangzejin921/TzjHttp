package com.tzj.http.platform;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class AndroidHandler extends Handler implements IPlatformHandler {

    protected boolean closed = false;

    @Override
    public boolean isClsed() {
        return closed;
    }

    @Override
    public void close(boolean b) {
        closed = b;
        if (closed) {
            removeMessages(0);
        }
    }

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        if (closed) {
            return false;
        }
        return super.sendMessageAtTime(msg, uptimeMillis);
    }

    @Override
    public boolean execute(final Runnable runnable,long delayMillis) {
        if (!closed) {
            if (delayMillis > 0){
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTask.THREAD_POOL_EXECUTOR.execute(runnable);
                    }
                },delayMillis);
            }else{
                AsyncTask.THREAD_POOL_EXECUTOR.execute(runnable);
            }
        }
        return !closed;
    }
}
