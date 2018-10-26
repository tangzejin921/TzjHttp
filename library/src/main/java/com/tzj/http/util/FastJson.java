package com.tzj.http.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FastJson implements IJSON {

    @Override
    public Map toMap(Object obj) {
        if (obj instanceof Map) {
            return (Map) obj;
        }
        Object o = JSON.toJSON(obj);
        if (o instanceof JSONObject) {
            return ((JSONObject) o).getInnerMap();
        }
        return new HashMap();
    }

    @Override
    public String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T toObj(String text, ClassType<T> clazz) {
        return JSON.parseObject(text, clazz.getType());
    }
}
