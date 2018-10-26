package com.tzj.http.util;


import java.util.Map;

public class UtilJSON {
    private static IJSON utilJSON = new FastJson();

    public static void init(IJSON utilJSON) {
        UtilJSON.utilJSON = utilJSON;
    }

    public static Map toMap(Object obj) {
        return utilJSON.toMap(obj);
    }

    public static String toJson(Object obj) {
        return utilJSON.toJson(obj);
    }

    public static <T> T toObj(String json, ClassType<T> clazz) {
        return utilJSON.toObj(json,clazz);
    }
}
