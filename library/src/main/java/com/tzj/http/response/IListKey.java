package com.tzj.http.response;

/**
 * Copyright © 2019 健康无忧网络科技有限公司<br>
 * Author:      唐泽金 tangzejin921@qq.com<br>
 * Version:     1.0.0<br>
 * Date:        2019/3/25 14:37<br>
 * Description: 接口返回一个结合但是放到了一个字段里，为了方便起见加了这个
 */
public interface IListKey {

    /**
     * @return 返回体为list时的key
     */
    String listKeyName();
}
