# http 调用

### 目标
* test/Java 可以直接调用
* 加缓存
* 拒绝代理
* 支持 https 
* Activity 关闭后不会调用回调

###  test/Java 可以直接调用
判断是否android 平台,决定是否用 handler
```java
class Temp{
    temp(){
        String property = System.getProperty("http.agent");
        if (property != null && property.contains("Android")) {
            isAndroid = true;
        }
    }
}
```
### 加缓存

### 拒绝代理
okhttp 的话 加
proxy(Proxy.NO_PROXY)
### 支持 https 

