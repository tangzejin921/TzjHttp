package com.tzj.http.util;

import java.util.Map;

public interface IJSON {

    Map toMap(Object obj);

    String toJson(Object obj);

    <T> T toObj(String json, ClassType<T> clazz);
}
