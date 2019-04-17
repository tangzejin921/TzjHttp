package com.tzj.http.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * fastJosn的实现
 */
public class FastJson implements IJSON {

    @Override
    public Map toMap(Object obj) {
        if (obj instanceof String){
            obj = JSON.parse((String) obj);
        }
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
    public List toList(Object obj) {
        if (obj instanceof String){
            obj = JSON.parse((String) obj);
        }
        if (obj instanceof List) {
            return (List) obj;
        }
        Object o = JSON.toJSON(obj);
        if (o instanceof JSONArray){
            return ((JSONArray)o).subList(0,((JSONArray) o).size()-1);
        }
        return new ArrayList();
    }

    @Override
    public String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T toObj(String text, ClassType<T> clazz) {
        return JSON.parseObject(text, clazz.getType());
    }

    @Override
    public <T> T toObj(String text, Type type) {
        return JSON.parseObject(text, type);
    }
}
