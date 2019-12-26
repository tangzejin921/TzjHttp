# http 调用
### 基本要求
- 面向对象，请求以对象方式呈现
- 平台化，可以java环境下快速测试
- 无感知的构造请求体，比如请求从xml方式改为json方式对调用而言无感知。
- 支持依据请求对返回修改，有些情况狗比后台不配合。
- 支持不同缓存策略，图片策略(有就用缓存)，新闻策略(先取缓存后取网络同时进行)
- 请求嵌套，比如请求发现登陆过期->调用登陆->接着调用原来的接口，这一过程对外部是一整体只是一个接口。
- 支持多请求单返回
- 支持清除回调，当Activity 消失后接口返回情况，Activity 消失时可以取消请求/清除回调
- ~~请求体，返回体模板化。可以根据模板修改他们~~



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

