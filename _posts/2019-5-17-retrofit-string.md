---
layout:     post
title:      "Retrofit获取String"
subtitle:   "通过Retrofit获取网页String获取Json"
date:       2019-5-17
tags:
    - retrofit2
---

在某些情况下请求网络可能只需要获取到`string`数据

然后自己再处理其他情况,本文讲几种如何在使用`retrofit`时获取`string`数据

这里先讲一下一个注意点,在`rxjava`结合使用中,一般使用的解析器都是`GsonConverterFactory`

有的人可能会直接如下所示获取数据

    @GET(url)
    fun getString(): Observable<String>


等获取数据时会直接报错,类似于这种的错误信心:

    Expected a string but was BEGIN_OBJECT at line 1 column 2 path $
    
很明显的是`Gson`解析数据失败,所以在使用普通的`Json`解析适配器时是无法直接通过设置`string`获取数据的

至于怎么解决可在下面几个方式中得到答案

## ResponseBody

    @GET(url)
    fun getString(): Observable<ResponseBody>
    
直接获取`okhttp`的`ResponseBody`,然后在成功回调中通过`body.string()`来获取需要的数据

#### 缺点

需要`try catch`,并且如果在这种情况多的下每次都要`try`,那么就需要再封装一下

## converter

其实`squareup`已经准备好了一款获取各种类型的适配器

    implementation 'com.squareup.retrofit2:converter-scalars:2.5.0'

然后设置适配器

    Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
