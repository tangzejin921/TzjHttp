package com.tzj.http.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  为了获取T
 */
public abstract class ClassType<T> {

    public Type getType() {
        return genericSuperType(getClass());
    }

    /**
     * 父类传进来的泛型
     */
    public static Type genericSuperType(Class c) {
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                type = actualTypeArguments[0];
            }
        }
        return type;
    }
}
