---
layout:     post
title:      "Android_React_Native_win,Mac环境搭载"
subtitle:   "React_Native_win,Mac环境搭载"
date:       2017-02-10
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
---

## Win

#### 安装之前准备


从[java](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)官网安装正确的JDK，并且配置好环境变量<br>

搭建好Android环境，配置好SDK.并且配置好ADB指令

SDK可下载的方式很多，现在不需要FQ就可以直接下载，或者使用代理，<br>

推荐网站：

[androiddevtools](http://androiddevtools.cn/)<br>
[bugly镜像](http://android-mirror.bugly.qq.com:8080/include/usage.html)<br>


#### 安装C环境

[itellyou](http://msdn.itellyou.cn/)下载Visual Studio,安装就OK<br>

或者选择`Windows SDK`、`cygwin`或`mingw`等其他C++环境。编译node.js的C++模块时需要用到.

#### 安装Node.js

[官网](https://nodejs.org/en/)直接下载安装即可;


>接下来一些操作建议全程FQ,以保证网络的可用性;

#### 安装react-native命令行工具

如果你的Node.js安装正确， 命令行直接输入 `npm install -g react-native-cli`

等下载好了你会得到如下类似的界面

		`-- react-native-cli@2.0.1
		  `-- chalk@1.1.3
		    `-- has-ansi@2.0.0
		      `-- ansi-regex@2.1.1

这个时候 输入 `react-native -version` 如果不出意外，会出现`react-native-cli: version`<br>

我这里 `version = 2.0.1`

#### 创建ReactNativeProject


在你的工作目录下打开DOS<br>

输入`react-native init Project`静静等待，这里Project仅仅是目录名，你可以替换成任意你想要的名字<br>

下载过程有时候会很慢，请慢慢等候


#### 运行及测试


运行 packager：`npm start`<br>

可以通过[localhost](http://localhost:8081/index.android.bundle?platform=android)检测是否启动成功<br>

如果看到打包后的脚本就证明启动成功<br>

在Android上运行：<br>

保持 Packager 窗口开启，重新在项目目录下开启一个DOS窗口，运行：

`react-native run-android`

这个时候应该会从网上下载Gradle依赖，请确保网络连通<br>

建议是在Android的项目中先设置好 可以确保在你的电脑上运行的sdk运行环境，这样可以省却一些运行时因为sdk版本出现问题的麻烦. <br>


如果成功后手机出现红屏,请摇晃手机在`Dev setting `里面设置 `debug server host` 修改为你的电脑Ip地址，端口为8081

例如：192.168.1.12:8081<br>

然后摇晃手机 ， `reload js`

如果 只是简单的修改 js文件，不用重新运行，直接 `reload js`  即可看到修改后的效果



## Mac

Mac上安装较为简单<br>

#### XCode

App Store就有 点击安装即可

#### 安装HomeBrew

[HomeBrew](http://brew.sh/index_zh-cn.html): 

Mac的包管理器，用于安装NodeJs和其他一些工具,指令如下：<br>

`/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

如果提示你权限不足之类的，请 加上 `sudo` 重新试试<br>

如果`/usr/local`目录不可写,请使用 `sudo chown -R 'whoami' /usr/local` 修复此问题<br>

#### 安装NodeJs

`brew install node`

#### Yarn、React Native的命令行工具

[Yarn](https://yarnpkg.com/lang/en/)是FaceBook提供的替代npm的工具

`npm install -g yarn react-native-cli`

如果提示 `EACCES: permission denied` 请修复`/usr/local`目录的权限



#### 创建ReactNativeProject

输入`react-native init Project`静静等待


#### 测试 

`react-native run-ios`

## 提示

如果Gradle出错，请自行FQ<br>

如果apk安装错误，请检查adb环境变量，MIUI系统请进入开发者选项关掉`MIUI优化`