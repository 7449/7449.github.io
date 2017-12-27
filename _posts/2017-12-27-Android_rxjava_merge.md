---
layout:     post
title:      "RxJava一次请求多个网络接口"
subtitle:   "针对RxJava多次请求网络接口"
date:       2017-12-27
author:     "y"
header-mask: 0.3
header-img: "img/android.png"
catalog: true
tags:
    - android
    - rxjava
---

## 前言

有时候会遇到在一个界面同时请求多个接口,最简单的办法就是各自请求,哪个调用成功了就先显示哪个，但是这个风险比较大
而且请求是异步的,如果对请求接口的先后顺序有要求的话这个办法就不适用了。

一个请求成功了再去请求另一个，这个方法虽然可以但是很明显能感觉出来这样写很不优雅，造成了嵌套逻辑,很容易造成逻辑上的问题

## rxjava

项目中使用到了`RxJava`,这里简单讲解下如何使用`rxjava`同时请求多个接口或者连续请求接口

简单分下场景：

* 如果一个页面使用了几个不同的接口去获取数据然后显示,这个时候是同时请求多个接口
* 登录的时候会先请求登录接口，然后进首页再请求接口,这个时候可以连续调用接口

## 准备

作者这里使用了之前封装的一个网络请求框架 [RxNetWork](https://github.com/7449/RxNetWork)

先获取两个`Observable`，是两个一样的接口，这里为了区分,一个获取`model`，一个获取`String`

        Observable<List<ListModel>> daily = RxNetWork.observable(Api.ZLService.class).getList("daily", 20, 0);
        
        Observable<Object> dail2 = RxNetWork.observable(Api.ZLService.class).getObject("daily", 20, 0);

## 同时请求多个接口

这里可以使用 `rxjava`的`merge`操作符,这个合并操作符就是将多个`Obsevable`合并为一个，比较方便统一管理

这样的网络请求封装的过程现在网上一抓一大把，这里就不在叙说了，在这里，直接看结果。

将两个不同的`Observable`使用`merge`操作符合并起来，然后直接请求网络

        Observable<Object> merge = Observable.merge(daily, dail2);
        RxNetWork.getInstance().getApi(merge, new RxNetWorkListener<Object>() {
            @Override
            public void onNetWorkStart() {
                KLog.i("onNetWorkStart");
            }

            @Override
            public void onNetWorkError(Throwable e) {
                KLog.i("onNetWorkError");
            }

            @Override
            public void onNetWorkComplete() {
                KLog.i("onNetWorkComplete");
            }

            @Override
            public void onNetWorkSuccess(Object data) {
                KLog.i(data);
            }
        });

如果不出意外,没有走错误的话,可以依次看见控制台输出

    onNetWorkStart
    
    array类型
    
    一大段字符串
    
    onNetWorkComplete
    
如果把`daily`,`daily2`,相互对换，控制台会输出：

    onNetWorkStart
    
    一大段字符串
    
    array类型
    
    onNetWorkComplete
    
顺便可以证明一个猜想，输出是按照合并的顺序依次发射的。

`merge`底层是使用的`flatMap`，并且`delayErrors`为`false`，也就是说只要有一个`Observable`走了`onError`，那么会终止整个序列，不会再走`onNext`。

如果不用知道是哪个接口报错了是可以使用`merge` or `mergeArray`

## 连续请求接口

就是嵌套网络请求,在第一个网络请求成功之后再请求N个接口

最简单的就是先注册,注册成功直接携带信息去请求登录接口,这里为了方便还是继续使用上面的接口去测试

目前来说网上大把的RxJava网络请求使用说明，这里就不再贴嵌套请求的代码了,总而言之，看起来比较烦，而且不利于维护

嵌套请求这里使用的是`flatMap`操作符,就是上面`merge`底层实现的操作符

	
	 Observable<Object> objectObservable =
	                daily
	                        .subscribeOn(Schedulers.io()) //先切换到IO
	                        .flatMap(new Function<List<ListModel>, ObservableSource<?>>() {
	                            @Override
	                            public ObservableSource<?> apply(List<ListModel> listModels) throws Exception {
	                                KLog.i(listModels);
	                                return daily;
	                            }
	                        })
	                        .flatMap(new Function<Object, ObservableSource<?>>() {
	                            @Override
	                            public ObservableSource<?> apply(Object o) throws Exception {
	                                KLog.i(o);
	                                return daily;
	                            }
	                        })
	                        .flatMap(new Function<Object, ObservableSource<?>>() {
	                            @Override
	                            public ObservableSource<?> apply(Object o) throws Exception {
	                                KLog.i(o);
	                                return daily;
	                            }
	                        })
	                        .flatMap(new Function<Object, ObservableSource<?>>() {
	                            @Override
	                            public ObservableSource<?> apply(Object o) throws Exception {
	                                KLog.i(o);
	                                return daily;
	                            }
	                        });

        RxNetWork.getInstance()
                .getApi(objectObservable,
                        new RxNetWorkListener<Object>() {
                            @Override
                            public void onNetWorkStart() {

                            }

                            @Override
                            public void onNetWorkError(Throwable e) {
                                KLog.i("onError:" + e.toString());
                            }

                            @Override
                            public void onNetWorkComplete() {
                                KLog.i("onComplete");
                            }

                            @Override
                            public void onNetWorkSuccess(Object data) {
                                KLog.i(data);
                            }
                        });
                        
                        
如果网络都是请求成功的话,可以在控制台看见依次输出了6行日志，分别是四个`flatMap`日志，一个`onNext`，一个`onComplete`

很明显的就可以看出来,如果一个网络接口的请求是根据上一个的接口是否成功去判断的话,`flatMap`是一个非常好的适合的操作符，并且在直观上看起来更加清晰明朗，而且`flatMap`默认的是只要有一个错误了,会直接中断发射器

但是如果在请求途中有个网络请求失败了，是如何知道是哪个接口失败了呢？

