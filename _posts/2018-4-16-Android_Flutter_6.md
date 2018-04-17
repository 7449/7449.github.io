---
layout:     post
title:      "Flutter系列--第六章:面对Android开发的Flutter说明"
subtitle:   "Flutter 与android开发的区别"
date:       2018-4-16
author:     "y"
header-mask: 0.3
header-img: "img/header_flutter.png"
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

#### blog

[第一章：什么是Flutter](https://7449.github.io/2018/03/19/Android_Flutter_1/)<br>
[第二章：安装Flutter](https://7449.github.io/2018/03/19/Android_Flutter_2/)<br>
[第三章：编写一个FlutterApp](https://7449.github.io/2018/03/19/Android_Flutter_3/)<br>
[第四章：框架预览](https://7449.github.io/2018/03/26/Android_Flutter_4/)<br>
[第五章：Widget目录](https://7449.github.io/2018/04/12/Android_Flutter_5/)<br>

## flutter与android的区别

本篇文章主要针对的是熟练`android`开发的一些人员,没有必要专门去学习`flutter`,只需要大致看一下区别即可。

作者刚开始写`react native`的时候也是类似于这种的学习方式,只是简单的看了下`react native`的框架,
最后都是在开发中边写边学习



注： 以`android`开头的标题,都是讲了在`flutter`里面等价于什么
    例如`android-view`表示在`flutter`里面什么东西相同与`android`里面的`view`


## views

#### android-view 

#### 怎么更新 `widget`

#### 布局，android-xml

#### 增删组件

#### 设置动画

#### 使用`Canvas`绘制

#### 自定义小部件

## intent

#### android-intent

#### 处理其他app传入的`intent`

#### startActivityForResult

## 异步

#### android-runOnUiThread

#### 切换线程

#### 网络请求,android-okhttp

#### 显示进度条

## 项目资源和结构

#### 图片

#### 字符串

#### android-gradle

## Activity or Fragment

#### android-activity-fragment

#### 生命周期

## 布局

#### android-LinearLayout

#### android-RelativeLayout

#### android-ScrollView

## 手势,触摸

#### onClick

#### 其他手势

## ListView and Adapter

#### android-ListView

#### item-onclick

#### 刷新ListView 

## 文本

#### 自定义字体

#### 修改样式

## 输入框

#### 提示输入hintText

#### 显示错误输入

## 插件

#### GPS

#### 相机

#### 登陆facebook

#### 自定义插件

## 主题

#### 设置`Material-style`

## 数据库和本地存储

#### 数据库

#### 本地存储

## 通知

#### 推送