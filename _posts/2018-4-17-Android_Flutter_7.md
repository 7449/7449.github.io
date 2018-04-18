---
layout:     post
title:      "Flutter系列--第七章:面对ReactNative开发的Flutter说明"
subtitle:   "Flutter 与ReactNative开发的区别"
date:       2018-4-17
author:     "y"
header-mask: 0.3
header-img: "img/header_flutter.png"
catalog: true
tags:
    - android
    - React-Native
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
[第六章：面对Android开发的Flutter说明](https://7449.github.io/2018/04/16/Android_Flutter_6/)<br>

## flutter与react-native的区别

本篇文章主要针对的是熟练`react-native`开发的一些人员

注： 以`rn`开头的标题,都是讲了在`flutter`里面等价于什么
    例如`rn-view`表示在`flutter`里面什么东西相同与`react-native`里面的`view`


## Dart简介

> `React-Native`采用的是`JavaScript`,但是`flutter`采用的是[Dart](https://www.dartlang.org/)，`Dart`是一个可扩展开源的编程语言。
用于构建web,服务器，和移动应用程序,是一种面向对象的单一继承语言，它使用`AOT`编译为本地语言的`C`风格语法，
并可选择将其翻译为`JavaScript`。
它支持接口，抽象类和强类型

注： 虽然讲的很好,但是就目前为止,我还是不太喜欢`Dart`编程,希望以后发力

#### 入口

`JavaScript`没有任何特定的入口函数，但`Dart`有一个入口函数`main()`

* JavaScript 


		function main() {
		  // Can be used as entry point
		}
		// but it has to be called manually.
		main();
	
	
* Dart


		main() {
		}

	
在[DartPad](https://dartpad.dartlang.org/0df636e00f348bdec2bc1c8ebc7daeb1)中试用

#### 输出控制台

* JavaScript 
	
	
		console.log("Level completed.");
	
	
* Dart


		print('Hello World');

		
在[DartPad](https://dartpad.dartlang.org/cf9e652f77636224d3e37d96dcf238e5)中试用

#### 变量

> 有关更多变量的问题可以查看[变量](https://www.dartlang.org/guides/language/language-tour)文档

###### 创建和分配变量

在`JavaScript`中是没有变量类型的，但是在`Dart`中存在变量类型，创建变量的时候指定类型是一种很好的做法。
在[dart-2](https://www.dartlang.org/dart-2)中,变量在声明的时候必须是明确的类型,或者变量必须是系统自己能自动推断的类型

* JavaScript 
	
	
		var name = "JavaScript";
	
	
* Dart


		String name = 'dart';
		var otherName = 'Dart'; // Also inferred to be a String in Strong mode.
		// Both are acceptable in Dart.

		
在[DartPad](https://dartpad.dartlang.org/3f4625c16e05eec396d6046883739612)中试用

###### 默认值

在`Dart`中，未初始化的变量的初始值为`null`，即使`numeric`变量最初也为`null`,因为`numeric`是`Dart`中的对象，
但在`JavaScript`中，未初始化的变量是`undefined`

* JavaScript 
	
	
		var name; // == undefined
	
	
* Dart


		var name; // == null
		int x; // == null

		
在[DartPad](https://dartpad.dartlang.org/57ec21faa8b6fe2326ffd74e9781a2c7)中试用

#### 检查 null/zero

在`Dart`中,只有`bool`会被识别为`true` 或者 `false`，但是在`JavaScript`中,除了`bool`还有
像`1`或者任何非空的变量都会被认为是一个`bool`

* JavaScript 
	
	
		var myNull = null;
		if (!myNull) {
		  console.log("null is treated as false");
		}
		var zero = 0;
		if (!zero) {
		  console.log("0 is treated as false");
		}
	
	
* Dart


		var myNull = null;
		if (myNull == null) {
		  print('use "== null" to check null');
		}
		var zero = 0;
		if (zero == 0) {
		  print('use "== 0" to check zero');
		}
		
在[DartPad](https://dartpad.dartlang.org/c85038ad677963cb6dc943eb1a0b72e6)中试用

#### 方法

> 更多方法可以查阅[方法](https://www.dartlang.org/resources/dart-tips/dart-tips-ep-6)文档

大多数情况下`Dart`和`JavaScript`的方法是相同的,它们两个唯一的不同在于方法的声明

* JavaScript 
	
	
		function fn() {
		  return true;
		}
	
	
* Dart


		fn() {
		  return true;
		}
		// can also be written as
		bool fn() {
		  return true;
		}
				
在[DartPad](https://dartpad.dartlang.org/5454e8bfadf3000179d19b9bc6be9918)中试用

#### 异步

###### Futures

> 更多的信息可以查阅[futures](https://www.dartlang.org/tutorials/language/futures)文档

和`JavaScript`一样，`Dart`支持单线程执行。
在`JavaScrip`t中，`Promise`对象表示异步操作及其结果值的最终完成（或失败）

	_getIPAddress = () => {
	  const url="https://httpbin.org/ip";
	  return fetch(url)
		.then(response => response.json())
		.then(responseJson => {
		  console.log(responseJson.origin);
		})
		.catch(error => {
		  console.error(error);
		});
	};

但是在`Dart`中使用[futures](https://www.dartlang.org/tutorials/language/futures)来处理这个问题

	_getIPAddress() {
	  final url = 'https://httpbin.org/ip';
	  HttpRequest.request(url).then((value) {
		  print(json.decode(value.responseText)['origin']);
	  }).catchError((error) => print(error));
	}
	
在[DartPad](https://dartpad.dartlang.org/b68eb981456c5eec03daa3c05ee59486)中试用

###### async/await

> 更多的信息可以查阅[await-async](https://www.dartlang.org/articles/language/await-async)文档

在`JavaScript`中，当调用异步函数时，它会返回一个`Promise`,
`await`则是用来等待`Promise`

	async _getIPAddress() {
	  const url="https://httpbin.org/ip";
	  const response = await fetch(url);
	  const json = await response.json();
	  const data = await json.origin;
	  console.log(data);
	}

而在`Dart`中`async`返回一个`Future`，函数被安排在稍后执行,
`await`则是用来等待`Future`

	_getIPAddress() async {
	  final url = 'https://httpbin.org/ip';
	  var request = await HttpRequest.request(url);
	  String ip = json.decode(request.responseText)['origin'];
	  print(ip);
	}

在[DartPad](https://dartpad.dartlang.org/96e845a844d8f8d91c6f5b826ef38951)中试用

## Flutter基础知识

#### 创建Flutter应用

#### 怎么运行Flutter应用

#### 导包

#### hello world

#### 嵌套Widgets形成Widget树

#### 复用组件

## 项目结构 and 资源

#### 第一行代码

#### 构建文件

#### 资源

#### 网络加载图片

#### 安装插件和软件包

## 内置的Widgets

#### rn-view

#### rn-FlatList-ListView

#### 使用Canvas绘制 

#### 设置布局位置

#### 重叠小组件

#### 自定义组件

#### Icons和Colors

#### 样式和主题

## 管理State 

#### 有无状态的小部件

#### 使用小部件的最佳实践

#### Props

#### 本地储存

## Routing

#### Flutter的页面跳转

#### Tab导航和Drawer导航

## 手势和触摸

#### 点击

## 网络请求

#### 调用api

## 输入框

#### rn-TextInput

## 判断系统

## 调试

#### Developer Menu

#### 热加载

#### Chrome开发者工具

## 动画

#### 淡入动画

#### 滑动动画

