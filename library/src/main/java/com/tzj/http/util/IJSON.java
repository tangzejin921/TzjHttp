package com.tzj.http.util;

import java.lang.reflect.Type;
import java.util.Map;

public interface IJSON {

    Map toMap(Object obj);

    String toJson(Object obj);

    <T> T toObj(String text, Type type);

    <T> T toObj(String json, ClassType<T> clazz);

}
