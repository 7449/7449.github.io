---
layout:     post
title:      "Flutter系列--第二章:在Mac上初始化Flutter并运行第一个Flutter App"
subtitle:   "本章讲解如何安装Flutter并且运行第一个App"
date:       2018-3-19
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
[第九章：动画](https://7449.github.io/2018/04/20/Android_Flutter_9/)<br>
[第十章：布局约束](https://7449.github.io/2018/04/21/Android_Flutter_10/)<br>

## 安装

#### mac准备工作

xcode,as或者IDEA或者vscode，并且推荐使用`brew`安装所需的依赖：`bash`, `mkdir`, `rm`, `git`, `curl`, `unzip`, `which`

其中有些依赖Mac已经是默认安装了,所以没必要重复安装

#### 获取Flutter SDK

> clone 项目

    git clone -b beta https://github.com/flutter/flutter.git 
    
> 添加路径

    export PATH=`pwd`/flutter/bin:$PATH
    
    or
    
    export PATH=${PATH}:.../android/flutter/bin:$PATH


> 检查依赖

    flutter doctor


作者的输出日志如下：

	Doctor summary (to see all details, run flutter doctor -v):
	[✓] Flutter (Channel beta, v0.1.5, on Mac OS X 10.13.3 17D102, locale en-CN)
	[✓] Android toolchain - develop for Android devices (Android SDK 27.0.3)
	[✓] iOS toolchain - develop for iOS devices (Xcode 9.2)
	[✓] Android Studio (version 3.0)
	[✓] IntelliJ IDEA Ultimate Edition (version 2017.3.2)
	[!] VS Code (version 1.20.1)
	[✓] Connected devices (1 available)
	
	! Doctor found issues in 1 category.


这里 `VS Code` 出现感叹号是作者并没有安装 `VS Code`，忽略即可,其他的不是很严重的错误同理

作者还是推荐使用`Android Studio`来编写`Flutter`,毕竟官推IDE


然后在 `IDEA` OR `AndroidStudio`中安装插件`Dart` and `Flutter`并重启IDE

`Android Studio`重新启动的时候就会有`start a new flutter project`，按照步骤创建第一个App


如果没有问题将会在模拟器或者真机上出现如下画面：

![_config.yml]({{ site.baseurl }}/img/flutter_first_install_app.png)
