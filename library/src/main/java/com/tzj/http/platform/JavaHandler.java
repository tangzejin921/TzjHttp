package com.tzj.http.platform;


public class JavaHandler implements IPlatformHandler{
    protected boolean closed = false;

    public boolean isClsed() {
        return closed;
    }

    @Override
    public void close(boolean b) {
        closed = b;
    }

    @Override
    public boolean execute(Runnable r) {
        if (!closed){
            r.run();
        }
        return !closed;
    }

    @Override
    public boolean post(Runnable r) {
        if (!closed){
            r.run();
        }
        return !closed;
    }

    @Override
    public boolean postDelayed(Runnable r, final long delayMillis) {
        if (!closed){
            //todo  这里可以用线程池,现在只是简单实现
            new Thread(r){
                @Override
                public void run() {
                    try {
                        sleep(delayMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();
        }
        return !closed;
    }
}
