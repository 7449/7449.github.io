---
layout:     post
title:      "Flutter系列--第一章:什么是Flutter"
subtitle:   "本章简单讲述下Flutter是什么,有什么用"
date:       2018-3-19
tags:
    - android
    - flutter
---

## 项目地址

[Flutter示例集合](https://github.com/7449/flutter_example)


## flutter

[github地址](https://github.com/flutter/flutter)<br>
[官方地址](https://flutter.io/)<br>
[官方文档地址](https://flutter.io/docs/)<br>
[Flutter中文开发者论坛](http://flutter-dev.com/)<br>
[中文文档](http://doc.flutter-dev.cn/)<br>

## blog

[Flutter番外篇:Dart](https://7449.github.io/2018/03/18/android_flutter_dart.html)<br>
[第一章：什么是Flutter](https://7449.github.io/2018/03/19/android_flutter_1.html)<br>
[第二章：安装Flutter](https://7449.github.io/2018/03/19/android_flutter_2.html)<br>
[第三章：编写一个FlutterApp](https://7449.github.io/2018/03/26/android_flutter_3.html)<br>
[第四章：框架预览](https://7449.github.io/2018/03/26/android_flutter_4.html)<br>
[第五章：Widget目录](https://7449.github.io/2018/04/12/android_flutter_5.html)<br>
[第六章：面对Android开发的Flutter说明](https://7449.github.io/2018/04/16/android_flutter_6.html)<br>
[第七章：面对ReactNative开发的Flutter说明](https://7449.github.io/2018/04/17/android_flutter_7.html)<br>
[第八章：手势](https://7449.github.io/2018/04/20/android_flutter_8.html)<br>
[第九章：动画](https://7449.github.io/2018/04/20/android_flutter_9.html)<br>
[第十章：布局约束](https://7449.github.io/2018/04/21/android_flutter_10.html)<br>
[第十一章：处理 assets 和 图像](https://7449.github.io/2018/04/22/android_flutter_11.html)<br>
[json序列化](https://7449.github.io/2018/05/02/android_flutter_json_serializable.html)<br>
[初始化项目和闪屏页](https://7449.github.io/2018/04/23/android_flutter_splash.html)<br>
[添加Tab和Drawer](https://7449.github.io/2018/04/24/android_flutter_drawer.html)<br>
[列表页完善,网络请求](https://7449.github.io/2018/04/24/android_flutter_net_list.html)<br>
[列表详情页](https://7449.github.io/2018/04/25/android_flutter_net_list_detail.html)<br>

> 本系列文章更多的可以认为是Flutter的官翻文档并且添加一点自己的想法，作者也知道不少的Flutter论坛已经开始了Flutter的翻译计划
> 但是为了自己的学习以及加深对Flutter的理解,还是决定自己记录


> 由于 Flutter 刚推出了beta版,处于快速迭代,所以记录下本系列的Flutter提交记录


[github提交记录为`9c49255f3eeb4873e6398c805b4bb6b9737be962`](https://github.com/flutter/flutter/commit/9c49255f3eeb4873e6398c805b4bb6b9737be962)

## 什么是Flutter?

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
