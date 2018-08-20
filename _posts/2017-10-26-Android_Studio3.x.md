---
layout:     post
title:      "AndroidStudio升级到3.x"
subtitle:   "记下遇到的问题"
date:       2017-10-26
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - ide
---



## 前言

今天Android Studio推送了3.0正式版，升级之后遇到了几个问题，记录下来，避免再犯


## 改变

> 高版本源码

首先就是 `findViewById` 的改动，看源码得知
底层的源码做了改动，不用再每次都强转`View`，


    @SuppressWarnings("TypeParameterUnusedInFormals")
    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        return getDelegate().findViewById(id);
    }
   
> IDE

![_config.yml]({{ site.baseurl }}/img/ide_3.x_tips.png)

如图所示，会有提示，IDE并且已经支持`Kotlin``Java8`，这样的新特性诱惑，不得不升级啊

## 问题

#### anim

以前是这样写的

    <style name="BottomToTopAnim" parent="android:Animation">
        <item name="@android:windowEnterAnimation">@anim/bottomview_anim_enter</item>
        <item name="@android:windowExitAnimation">@anim/bottomview_anim_exit</item>
    </style>

编译并没有问题，但是升级了之后提示`bottomview_anim_exit`找不到，IDE并且也跳转不到

去掉了`@`编译通过

    <style name="BottomToTopAnim" parent="android:Animation">
        <item name="android:windowEnterAnimation">anim/bottomview_anim_enter</item>
        <item name="android:windowExitAnimation">anim/bottomview_anim_exit</item>
    </style>
    
#### findViewById

在 api 26之后，源码进行了改变，如果使用Kotlin，会报错

	Type inference failed: Not enough information to infer parameter T in fun <T : View!> findViewById(p0: Int): T!
	Please specify it explicitly.
	
主要是因为返回的泛型是不明确的，kotlin不能推断出类型

	findViewById(id)
	
	可改为
	
	findViewById<View>(id)

即可编译通过

## gradle version

如果升级到 3.x 版本遇到了 gradle build 问题

将 `com.novoda:bintray-release` 升级到`0.5.0`即可
    
## 改进

gradle升级到`3.0.0`

`compile `依赖改为`implementation ` or `api`

建议是如果`implementation `依赖没问题，那就用`implementation `,如果有问题改为`api`，因为`api`就是以前的`compile`，只不过是改个名字




