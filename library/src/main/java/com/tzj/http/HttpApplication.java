package com.tzj.http;

import android.app.Application;
import android.content.Context;

public class HttpApplication extends Application {

    public static Context mCtx;

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    public static void init(Context ctx){
        mCtx = ctx;
    }
}
