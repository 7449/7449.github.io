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

* react-native 

       
        create-react-native-app {projectname}
       
	   
* flutter

		
		flutter create {projectname}
		
		

#### 怎么运行Flutter应用

在`React-Native`中使用的是`react-native run-ios` or `react-native run-android`,
如果使用`WebStorm`则可以在`WebStorm`中直接启动

而在[flutter](https://flutter.io/get-started/)中，如果使用终端,则可以在项目的根目录下使用`flutter run` 指令
运行项目,如果使用`IDEA`,`Android Studio`,`VS Code`这种`IDE`则可以安装`flutter`插件直接在`IDE`运行程序,
这里推荐`Android Studio`,更多的资料可以参考[get-started](https://flutter.io/get-started/)

#### 导包

> 和`react-native`不同的是,`react-native`是需要什么导入什么,而`flutter`导入`material.dart`,就可以使用所有的`widget`,
`Dart`则只会引用使用的`widget`

* react-native


		import React from "react";
		import { StyleSheet, Text, View } from "react-native";


* flutter


		import 'package:flutter/material.dart';
		import 'package:flutter/cupertino.dart';
		import 'package:flutter/widgets.dart';
		import 'package:flutter/my_widgets.dart';

#### hello world

* react-native


		import React from "react";
		import { StyleSheet, Text, View } from "react-native";

		export default class HelloWorldApp extends React.Component {
		  render() {
			return (
			  <View>
				<Text>Hello World</Text>
			  </View>
			);
		  }
		}
		

* flutter


		import 'package:flutter/widgets.dart';

		void main() { 
		  runApp(
			new Center(
			  child: new Text(
				'Hello, world!',
				textDirection: TextDirection.ltr,
			  ),
			), 
		  );
		}


这个示例中,`flutter`使用了两个`widget`构成了一个`widget`树,分别是`Center`和`Text`.
在这种情况下，`Text`的显示方向是需要指定的，使用`MaterialApp`时，不需要特意指定,但是如果有更复杂的布局时，
可以创建`StatelessWidget`或者`StatefulWidget`，也可以把`MaterialApp`当作根布局,将其他的`widget`传递给`MaterialApp`即可
		
#### 嵌套Widgets形成Widget树

在开发过程中,通常会根据`widget`是否需要改变状态来创建新的`widget`，他们分别是[StatelessWidget](https://docs.flutter.io/flutter/widgets/StatelessWidget-class.html)或者[StatefulWidget](https://docs.flutter.io/flutter/widgets/StatefulWidget-class.html)
的子类，在`Hello World`示例中，`HelloWorldApp`类扩展了一个`StatelessWidget`并覆盖了一个构建函数，
该函数使用其他较低级别的`widget`来描述该`widget`

	import 'package:flutter/material.dart';

	void main() => runApp(new MyApp());

	class MyApp extends StatelessWidget {
	  @override
	  Widget build(BuildContext context) {
		return new MaterialApp(
		  home: new Scaffold(
			appBar: new AppBar(
			  title: new Text("Flutter"),
			),
			body: new Center(
			  child: new Text('Hello World'),
			),
		  )
		);
	  }
	}
	
在这个示例中,`widget`树由五个`widget`组成,分别是`MaterialApp`,`Scaffold`,`AppBar`,`Center`,`Text`,
在简单的布局中很容易就可以嵌套`widget`，但是如果是复杂的布局,建议通过封装或者其他方式来分离这些`widget`，
使其看起来比较容易理解

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/hello-world/flutterhelloworld/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/hello-world/rnhelloworld/App.js)相同功能的代码

#### 复用组件

在`React-Native`中可以通过创建新的组件并传递方法或者参数去封装或者复用

例如：

	class CustomCard extends React.Component {
	  render() {
		return (
		  <View>
			<Text > Card {this.props.index} </Text>
			<Button
			  title="Press"
			  onPress={() => this.props.onPress(this.props.index)}
			/>
		  </View>
		);
	  }
	}

	// Usage
	<CustomCard onPress={this.onPress} index={item.key} />
	
同样，在`Flutter`中，只需要创建一个类，然后像使用普通`widget`一样使用该类。

	class CustomCard extends StatelessWidget {
	  CustomCard({@required this.index, @required this.onPress});
	  
	  final index;
	  final Function onPress;
	  
	  @override
	  Widget build(BuildContext context) {
		return new Card(
		  child: new Column(
			children: <Widget>[
			  new Text('Card $index'),
			  new FlatButton(
				child: const Text('Press'),
				onPressed: this.onPress,
			  ),
			],
		  )
		);
	  }
	}
		...
	// Usage
	new CustomCard(
	  index: index,
	  onPress: () {
		print('Card $index');
	  },
	)
		...

`CustomCard`该类的构造函数显示了`Dart`特性:

构造函数中的花括号表示参数在初始化时是可选的。
为了使这些字段成为必需，
我们有两个办法 

* 从构造函数中删除大括号
* 添加`@required`到构造函数中

后一种方法可以在开发中让`IDE`提供参数名,推荐使用

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/modular/fluttermodular/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/modular/rnmodular/App.js)相同功能的代码

## 项目结构 and 资源

#### 第一行代码

`React-Native`是在`App.js`中编写第一行代码,而`Flutter`则是在`packageName/lib/main.dart`并从主函数开始执行.

最小的`Flutter`只需要为`runApp`提供一个`widget`即可，任何`widget`都可以传递给`runApp`，
如果项目是符合`material design`那么使用`MaterialApp`作为`widget`树是一个不错的选择,
如果新创建一个页面,那么可以使用`Scaffold`实现基本的`material design`设计和可视化布局，当然这个不是必须的

`Flutter`的文件目录结构层次

	┬
	└ projectname
	  ┬
	  ├ android
	  ├ build
	  ├ ios
	  ├ lib
		┬
		└ main.dart
	  ├ test
	  └ pubspec.yaml
	  
* projectname/android ： `android`项目

* projectname/build ： `android`和`ios`构建的`build`文件

* projectname/ios ： `ios`项目

* projectname/lib ： 开发目录

* projectname/lib/main.dart ： 入口

* projectname/test ： 自动化测试文件

* projectname/pubspec.yaml ： 等同于`package.json`

#### 本地文件

在`react-native`中引用本地图片如下所示：

	<Image source={require("./my-icon.png")} />

`flutter`的任何资源文件都可以随意放在根目录下,但是为了规范起见,建议放在`assets`文件夹下

然后在`pubspec.yaml`中声明这些引用

	flutter:
	  assets:
		- assets/my_icon.png
		- assets/background.png
		
使用如下：

	image: new AssetImage('assets/background.png')

#### 网络加载图片

在`React-Native`中，可以在`Image`的`source`指定`uri`，并在Image需要时提供大小

在`Flutter`中使用`NetworkImage`即可

	new NetworkImage("https://example.com/images/background.png"),

#### 安装插件和软件包

`flutter`无法像`react-native`这样`yarn add`或者`npm install --save`安装依赖，要在`flutter`中使用依赖必须要按照以下步骤：

* 将软件包名称和版本添加到软件包的`pubspec.yaml`依赖项中


		dependencies:
		  flutter:
			sdk: flutter
		  google_sign_in: "^2.0.1"

		  
* 通过运行从命令行安装软件包`flutter packages get`

* 导包


		import 'package:google_sign_in/google_sign_in.dart';
	
	
## 内置的Widgets

·`widget`本身通常由许多小的，单一用途的`widget`组成，这些`widget`结合起来产生强大的效果

#### rn-view

`react-native`中.`view`是一个支持`Flexbox` `style` `touch` `accessibility`的容器

在`Flutter`中，存在特殊属性的小部件，如`container`，`column`，`row`，`center`等。
还可以使用更高级的`widget`，例如`Scaffold`，它提供了用于显示`drawers`，` snack bars`和`bottom sheets`
，高级`widget`是低级`widget`组合而成的

更多的信息可以查阅[layout](https://flutter.io/widgets/layout/)

#### rn-FlatList-ListView

在`react-native`中使用列表：

	<FlatList
	  data={[ ... ]}
	  renderItem={({ item }) => <Text>{item.key}</Text>}
	/>

而在`flutter`中`ListView`可以渲染一个`Widget`列表,当然`ListView`本身适用于数据量不多的列表,如果数据量很大的话
，推荐使用`ListView.Builder`,`Builder`需要两个参数,第一个是默认列表的长度,第二个是需要返回的`Widget`

	var data = [ ... ];
	new ListView.builder(
	  itemCount: data.length,
	  itemBuilder: (context, int index) {
		return new Text(
		  data[index],
		);
	  },
	)
	
在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/scrollable/flutterscrollable/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/scrollable/rnscrollable/App.js)相同功能的代码

#### 使用Canvas绘制 

`react-native`中并不存在`canvas`组件,所以需要的时候需要借助`react-native-canvas`去绘制

	handleCanvas = canvas => {
	  const ctx = canvas.getContext("2d");
	  ctx.fillStyle = "skyblue";
	  ctx.beginPath();
	  ctx.arc(75, 75, 50, 0, 2 * Math.PI);
	  ctx.fillRect(150, 100, 300, 300);
	  ctx.stroke();
	};

	render() {
	  return (
		<View>
		  <Canvas ref={this.handleCanvas} />
		</View>
	  );
	}
	
而在`flutter`中可以使用[CustomPaint](https://docs.flutter.io/flutter/widgets/CustomPaint-class.html)进行绘制

实现[CustomPainter](https://docs.flutter.io/flutter/rendering/CustomPainter-class.html)并将其传递给`widget`中的`painter`

	class MyCanvasPainter extends CustomPainter {

	  @override
	  void paint(Canvas canvas, Size size) {
		Paint paint = new Paint();
		paint.color = Colors.amber;
		canvas.drawCircle(new Offset(100.0, 200.0), 40.0, paint);
		Paint paintRect = new Paint();
		paintRect.color = Colors.lightBlue;
		Rect rect = new Rect.fromPoints(new Offset(150.0, 300.0), new Offset(300.0, 400.0));
		canvas.drawRect(rect, paintRect);
	  }
	  
	  bool shouldRepaint(MyCanvasPainter oldDelegate) => false;
	  bool shouldRebuildSemantics(MyCanvasPainter oldDelegate) => false;
	}
	class _MyCanvasState extends State<MyCanvas> {

	  @override
	  Widget build(BuildContext context) {
		return new Scaffold(
		  body: new CustomPaint(
			painter: new MyCanvasPainter(),
		  ),
		);
	  }
	}

	
在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/canvas/fluttercanvas/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/canvas/rncanvas/App.js)相同功能的代码

#### 设置布局位置

`react-native`中通常通过设置`style`确定布局的位置

	<View
	  style={{
		flex: 1,
		flexDirection: "column",
		justifyContent: "space-between",
		alignItems: "center"
	  }}
	>

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Layout_sample/basiclayout_sample/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Layout_sample/rnlayout/App.js)相同功能的代码

#### 重叠小组件

#### 自定义组件

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/styling/flutterstyling/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/styling/rnstyling/App.js)相同功能的代码

#### Icons和Colors

#### 样式和主题

## 管理State 

#### 有无状态的`widget`

#### 使用`widget`的最佳实践

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/State_sample/flutter_basic_statesample/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/State_sample/reactnative-state-sample/App.js)相同功能的代码

#### Props

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/modular/fluttermodular/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/modular/rnmodular/App.js)相同功能的代码

#### 本地储存

## Routing

#### Flutter的页面跳转

#### Tab导航和Drawer导航

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Navigation_example/flutternavigation/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Navigation_example/reactnative-navigation-example/App.js)相同功能的代码

## 手势和触摸

#### 点击

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/gestures/fluttergestures/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/gestures/rngestures/App.js)相同功能的代码

## 网络请求

#### 调用api

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/api-calls/flutterapicalls/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/api-calls/rnapicalls/App.js)相同功能的代码

## 输入框

#### rn-TextInput

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/input-fields/flutterinputfields/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/input-fields/rninputfields/App.js)相同功能的代码

## 判断系统

## 调试

#### Developer Menu

#### 热加载

#### Chrome开发者工具

## 动画

#### 淡入动画

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/animations/flutterfade/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/animations/rnfade/App.js)相同功能的代码

#### 滑动动画

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/animations/fluttercardswipe/lib/main.dart)代码