package com.tzj.http.util;

import com.tzj.http.platform.PlatformHandler;

/**
 * 为了多平台，Java，android
 */
public class UtilToast {
    private static IToast toast;

    public static void init(IToast toast) {
        UtilToast.toast = toast;
    }

    public static void showToast(String str) {
        if (PlatformHandler.isAndroid){
            toast.showToast(str);
        }else{
            System.out.println(str);
        }
    }

    public static void showToast(int res) {
        if (PlatformHandler.isAndroid){
            toast.showToast(res);
        }else{
            System.out.println(res);
        }
    }
}
