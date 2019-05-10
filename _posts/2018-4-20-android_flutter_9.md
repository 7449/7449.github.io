---
layout:     post
title:      "Flutter系列--第九章:动画"
subtitle:   "Flutter中的动画"
date:       2018-4-20
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

## 动画类型

适度的过渡动画可以给用户更好的体验,`flutter`的动画支持实现各种动画类型,特别是[Material Design部件](https://flutter.io/widgets/material/)，都带有标准的动画效果,当然也可以自定义这些效果

以下是学习`flutter`动画框架的好地方,每一个文档都逐步展示了怎么编写一个动画代码：

* [`flutter`中的动画](https://flutter.io/tutorials/animation/)

	讲解了`flutter`的动画包,(控制器,动画,曲线,侦听器,构建器)中的基本类,会从`api`的不同方面指导使用者使用补间动画
	
* [`flutter`从零到一第一部分](https://medium.com/dartlang/zero-to-one-with-flutter-43b13fd7b354)和[第二部分](https://medium.com/dartlang/zero-to-one-with-flutter-part-two-5aa2f06655cb)

	中级文章,介绍了怎么使用补间动画创建动画
	
* [使用`flutter`构建美丽的用户界面](https://codelabs.developers.google.com/codelabs/flutter/index.html#0)

	`Codelab`演示了怎么构建一个简单的聊天程序,[第七步的时候](https://codelabs.developers.google.com/codelabs/flutter/index.html#6)显示了怎么对新消息进行动画处理-将新消息从输出区域滑动到消息列表

动画分为两类：

* tween(补间动画)
* 物理动画

以下部分解释了这些术语的含义，使用者能在其中了解更多的信息,在某些情况下,目前最好的文档就是`flutter`的示例代码

#### 补间动画

在补间动画中,定义了开始和结束点,以及时间线和定义时间线和速度的曲线,然后框架计算如何从开始点过度到结束点，上面的文档(例如[animation](https://flutter.io/tutorials/animation/))讲解的并不是补间动画,但是它们在实例中使用到了补间动画

#### 物理动画

在物理动画中,运动被模拟为为真实世界行为相似,例如当你掷球时，它在何处落地，取决于抛球速度有多快，球有多重，距离地面有多远。类似地，将连接在弹簧上的球落下（并弹跳）与将连接到绳上的球放下的方式不同。

* [flutter_gallery](https://github.com/flutter/flutter/tree/master/examples/flutter_gallery)

	在`Material Components`中,[grid](https://github.com/flutter/flutter/blob/master/examples/flutter_gallery/lib/demo/material/grid_list_demo.dart)示例中演示了一个动画,从网格中选择一个图像然后放大,然后可以根据手势平移图像

* 另请查看[animateWith](https://docs.flutter.io/flutter/animation/AnimationController/animateWith.html)和[SpringSimulation](https://docs.flutter.io/flutter/physics/SpringSimulation-class.html)的`api`文档

## 动画模式

本节列出了一些常用的动画，并且告诉你在哪里可以了解更多的信息

#### 列表或者网格动画

此模式涉及到了在列表中删除或者添加元素时候的动画

* [Animatedlist](https://flutter.io/catalog/samples/animated-list/)

	此示例演示如何将元素添加到列表或删除选定元素,内部Dart列表在用户使用(+)和(-)按钮修改列表时并同步列表

#### 共享元素转换

在这种模式中，用户从页面中选择一个元素（通常是一个图像），然后打开所选元素的详情页面，在打开详情页时使用动画。 在Flutter中，您可以使用Hero widget 轻松实现路由（页面）之间的共享元素过渡动画

* [Hero 动画](https://flutter.io/animations/hero-animations/)

	如何创建两种风格的` Hero `动画：
	
	* 在改变位置和大小的同时，`hero`从一页转到另一页
	* `hero`的边界从一个圆形变成一个正方形，同时它从一个页面转到另一个页面

* [flutter Gallery](https://github.com/flutter/flutter/tree/master/examples/flutter_gallery)

	 您可以自己构建Gallery应用程序，也可以从Play商店下载(中国不行)。 其中[Shrine](https://github.com/flutter/flutter/blob/master/examples/flutter_gallery/lib/demo/shrine_demo.dart)演示了包括`hero`动画的一个例子。

另外请参阅[Hero](https://docs.flutter.io/flutter/widgets/Hero-class.html),[Navigator](https://docs.flutter.io/flutter/widgets/Navigator-class.html)和[PageRoute](https://docs.flutter.io/flutter/widgets/PageRoute-class.html) 类的`API`文档。

#### 交错动画

动画被分解为较小的动作，其中一些动作被延迟,较小的动画可以是连续的，或者可以部分或完全重叠。

* [交错动画](https://flutterchina.club/animations/staggered-animations/)

## 其他

在以下链接中了解更多关于Flutter动画的信息：

* [动画: 技术概述](https://flutter.io/animations/overview.html)

	查看动画库中的一些主要类，以及`flutter`的动画架构。

* [动画和运动（Motion） Widgets](https://flutterchina.club/widgets/animation/)
	
	`flutter`提供的一些动画`widget `的目录
