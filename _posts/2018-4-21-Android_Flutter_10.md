---
layout:     post
title:      "Flutter系列--第十章:组件约束"
subtitle:   "处理Flutter中的组件约束"
date:       2018-4-21
author:     "y"
header-mask: 0.3
header-img: "img/header_flutter.png"
catalog: true
tags:
    - android
    - flutter
---

## 项目地址

[知乎专栏](https://github.com/7449/flutter-zhihu_zhuanlan)

## Flutter

[github地址](https://github.com/flutter/flutter)<br>
[官方地址](https://flutter.io/)<br>
[官方文档地址](https://flutter.io/docs/)<br>
[Flutter中文开发者论坛](http://flutter-dev.com/)<br>
[中文文档](http://doc.flutter-dev.cn/)<br>

#### blog

[第一章：什么是Flutter](https://7449.github.io/2018/03/19/Android_Flutter_1/)<br>
[第二章：安装Flutter](https://7449.github.io/2018/03/19/Android_Flutter_2/)<br>
[第三章：编写一个FlutterApp](https://7449.github.io/2018/03/26/Android_Flutter_3/)<br>
[第四章：框架预览](https://7449.github.io/2018/03/26/Android_Flutter_4/)<br>
[第五章：Widget目录](https://7449.github.io/2018/04/12/Android_Flutter_5/)<br>
[第六章：面对Android开发的Flutter说明](https://7449.github.io/2018/04/16/Android_Flutter_6/)<br>
[第七章：面对ReactNative开发的Flutter说明](https://7449.github.io/2018/04/17/Android_Flutter_7/)<br>
[第八章：手势](https://7449.github.io/2018/04/20/Android_Flutter_8/)<br>
[第九章：动画](https://7449.github.io/2018/04/22/Android_Flutter_9/)<br>


## 介绍

`flutter`中,`widget`由最基础的[RenderBox](https://docs.flutter.io/flutter/rendering/RenderBox-class.html)呈现,渲染框则由父`widget`给出约束,并且将它们自己的大小限制在约束中,约束由最大宽度和高度组成,尺寸由特定的宽度和高度组成.

一般来讲,就约束而言,一共有三种类型的`box`：

* 尽可能大的`box`，例如：[Center](https://docs.flutter.io/flutter/widgets/Center-class.html)和[ListView](https://docs.flutter.io/flutter/widgets/ListView-class.html)
* 适应子`widget`的`box`，例如[Transform](https://docs.flutter.io/flutter/widgets/Transform-class.html)和[Opacity](https://docs.flutter.io/flutter/widgets/Opacity-class.html)
* 指定大小,例如[Image](https://docs.flutter.io/flutter/dart-ui/Image-class.html)和[Text](https://docs.flutter.io/flutter/widgets/Text-class.html)的渲染`box`

例如[Container](https://docs.flutter.io/flutter/widgets/Container-class.html)的一些子`widget`会根据参数的不同显示不同的大小,在这种情况下,`Container`会尽可能的大,但是如果给定一个指定的`widget`，那么`Container`会显示那个指定的`widget`

其他的例如[Row](https://docs.flutter.io/flutter/widgets/Row-class.html)和[Column](https://docs.flutter.io/flutter/widgets/Column-class.html)会根据它们的限制而有所不同,如下面的`flex`部分所述

这些约束有的时候是`tight`，这意味着它们没有空间让`renderBox`决定大小(例如如果最小的宽度和高度相同,也就是说有一个`tight`宽度),其中的主要示例`App`是由`RenderView`类填充的`widget`：由应用程序`build`方法返回的`widget`使用的`box`被约束,强制它填充应用程序的内容区域(通常是整个屏幕),`flutter`中的很多`box`，特别是那些只有一个子`widget`的`box`，都会将限制传递给子`widget`，这也就意味着如果应用程序的根`widget`嵌套了一堆`box`，那么它们将受到严格的约束而彼此自适应

有些`box`，有`最大`约束，但没有`最小`约束。例如[Center](https://docs.flutter.io/flutter/widgets/Center-class.html)

## 无限制的约束

在一些情况下,`box`的约束是无限的,这就意味着宽度或者高度是`double.INFINITY`

一个本身试图占用尽可能大的`box`在给定无限制的约束时不会有效果，在检查模式下会抛出异常。

`box`具有无限制约束的最常见情况是在自身处于弹性盒（`Row` 和 `Column`）内以及可滚动区域 （`ListView` 和其他`ScrollView`的子类）内。

特别是，`ListView`试图扩充以适应其横向可用空间（即，如果它是一个垂直滚动块，它将尝试与其父`widget`一样宽)，如果`ListView`在水平滚动的情况下嵌套垂直滚动的`ListView`，则内部滚动区域会尽可能宽，这是无限宽的，因为外部滚动区域可以在水平方向上一直滚动


## Flex

`flex`自身([Row](https://docs.flutter.io/flutter/widgets/Row-class.html)和[Column](https://docs.flutter.io/flutter/widgets/Column-class.html))的行为有所不同，这取决于它们是否存在限制

在有限制的情况下，他们在这个方向上会尽可能大

在无限制的情况下,它们会试图让子`widget`适应这个方向,在这种情况下,子`widget`的`flex`将不能为`0`,在`widget`库中,如果一个弹性`box`位于另一个弹性`box`或可以滚动的`box`内部时,不能使用[Expanded](https://docs.flutter.io/flutter/widgets/Expanded-class.html)，如果使用`Expanded`则会报错

在 交叉(`cross direction`) 方向上, 例如在`Column`的宽度和在`Row`的高度上，它们绝不能是无限制的，否则它们将无法合理地对齐它们的子`widget`
