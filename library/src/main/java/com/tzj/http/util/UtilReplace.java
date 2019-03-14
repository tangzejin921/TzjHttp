package com.tzj.http.util;

import com.tzj.http.HttpApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2019 健康无忧网络科技有限公司<br>
 * Author:      唐泽金 tangzejin921@qq.com<br>
 * Version:     1.0.0<br>
 * Date:        2019/3/14 14:23<br>
 * Description: http 的入参/出产 替换字段
 */
public class UtilReplace {

    /**
     * 寻找 key 的路径
     */
    public static String findPath(String path,String key){
        try {
            String[] apis = HttpApplication.mCtx.getAssets().list(path);
            for (String s : apis) {
                if (new File(s).isDirectory()){
                    String temp = findPath(path+ File.separator+s, key);
                    if (temp != null){
                        return temp;
                    }
                }else if (s.equalsIgnoreCase(key)){
                    return path+File.separator+s;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从assets中读取json文件获取坐标点
     */
    public static String getStrFromAssets(String fileName) {
        InputStreamReader inputReader = null;
        try {
            inputReader = new InputStreamReader(HttpApplication.mCtx.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
            bufReader.close();
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }finally {
            try {
                inputReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 入参替换
     */
    public static Map<String, Object> replaceIn(Map<String, Object> map,String key){
        String api = findPath("api/in", key + ".json");
        if (api != null){
            try {
                String jsonStr = getStrFromAssets(api);
                Map<String,Object> templtate = UtilJSON.toMap(jsonStr);
                replaceMap(map,templtate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 出参替换
     */
    public static Map<String, Object> replaceOut(Map<String, Object> map,String key){
        String api = findPath("api/out", key + ".json");
        if (api != null){
            try {
                String jsonStr = getStrFromAssets(api);
                Map<String,Object> templtate = UtilJSON.toMap(jsonStr);
                replaceMap(map,templtate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 入参替换
     */
    private static void replaceMap(Map<String,Object> src,Map<String,Object> des){
        Iterator<Map.Entry<String, Object>> iterator = des.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String k = next.getKey();
            Object v = next.getValue();
            if (v instanceof String){
                src.put((String) v,src.remove(k));
            }else if (v instanceof List){
                List<Object> newList = null;
                if (k.contains(",")){
                    String[] split = k.split(",");
                    newList = (List<Object>) src.remove(split[0]);
                    src.put(split[1],newList);
                }else{
                    newList = (List<Object>) src.get(k);
                }
                Object obj = ((List) v).get(0);
                if (obj instanceof Map){
                    for (Object item : newList) {
                        replaceMap((Map<String, Object>)item,(Map<String, Object>) obj);
                    }
                }else{
                    throw new RuntimeException("没有实现的内容"+k+"："+obj.getClass());
                }
            }else if (v instanceof Map){
                Map<String,Object> newMap = null;
                if (k.contains(",")){
                    String[] split = k.split(",");
                    newMap = (Map<String, Object>) src.remove(split[0]);
                    src.put(split[1],newMap);
                }else{
                    newMap = (Map<String, Object>) src.get(k);
                }
                replaceMap(newMap,(Map<String, Object>) v);
            }else{
                throw new RuntimeException("没有实现的内容"+k+"："+v.getClass());
            }
        }
    }
}
