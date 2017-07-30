---
layout:     post
title:      "第一章：Cordova安装及运行"
subtitle:   "第一章：安装Cordova及配置环境"
date:       2017-7-30
author:     "y"
header-mask: 0.3
header-img: "img/cordova.png"
catalog: true
tags:
    - cordova
---

> 这篇文章主要讲解如何安装Cordova并运行在android上面

* 本篇文章涉及到的资料

[cordova中文文档](http://cordova.axuer.com/docs/zh-cn/latest/)<br>
[cordova主页](https://cordova.apache.org/)<br>
[开源代码](https://github.com/apache?utf8=%E2%9C%93&q=cordova&type=&language=)<br>


## 什么是cordova？

Apache Cordova是一个开源的移动开发框架。允许你用标准的web技术-HTML5,CSS3和JavaScript做跨平台开发。 应用在每个平台的具体执行被封装了起来，并依靠符合标准的API绑定去访问每个设备的功能，比如说：传感器、数据、网络状态等。

使用Apache Cordova的人群:

* 移动应用开发者，想扩展一个应用的使用平台，而不通过每个平台的语言和工具集重新实现。

* web开发者，想包装部署自己的web App将其分发到各个应用商店门户。

* 移动应用开发者，有兴趣混合原生应用组建和一个WebView(一个特别的浏览器窗口) 可以接触设备A级PI，或者你想开发一个原生和WebView组件之间的插件接口。

以上摘录自Cordova中文网
        
## 安装


> 作者的开发环境为MacOS，但是安装在Windows上的步骤是相同的

#### Node

安装Node，这步必不可少，安装完成之后你就可以在电脑上使用命令行来安装Cordova

[Node官网](https://nodejs.org/en/download/)

作者是用 brew 去安装的Node.js 当然你也可以去下载安装包去安装Node ，但是极力推荐用 brew 去安装


#### cordova

Node安装完整之后在命令行中输入 `npm install -g cordova` 如果是Mac或者Linux 可以在前面加上`sudo`防止安装时权限的问题

这里需要注意的是不要为了速度去用 cnpm ，cnpm 安装的会出现依赖库不全而报错的问题，npm 需要翻墙去安装，我相信看到这篇文章的程序员没有几个是不会翻墙的

其中 `-g` 是全局安装，这样我们不管在哪里打开Dos 都可以输入`cordova`

不输入 `-g` 则会安装在当前工作目录的 `node_modules`子目录

安装完成之后可以输入 `cordova --help` 查看指令

## 创建第一个Android App


指令： `cordova create folderName appPackageName appName`

`appName`不是必须的，可忽略掉

其中 ：

* folderName : 创建成功之后的文件夹名字
* appPackageName: App包名
* appName: app名称


这样Cordova的开发目录就已经初始化完成了，接下来进入到初始化的目录添加需要的平台，这里用android作为实例，

指令：`cordova platform add android --save`

安装按成之后应该会出现如下代码：


	Using cordova-fetch for cordova-android@~6.2.2
	Adding android project...
	Creating Cordova project for the Android platform:
		Path: platforms/android
		Package: com.hellocordova
		Name: HelloCordova
		Activity: MainActivity
		Android target: android-25
	Subproject Path: CordovaLib
	Android project created with cordova-android@6.2.3
	Discovered plugin "cordova-plugin-whitelist" in config.xml. Adding it to the project
	Installing "cordova-plugin-whitelist" for android
	
	               This plugin is only applicable for versions of cordova-android greater than 4.0. If you have a previous platform version, you do *not* need this plugin since the whitelist will be built in.
	
	Adding cordova-plugin-whitelist to package.json
	Saved plugin info for "cordova-plugin-whitelist" to config.xml
	--save flag or autosave detected
	Saving android@~6.2.3 into config.xml file ...


这个时候你可以去`platforms`目录下看看，会出现`platforms.json` 和 `android` 目录

 
当然你也可以使用`cordova platform ls`查看你的项目中安装了哪些平台，

* 当用命令行创建应用时，不建议修改 `platform` 目录下的东西，一般来说，运行或者构建项目这个目录通常会被重写

## 运行


运行android app 时需要的三个条件缺一不可，分别为`jdk` `sdk` `gradle`，并且要添加在环境变量中。

也可以输入 `cordova requirements` 命令来查看自己的环境是否完整

	Java JDK: installed 1.8.0
	Android SDK: installed true
	Android target: installed
	Gradle: installed 
	
这里建议使用 Android Studio 导入 `/platforms/android` ，`build`项目，如果不报错，这样也可证明环境是完整的，
为什么建议？ 因为作者在Windows上有一次因为sdk的问题，出现了一个小错误，但是 用命令行`requirements`并没有报错，当作者运行项目的时候才报错了

这里运行项目时选择了限制平台：

`cordova build android`

第一次运行时如果缺少一些东西，会自动下载，所以比较慢，静心等待即可

	BUILD SUCCESSFUL
	
	Total time: 3 mins 47.97 secs
	Built the following apk(s):
		.../platforms/android/build/outputs/apk/android-debug.apk
		
可以看到作者第一次运行时花费了 `3 mins 47.97 secs`,其中 99% 的时间都是用来下载缺少的东西

这个命令只是生成debug的apk，也可以使用` cordova run android`来直接运行在手机上，当然，条件是你的手机必须成功连接在电脑上并且打开了USB调试

至此 第一个Cordova的apk已成功运行，开始页面位于`www/index.html`

最后附上作者成功运行的截图：

![_config.yml]({{ site.baseurl }}/img/corodva-success.jpg)


那么，我们下一章见！
	