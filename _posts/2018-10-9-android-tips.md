---
layout:     post
title:      "Android面试总结--加分项"
subtitle:   "Android面试总结--加分项"
date:       2018-10-9
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
---


## 加分项

### 有一定的代码洁癖和对自己的代码约束力

在`react-native`开发中日常打`log`

    console.log();
    
这样打印日志会在控制台输入相应的数据,为了进一步的简单可以在`WebStorm`中创建一个`Live Templates`，
但是这样的缺点是如果到处打印日志太过频繁的话会影响性能,虽然很小.
但这个时候就可以判断出一个人对自己代码的容忍度

相对应的解决办法有很多种,这里介绍一种

    consoles.log = (message) => {
        if (__DEV__) {
            console.log(message);
        }
    };

这样既可以在`debug`中看到日志,`release`中也不会影响性能

#### 善用IDE

    工欲善其事必先利其器
    
可能有人嗤之以鼻,但是会用`IDE`,解决任何`IDE`的突发情况是一个程序员最基本的能力,抛开代码能力不说,
善用会用`IDE`会使你在开发过程中省时省力

这里介绍几种情况下的解决问题:

* IDE 不停的`build`

 file --> Invalidate Caches/Restart
 
* gradle

本地已经存在相对应的依赖情况下,如果在打开项目拉不下网络依赖的情况下可以开启`Offline work`

如果`gradle`下载不下来，可以手动下载下来，然后放在对应的文件夹下,然后重新打开项目可以省略下载这步直接解压

* 代理

可以在`IDE`中开启代理,这样在`VPN`开启的情况下避免因为网络而出现的各种各样的问题

* 插件

`GsonFormat`之类的节省时间的插件

#### 各种优化的小优点

* 这些知识没有开发经验的人几乎不知道,这就是优点,因为没有实际遇到问题,就不会主动去寻找解决问题的办法,而靠着
死记硬背很容易忘掉

例如

`setBackgroundResource(0)` 可以移除 View 的背景色

`android:padding` 和 `android:clipToPadding="false"`结合使用

`Space`占位,会自动跳过`onDraw`

`TextView.setError()`


...


等等小知识

#### 系统自带常用工具或者优化的替代类

`android`的`sdk`就自己带了一些常用的方法

例如

`TextUtils.isEmpty()` `TextUtils.concat()` ...

`DateUtils.formatDateTime())`...

替代类

`SparseArray` `SparseBooleanArray` `SparseIntArray` `SimpleArrayMap`

这个时候如果能讲出来这些替代类的优点会更好

例如`SparseArray`是用`integer`去管理`object`对象，它比`HashMap`去通过`Integer`查找`object`时在内存上更具效率,因为它避免了`HashMap`中基本数据类型需要装箱的步骤，其次不使用额外的结构体（Entry)，单个元素的存储成本下降,
但是`SparseArray`不适用于大量数据的操作,因为它插入操作需要复制数组，增删效率降低

SparseArray`的原理是二分检索法，也因此`key`的类型都是整型,如果`value`是`Boolean`或者其他的也有`SparseBooleanArray`供使用


