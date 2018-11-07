package com.tzj.http.cache;

public enum  CacheType {
    /**
     * 不缓存/只用网络 (一般情况)
     */
    DEFAULT,
    /**
     * 用缓存,没有就用网络(如图片)
     */
    USER_CACHE,
    /**
     * 只用缓存,没有就什么也不干(如疯狂请求下拉)
     */
    ONLY_USER_CACHE,
    /**
     * 用缓存，并且用网络（返回两次,如列表刚进去看到上次的）
     */
    USER_CACHE_AND_RESPONSE
}
