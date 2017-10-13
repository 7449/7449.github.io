---
layout:     post
title:      "Cordova系列---第三章：Config介绍"
subtitle:   "config.xml在cordova中扮演着什么角色？怎么在里面设置名称，图标，插件等等功能？"
date:       2017-8-1
author:     "y"
header-mask: 0.3
header-img: "img/cordova.png"
catalog: true
tags:
    - cordova
---

本文相关资料：

[cordova官网config介绍](http://cordova.apache.org/docs/en/latest/config_ref/):<br>


## config.xml

	<?xml version='1.0' encoding='utf-8'?>
	<widget id="com.hello.cordova" version="1.0.0" xmlns="http://www.w3.org/ns/widgets" xmlns:cdv="http://cordova.apache.org/ns/1.0">
	    <name>HelloCordova</name>
	    <description>
	        A sample Apache Cordova application that responds to the deviceready event.
	    </description>
	    <author email="dev@cordova.apache.org" href="http://cordova.io">
	        Apache Cordova Team
	    </author>
	    <content src="index.html" />
	    <plugin name="cordova-plugin-whitelist" spec="1" />
	    <access origin="*" />
	    <allow-intent href="http://*/*" />
	    <allow-intent href="https://*/*" />
	    <allow-intent href="tel:*" />
	    <allow-intent href="sms:*" />
	    <allow-intent href="mailto:*" />
	    <allow-intent href="geo:*" />
	    <platform name="android">
	        <allow-intent href="market:*" />
	    </platform>
	    <platform name="ios">
	        <allow-intent href="itms:*" />
	        <allow-intent href="itms-apps:*" />
	    </platform>
	</widget>


上面是一个刚初始化完成的Cordova项目里面的 config.xml，从上往下以此说明里面的属性分别代表了什么意思


* id: 包名
* version: 版本号
* name:应用名称
* description:简单说明，会出现在市场介绍中
* author：开发者信息
* content: 指定首页地址
* plugin:相关插件，具体可查看[Cordova系列第二章插件](https://7449.github.io/2017/07/31/Android_Cordova_plugin/)
* access:允许app发起的网络请求，*为所有
* allow-intent : 从系统打开该Intent，例如 tel，android打电话
* platform:平台，像一些 是否隐藏splash页面这种小功能，都可以通过限制平台在不同设备上显示不同效果


当然 config 的设置远远不止这么一点，这个只是简单的介绍下最原始的 config 的各个参数含义，接下来介绍怎么设置图标，插件等等功能，


## 启动图标

位于`/res/icon`目录下，里面放置了各个不同的系统图标。

把启动图片放置在`/res/icon/android` 目录下，然后在 config 中修改

	<?xml version='1.0' encoding='utf-8'?>
	<widget id="com.hello.cordova" version="1.0.0" xmlns="http://www.w3.org/ns/widgets" xmlns:cdv="http://cordova.apache.org/ns/1.0">
	    <platform name="android">
	        <allow-intent href="market:*" />
	        <icon density="ldpi" src="res/icon/android/icon-36-ldpi.png" />
	        <icon density="mdpi" src="res/icon/android/icon-48-mdpi.png" />
	        <icon density="hdpi" src="res/icon/android/icon-72-hdpi.png" />
	        <icon density="xhdpi" src="res/icon/android/icon-96-xhdpi.png" />
	    </platform>
	</widget>


这样为app设置启动图标，当然也可以放在其他目录，但是统一规范嘛！

## splash页面

位于`/res/screen`目录下，里面放置了各个不同系统的启动页

把splash图片放置在`/res/icon/android` 目录下，然后在 config 中修改


	<?xml version='1.0' encoding='utf-8'?>
	<widget id="com.hello.cordova" version="1.0.0" xmlns="http://www.w3.org/ns/widgets" xmlns:cdv="http://cordova.apache.org/ns/1.0">
	    <platform name="android">
	        <allow-intent href="market:*" />
	        <splash density="land-hdpi" src="res/screen/android/screen-hdpi.png" />
	        <splash density="land-ldpi" src="res/screen/android/screen-ldpi.png" />
	        <splash density="land-mdpi" src="res/screen/android/screen-mdpi.png" />
	        <splash density="land-xhdpi" src="res/screen/android/screen-xhdpi.png" />
	        
	        <splash density="port-hdpi" src="res/screen/android/screen-hdpi.png" />
	        <splash density="port-ldpi" src="res/screen/android/screen-ldpi.png" />
	        <splash density="port-mdpi" src="res/screen/android/screen-mdpi.png" />
	        <splash density="port-xhdpi" src="res/screen/android/screen-xhdpi.png" />
	    </platform>
	</widget>
	
	
为app设置不同屏幕的启动页，分别为横屏和竖屏状态下的图片

#### 显示条件

每次重新启动都显示

	 <preference name="SplashShowOnlyFirstTime" value="false" />

#### 时长

可为0，即不显示启动页

	<preference name="SplashScreenDelay" value="5000" />
	
#### 进度条

默认的splash中间有进度条显示，如果不需要可隐藏掉

	<preference name="ShowSplashScreenSpinner" value="false"/>
	
#### 渐变

默认的splash消失时有渐变效果，可隐藏掉

	<preference name="FadeSplashScreen" value="false"/>
	
当然也可以控制其时间

	<preference name="FadeSplashScreenDuration" value="1000"/>
	
#### 手动控制

通过查看 splash 插件源码得知，是可以手动去控制什么时候隐藏显示的

* 先将自动隐藏隐藏掉
	
		<preference name="AutoHideSplashScreen" value="false" />
		<preference name="FadeSplashScreen" value="false"/>
		
然后代码调用：

		navigator.splashscreen.show();
		navigator.splashscreen.hide();
		
## 屏幕


#### 横竖屏

设置横竖屏，三种可选 default  landscape   portrait

	<platform name="android">
	     <preference name="Orientation" value="sensorLandscape" />
	</platform>
		
#### 全屏

	<preference name="Fullscreen" value="true" />
	
#### 滚动条

隐藏

	<preference name="DisallowOverscroll" value="true"/>
	
#### 背景色

	<preference name="BackgroundColor" value="0xff0000ff"/>
	
#### 超链接

`cordova`默认点击超链接是跳转系统浏览器打开网页的，如果需要在当前app里面的打开网页，需要在`config.xml`里面添加

	<allow-navigation href="http://*/*" />

暂时就记下这么多吧，以后用到了再更新。



那么，我们下一章见！
