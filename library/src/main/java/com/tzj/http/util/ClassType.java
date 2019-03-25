package com.tzj.http.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  为了获取T
 *  注意：T 必须是具体的类型，不能是其他泛型传过来的，如 T,E 什么的
 */
public abstract class ClassType<T> {

    public Type getType() {
        return genericSuperType(getClass());
    }

    /**
     * 父类传进来的泛型
     * 如果没有返回 Object
     */
    public static Type genericSuperType(Class c) {
        Type type = c.getGenericSuperclass();
        return genericType(type);
    }

    /**
     * 得到里面的泛型
     */
    public static Type genericType(Type type){
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                return actualTypeArguments[0];
            }
        }
        return Object.class;
    }
}
