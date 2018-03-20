---
layout:     post
title:      "Flutter系列--第一章:什么是Flutter"
subtitle:   "本章简单讲述下Flutter是什么,有什么用"
date:       2018-3-19
author:     "y"
header-mask: 0.3
header-img: "img/android.png"
catalog: true
tags:
    - android
    - flutter
---

## Flutter

[github地址](https://github.com/flutter/flutter)<br>
[官方地址](https://flutter.io/)<br>
[官方文档地址](https://flutter.io/docs/)<br>
[Flutter中文开发者论坛](http://flutter-dev.com/)<br>
[中文文档](http://doc.flutter-dev.cn/)<br>


> 本系列文章更多的可以认为是Flutter的官翻文档并且添加一点自己的想法，作者也知道不少的Flutter论坛已经开始了Flutter的翻译计划
> 但是为了自己的学习以及加深对Flutter的理解,还是决定自己记录


> 由于 Flutter 刚推出了beta版,处于快速迭代,所以记录下本系列的Flutter提交记录


[github提交记录为`9c49255f3eeb4873e6398c805b4bb6b9737be962`](https://github.com/flutter/flutter/commit/9c49255f3eeb4873e6398c805b4bb6b9737be962)

#### 什么是Flutter?

简单来说就是`google`推出的一门跨平台框架,兼容了 ios 和 android，并且为`google`下一个操作系统`Fuchsia`的官方开发框架

底层语言为`Dart`

知乎上有个问题推荐去阅读一下

[如何评价 Google 的 Fuchsia、Android、iOS 跨平台应用框架 Flutter](https://www.zhihu.com/question/50156415)


## 与 RN 对比

首先作者之前写个一款比较简单的RN app，通过直观对比，感觉渲染还是`Flutter`更为快速，并且各种动画,`MD`效果都是默认支持,简直不能更赞

摘录自知乎@马超的回答：

    一个是 Facebook 推出两年多的 RN ，另一个则是 Google 这种顶级科技公司的产品。
    那么它们有什么区别呢？
    玩过 ReactNative 的朋友应该或多或少看过它的源码，从实现原理上来讲 ReactNative 提供的组件都是继承自原生 Native 的 View 组件，
    比如ReactNative 中的 ListView 在 Android 中就是继承自 ListView ，
    还有 RecycleView。
    然而 Flutter 则不同，它的所有 UI 组件都是一帧一帧画出来的。
    这样也能够很准确，也很灵活的做出你想要的 UI 。
    其次它还非常人性化的贴近了平台的特性
    ，比如 Android 的 Material Design 在 Flutter 就默认支持了进去。
    其实话说回来，在开发者角度来讲这两个跨平台都是一样的使用效果，毕竟都是通过一套语言来搭建可运行不同平台的应用。
    然而，Flutter 使用 Dart 语言开发而 ReactNative 则使用 JS 结合 XML 来开发的。这就有问题了
    
    作者：马超
    链接：https://www.zhihu.com/question/50156415/answer/278374951
    来源：知乎
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

私认为`Flutter`作为`Fuchsia`的主推开发框架，并且由`google`推出,能感觉出来它的前景更为广泛，对`google`的迷之自信吧,哈哈
