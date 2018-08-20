---
layout:     post
title:      "Android中的MVVM和databinding"
subtitle:   "MVVM和databinding在android中的简单使用"
date:       2018-8-20
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - mvvm
    - databinding
---


## 相关代码

[Kotlin-Sample-App](https://github.com/7449/Kotlin-Sample-App)

## 相关资料

[Databinding](https://developer.android.com/topic/libraries/data-binding/)

## mvp

[Android中MVP的使用](https://7449.github.io/2016/10/08/Android_MVP/)

## mvvm

* View: 对应于`Activity`和`XML`，负责`View`的绘制以及与用户交互。
* Model: 实体模型。
* ViewModel: 负责完成`View`与`Model`间的交互，负责业务逻辑。

当然这里只是简单说明下,具体代码建议参考`google`的[mvvm-databinding](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-databinding)

以前也使用过`databinding`但是当时发现使用过程中还是有一些比较棘手的问题,例如出现问题很难排查原因,只支持单向绑定，就没有继续使用下去,当时只是简单的写了一个`Demo`,
截至目前,`databinding`和`mvvm`在`android`中也日渐成熟,越来越多的应用采用了`databinding`,`mvvm`,`rxjava`,`kotlin`...等等具有优势的框架或者应用框架

## 个人对`mvvm`的一些理解

* 为什么在`mvc`->`mvp`的情况下，现在又出现了`mvvm`?

`mvc`以及`mvp`的对比这里就不再赘述,简单说下`mvp`的缺点
1. 不适合小项目,这里的小项目指的是就几个页面的，没有复杂逻辑的项目，如果采用`mvp`只会增加一大堆接口,给后来者会带来一定的阅读难度
2. 随着业务逻辑的递增,如果项目不做减法,`presenter`只会越来越复杂,这个时候因为`presenter`持有`view`的引用，改动起来很麻烦
3. 需要考虑组件的生命周期,`presenter`越来越臃肿那么`mvp`想解决的问题就在`presenter`中又出现了

* `mvvm` 相比 `mvp` 有什么优点?

1. 平时开发中需要更新`UI`时,需要先获取`UI`控件的引用，然后再更新`UI`,在`MVVM`中,数据变化后会自动更新`UI`,`UI`的改变也能自动反馈到数据层，数据成为主导因素。
这样`VM`层在业务逻辑处理中只要关心数据，不需要直接和`UI`打交道
2. 低耦合,数据和业务逻辑在`ViewModel`中，`ViewModel`只需要关注数据和业务逻辑，不需要和控件打交道。
控件想怎么处理数据都由控件决定，`ViewModel`不持有控件的引用,即便是控件层变了，`ViewModel`也几乎不需要更改任何代码
3. 在`MVVM`中，数据发生变化后，可以直接在线程安全的情况下修改`ViewModel`的数据，不需要考虑要切到主线程更新`UI`
4. `ViewModel`带来的低耦合非常适用于团队合作

## databingding