---
layout:     post
title:      "Flutter系列--第八章:手势"
subtitle:   "如何在Flutter中判断各种各样的手势"
date:       2018-4-20
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

#### 介绍

`flutter`中有手势有两个独立的层

* 原始指针事件

	屏幕上的指针,例如触摸,鼠标,或者触控笔在屏幕上的位置和移动
	
* 手势

	一个或多个指针组成的动作(多点触控)

#### 指针

指针代表了用户和屏幕之间的交互，有四种类型：

* [PointerDownEvent](https://docs.flutter.io/flutter/gestures/PointerDownEvent-class.html):已经开始和屏幕接触
* [PointerMoveEvent](https://docs.flutter.io/flutter/gestures/PointerMoveEvent-class.html):从屏幕一个位置移动到另一个位置
* [PointerUpEvent](https://docs.flutter.io/flutter/gestures/PointerUpEvent-class.html):停止触摸屏幕
* [PointerCancelEvent](https://docs.flutter.io/flutter/gestures/PointerCancelEvent-class.html):此指针已经不再针对这个应用

当指针开始的时候,框架开始对`app`进行测试,来确定指针和屏幕接触的位置存在哪些`widget`，触摸事件会从根部的`widget`向外分发,没有任何机制可以取消或者停止调度指针，如果想从`widget`监听指针，请使用[Listener](https://docs.flutter.io/flutter/widgets/Listener-class.html)，但是通常情况下，请考虑使用手势

#### 手势

手势可以从多个单独的指针中识别出例如点击,拖动，缩放这种动作,手势可以分派多个时间,对应于手势的生命周期(例如开始滑动，滑动,滑动结束)

* Tap

	* onTapDown 按下
	* onTapUp 抬起
	* onTap 点击
	* onTapCancel 按下但是没有触发`onTapUp`

* Double tap

   * onDoubleTap 使用者快速两次点击相同的位置

* Long press 

	* onLongPress 使用者长按

* Vertical drag

	* onVerticalDragStart  	垂直移动的起点
	* onVerticalDragUpdate  垂直移动过程中不断触发
	* onVerticalDragEnd  	垂直移动结束,停止触摸屏幕

* Horizontal drag

	* onHorizontalDragStart  水平移动的起点
	* onHorizontalDragUpdate 水平移动过程中不断触发
	* onHorizontalDragEnd    水平移动结束,停止触摸屏幕

如果要在`widget`监听手势请使用[GestureDetector](https://docs.flutter.io/flutter/widgets/GestureDetector-class.html)

如果使用`Material`小部件,很多小部件已经对点击或者其他手势有相对应的方法,例如`IconButton`和`FlatButton`可以响应用户的点击,`ListView`可以响应滑动,如果不想使用这么`widget`，但是想要水波纹效果,可以使用[InkWell](https://docs.flutter.io/flutter/material/InkWell-class.html)

#### 手势消歧

在屏幕上的指定位置，可能会有多个手势检测器。所有这些手势检测器在指针事件流经过并尝试识别特定手势时监听指针事件流， [GestureDetector](https://docs.flutter.io/flutter/widgets/GestureDetector-class.html)根据哪些回调是非空的来决定是哪种手势。

当屏幕上给定指针有多个手势识别器时，框架通过让每个识别器加入一个`手势竞争场(gesture arena)`来确定用户想要的手势。`手势竞争场(gesture arena)`使用以下规则确定哪个手势胜出

* 在任何时候，识别者都可以宣布失败并离开`手势竞争场(gesture arena)`，如果在识别器中只剩下一个识别器，那么该识别器就是赢家

* 在任何时候，识别者都可以宣布胜利，这会导致胜利，并且所有剩下的识别器都会失败

例如，在消除水平和垂直拖动的歧义时，两个识别器在接收到指针向下事件时进入`手势竞争场(gesture arena)`，识别器观察指针移动事件, 如果用户将指针水平移动超过一定数量的逻辑像素，则水平识别器将声明胜利，并且手势将被解释为水平拖拽，类似地，如果用户垂直移动超过一定数量的逻辑像素，垂直识别器将宣布胜利。

当只有水平（或垂直）拖动识别器时，`手势竞争场(gesture arena)`将只有一个识别器，并且水平拖动将被立即识别，这意味着水平移动的第一个像素可以被视为拖动，用户不需要等待进一步的手势消歧。
