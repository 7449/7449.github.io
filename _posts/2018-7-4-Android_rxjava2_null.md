---
layout:     post
title:      "RxJava2:onNext不能为null的几个解决办法"
subtitle:   "Null is not a valid element"
date:       2018-7-4
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - rxjava
---

## 示例

[SampleRxJava2Null](https://github.com/7449/AndroidDevelop/blob/develop/AppModules/app/src/main/java/com/sample/SampleRxJava2Null.java)

## 前言

`Rxjava2`之后`onNext`，不能传`null`,否则会报一个`Null is not a valid element`的错误，但是在某些情况下(例如某些`delete`或者`post`网络请求)`data`
返回的`null`,这个时候就会报异常，这里提出了几个解决办法

## 最简单的办法

如果`code`是正常的，但是`data`为`null`，自定义一个异常，返回一个规定好的信息，然后在`onError`里面根据异常内容判断
这个是最简单的办法，但是不是很推荐，毕竟要多一层判断，很麻烦

接下来这个办法比较推荐

## 基本的Entity

一般来说，网络请求都会有`code`和`message`,`data`

示例如下：

     public class BaseEntity<T> {
        private int code;
        private String message;
        private T data;
    }


## rxjava请求网络

一般都是封装一个`BaseEntity`,然后使用泛型去简化网络请求

#### 基本示例

   
    interface Server {
         @GET("http://api.codekk.com/op/page/" + "{page}")
         Observable<SampleEntity<Entity>> test(@Path("page") int page);
     }

然后使用`Rxjava`请求网络

这里使用了[RxNetWork](https://github.com/7449/RxNetWork)

       RxNetWork
                .getInstance()
                .getApi(RxNetWork.observable(Server.class).test(1), new RxNetWorkListener<SampleEntity<Entity>>() {
                    @Override
                    public void onNetWorkSuccess(SampleEntity<Entity> data) {
                    }
                });

为了统一判断`code`,也可以加一个`map`或者其他操作符

    private static class SampleFun<T> implements Function<SampleEntity<T>, T> {

        @Override
        public T apply(SampleEntity<T> entity) {
            // 这里判断code，如果不对直接返回Error即可
            return entity.getData();
        }
    }

那这样使用就是

    Observable<Entity> map = RxNetWork.observable(Server.class).test(1).map(new SampleFun<>());
    RxNetWork
             .getInstance()
             .getApi(map, new RxNetWorkListener<Entity>() {
                 @Override
                 public void onNetWorkSuccess(Entity entity) {
                 }
             });

这样使用确实很方便，但是如果`data`为`null`的情况下，就出现了上面那个错误，这里有两个办法

# 第一个

如果`data`在这个网络请求下一直为`Null`,则使用`Object`接收返回的数据，

示例：

    interface Server {
         @GET("http://api.codekk.com/op/page/" + "{page}")
         Observable<SampleEntity<Object>> test(@Path("page") int page);
     }
     
网络请求不变，但是操作符这里处理一下,这里使用的是`Gson`

    private static class SampleFun<T> implements Function<SampleEntity<T>, T> {

        @Override
        public T apply(SampleEntity<T> entity) {
            //如果data为null
             return (T) new TypeToken<T>() {
                        }.getType();
        }
    }
    
这样就可以正确的触发`onNext()`的成功回调

如果不使用`Object`，而是使用`Entity`接收的话，则会报一个

    libcore.reflect.TypeVariableImpl cannot be cast to xxEntity
    
这里是因为`java`泛型擦除的问题，会出现类型转换异常

如果`data`在某个情况下为`null`的话，个人建议是和后台沟通下，但是还有其他办法解决，可以看第二个解决办法

# 第二个

很简单，不要让`onNext`发送`null`即可，但是在获取数据的时候每次都要data.getData()

    class SampleFun<T> implements Function<T, T> {

        @Override
        public T apply(T t) throws Exception {
            if (t instanceof SampleEntity) {
             // 这里判断code
            }
            return t;
        }
    }
