package com.tzj.http.callback;

import java.lang.reflect.Type;

/**
 * Copyright © 2019 健康无忧网络科技有限公司<br>
 * Author:      唐泽金 tangzejin921@qq.com<br>
 * Version:     1.0.0<br>
 * Date:        2019/3/20 09:36<br>
 * Description: 返回实体类型
 */
public interface IType {
    /**
     *
     * @param key
     * @param type 返回的实体类
     */
    void rspType(String key,Type type);

    /**
     * 如果 CallBack 上没有设置泛型，便取 api 的内部类 Rsp
     * 返回的实体类
     */
    Type rspType();
}
