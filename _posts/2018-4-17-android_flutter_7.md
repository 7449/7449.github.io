---
layout:     post
title:      "Flutter系列--第七章:面对ReactNative开发的Flutter说明"
subtitle:   "Flutter 与ReactNative开发的区别"
date:       2018-4-17
tags:
    - android
    - React-Native
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

在`Flutter`中，存在特殊属性的小部件，如`Container`，`Column`，`Row`，`Center`等。
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
	  style={
		Flex: 1,
		FlexDirection: "column",
		JustifyContent: "space-between",
		AlignItems: "center"
	  }
	>
	
与`React-Native`的做法相反，在`flutter`中，大部分布局都是通过使用专门用于提供布局的`widget`完成,
`flutter`的`Column`和`Row`获取一组`widget`，而不是样式属性（尽管有诸如`CrossAxisAlignment`和`direction`），并分别垂直和水平地对齐它们。

例如，如果想指定布局是水平还是垂直的，在`react-native`中需要指定`flexDirection: “column”`,
但在`flutter`中，使用一个`Column`并提供所需的`widget`即可

	new Center(
	  child: new Column(
		children: <Widget>[
		  new Container(
			color: Colors.red,
			width: 100.0,
			height: 100.0,
		  ),
		  new Container(
			color: Colors.blue,
			width: 100.0,
			height: 100.0,
		  ),
		  new Container(
			color: Colors.green,
			width: 100.0,
			height: 100.0,
		  ),
		],
	  ),
	)

有关其他的`widget`:[Padding](https://docs.flutter.io/flutter/widgets/Padding-class.html)，[Align](https://docs.flutter.io/flutter/widgets/Align-class.html)，[Stack](https://docs.flutter.io/flutter/widgets/Stack-class.html)

更多资料可以查阅[widget](https://flutter.io/widgets/layout/)的文档。
	
在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Layout_sample/basiclayout_sample/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Layout_sample/rnlayout/App.js)相同功能的代码

#### 重叠小组件

`react-native`中可以使用`absolute`重叠不同的组件,而`flutter`使用[Stack](https://docs.flutter.io/flutter/widgets/Stack-class.html)
实现`widget`的重叠,`Stack`将它的子`widget`定位于它的边缘，如果只是想重叠几个子`widget`，这个类很有用。


示例：

`Stack`叠加一个`Container`（在半透明的黑色背景上显示Text）和`CircleAvatar`,
`Stack`使用`alignment`和`Alignments`属性偏移`Text`

	new Stack(
	  alignment: const Alignment(0.6, 0.6),
	  children: <Widget>[
		new CircleAvatar(
		  backgroundImage: new NetworkImage(
			"https://avatars3.githubusercontent.com/u/14101776?v=4"),
		),
		new Container(
		  decoration: new BoxDecoration(
			  color: Colors.black45,
		  ),
		  child: new Text('Flutter'),
		),
	  ],
	)

#### 自定义组件

`react-native`中可以通过以下方式自定义组件

	<View style={styles.container}>
	  <Text style={ fontSize: 32, color: "cyan", fontWeight: "600" }>
		This is a sample text
	  </Text>
	</View>
	
	const styles = StyleSheet.create({
	  container: {
		flex: 1,
		backgroundColor: "#fff",
		alignItems: "center",
		justifyContent: "center"
	  }
	});
	
与其类似的`flutter`存在一些特定组件[Padding](https://docs.flutter.io/flutter/widgets/Padding-class.html),[Center](https://docs.flutter.io/flutter/widgets/Center-class.html),[Card](https://docs.flutter.io/flutter/material/Card-class.html),[Stack](https://docs.flutter.io/flutter/widgets/Stack-class.html)
,`Text`可以使用`TextStyle`,如果在多个地方使用相同的样式,则可以创建一个`TextStyle`类使用

	var textStyle = new TextStyle(fontSize: 32.0, color: Colors.cyan, fontWeight: FontWeight.w600);
		...
	new Center(
	  child: new Column(
		children: <Widget>[
		  new Text(
			'Sample text',
			style: textStyle,
		  ),
		  new Padding(
			padding: new EdgeInsets.all(20.0),
			child: new Icon(Icons.lightbulb_outline,
			  size: 48.0, color: Colors.redAccent)
		  ),
		],
	  ),
	)

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/styling/flutterstyling/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/styling/rnstyling/App.js)相同功能的代码

#### Icons和Colors

`react-native`中没有内置图标库,所以必须使用第三方库,但是`flutter`内置了`Material`和`Cupertino`，
如果想使用`Material`请使用[Icons](https://docs.flutter.io/flutter/material/Icons-class.html),
否则使用[CupertinoIcons](https://docs.flutter.io/flutter/cupertino/CupertinoIcons-class.html)，
需要注意的是,请确保在项目的`pubspec.yaml`文件中添加对`cupertino_icons`的依赖

[Icons-class](https://docs.flutter.io/flutter/material/Icons-class.html#constants):这个是所有的`Material`图标列表

对于颜色,`flutter`内置了[Colors](https://docs.flutter.io/flutter/material/Colors-class.html),
`Colors`采用的是[material](https://material.io/guidelines/style/color.html)风格的颜色

	new Icon(Icons.lightbulb_outline, color: Colors.redAccent)

#### 样式和主题

在`React-Native`中，通常定义一些常用主题应用在组件上,
同样，在`flutter`中，可以通过在[ThemeData](https://docs.flutter.io/flutter/material/ThemeData-class.html)
来添加统一的主题以及属性

示例：

`MaterialApp`包装所有的`widget`并充当应用程序的根`widget`。

	@override
	Widget build(BuildContext context) {
	  return new MaterialApp(
		theme: new ThemeData(
		  primaryColor: Colors.cyan,
		  brightness: Brightness.dark,
		),
		home: new StylingPage(),
	  );
	}

即使不使用`MaterialApp`，也可以设置`Theme`, 需要指定`ThemeData`在它的`data`属性，
然后会在它的所有子`widget`设置对应的`ThemeData`

示例：

	@override
	 Widget build(BuildContext context) {
	   return new Theme(
		 data: new ThemeData(
		   primaryColor: Colors.cyan,
		   brightness: brightness,
		 ),
		 child: new Scaffold(
			backgroundColor: Theme.of(context).primaryColor,
				 ...
				 ...
		 ),
	   );
	 }

## 管理State 

> 什么是`StatefulWidget`和`StatelessWidget`，应该怎么使用？

#### 有无状态的`widget`

在`react-native`中，没有特定`setState`的组件，使用者可以在任何组件调用`setState`

	export default class BlinkApp extends Component {

	  constructor(props) {
		super(props);
		this.state = {
		  buttonText: "blink",
		  showText: true,
		  text: "I love blinking"
		};
		this.toggleBlinkState = this.toggleBlinkState.bind(this);
		var handle;
	  }
	  
	  toggleBlinkState() {
		if (this.state.buttonText == "blink") {
		  handle = setInterval(() => {
			this.setState(previousState => {
			  return { showText: !previousState.showText };
			});
		  }, 1000);
		  this.setState({
			buttonText: "Stop blinking"
		  });
		} else {
		  clearInterval(handle);
		  this.setState({ buttonText: "blink", showText: true });
		}
	  }

	  render() {
		let display = this.state.showText ? this.state.text : " ";
		return (
		  <View style={ flex: 1, justifyContent: "center", alignItems: "center" }>
			<StatusBar hidden={true} />
			<Text>{display} </Text>
			<Button onPress={this.toggleBlinkState} title={this.state.buttonText} />
		  </View>
		);
	  }
	}

在`flutter`中只有[StatefulWidget](https://docs.flutter.io/flutter/widgets/StatefulWidget-class.html)才可以使用`setState`

###### StatelessWidget

无状态的`widget`，没有状态,无法`setState`，例如[Icons](https://docs.flutter.io/flutter/material/Icons-class.html),[IconButton](https://docs.flutter.io/flutter/material/IconButton-class.html),[Text](https://docs.flutter.io/flutter/widgets/Text-class.html)
,这几个`widget`就是`StatelessWidget`的子部件,都是继承自`StatelessWidget`

`StatelessWidget`适用于一些没有变化,也无需变化的地方,例如首页闪屏的`Image`，或者一些特定地方的内容`Text`

	import 'package:flutter/material.dart';

	void main() => runApp(new MyStatelessWidget(text: "StatelessWidget Example to show immutable data"));

	class MyStatelessWidget extends StatelessWidget {
	  final String text;
	  MyStatelessWidget({Key key, this.text}) : super(key: key);

	  @override
	  Widget build(BuildContext context) {
		return new Center(
		  child: new Text(
			text,
			textDirection: TextDirection.ltr,
		  ),
		);
	  }
	}
	
`MyStatelessWidget`继承自`StatelessWidget`，可以存在不可变的数据,
所以上面的示例可以看到`MyStatelessWidget`的构造里面存在一个`text`是`final`修饰的.

`StatelessWidget`的构建方法通常在以下三种情况下调用：

* 当`widget`插入到`widget tree`中时

* `widget`的上级发生了变化

* [InheritedWidget](https://docs.flutter.io/flutter/widgets/InheritedWidget-class.html)发生变化时

###### StatefulWidget

`StatefulWidget`是一个可以通过`setState`改变状态的`widget`,`State`可以在构建的时候同步读取到的信息,并且在`widget`的生命周期中也可能发生改变,
可以在`state`发生改变的时候通知`StatefulWidget`，然后根据不同的状态反馈给使用者不同的视觉交互，例如滑动滑块,表单输入，网络请求显示进度
[Checkbox](https://docs.flutter.io/flutter/material/Checkbox-class.html),[Radio](https://docs.flutter.io/flutter/material/Radio-class.html),[Slider](https://docs.flutter.io/flutter/material/Slider-class.html),[InkWell](https://docs.flutter.io/flutter/material/InkWell-class.html),[Form](https://docs.flutter.io/flutter/widgets/Form-class.html),[TextField](https://docs.flutter.io/flutter/material/TextField-class.html)
都是继承自`StatefulWidget`

> 自定义一个有状态的`widget`，通过`createState`创建这个`widget`的状态

	class MyStatefulWidget extends StatefulWidget {
	  MyStatefulWidget({Key key, this.title}) : super(key: key);
	  final String title;
	  
	  @override
	  _MyStatefulWidgetState createState() => new _MyStatefulWidgetState();
	}

下面这个示例构建一个可以改变状态的`widget`，点击之后会启动一个`Timer`，定时通过`setState`修改`showtext`,
然后返回不同的`widget`显示在屏幕上,其实这个示例就已经开始体现出了`Dart`的繁琐,不信看看有多少个括号

	class _MyStatefulWidgetState extends State<MyStatefulWidget> {
	  bool showtext=true;
	  bool toggleState=true;
	  Timer t2;
	  
	  void toggleBlinkState(){
		setState((){ 
		  toggleState=!toggleState;
		});
		var twenty = const Duration(milliseconds: 1000);
		if(toggleState==false) {
		  t2 = new Timer.periodic(twenty, (Timer t) {
			toggleShowText();
		  });
		} else {
		  t2.cancel();
		}
	  }
	  
	  void toggleShowText(){
		setState((){
		  showtext=!showtext;
		});
	  }
	  
	  @override
	  Widget build(BuildContext context) {
		return new Scaffold(
		  body: new Center(
			child: new Column(
			  children: <Widget>[
				(showtext
				  ?(new Text('This execution will be done before you can blink.'))
				  :(new Container())
				),
				new Padding(
				  padding: new EdgeInsets.only(top: 70.0),
				  child: new RaisedButton(
					onPressed: toggleBlinkState,
					child: (toggleState
					  ?( new Text("Blink")) 
					  :(new Text("Stop Blinking"))
					)
				  )
				)
			  ],
			),
		  ),
		);
	  }
	}

#### 使用`widget`的最佳实践

###### 区分出`widget`是否存在状态

简单的判断出来`widget`是否存在状态.
如果一个`widget`存在交互,能发生改变,那么就是有状态的,反之则可能是无状态的,
(这里使用可能字样是因为有可能使用者在使用过程中不细心或者懒得注意细节而统统使用了`StatefulWidget`，相信这样的人大有人在)
，建议根据`UI`选择不同的`widget`，而不是为了方便统统使用`StatefulWidget`

###### 使用`Stateful`时决定哪个`widget`管理`state`

管理`state`有三种方式：

* 自己内部管理
* 上级`widget`管理`state`
* 两者混用

下面这几个原则可以帮助使用者选择哪种方式：

* 如果状态由上层决定,例如`checkbox`，那么应该由上层决定子类的`state`
* 如果是动画之类的,那么`state`由自己控制即可
* 如果是在搞不清楚,那么就由上层控制子类的`state`即可

###### 子类`StatefulWidget`和`state`

`MyStatefulWidget`通过重写`createState()`来管理自己的`state`
而`createState()`在构建`widget`时调用,在这个示例中，`createState()`创建一个`_MyStatefulWidgetState`实例

	class MyStatefulWidget extends StatefulWidget {
	  MyStatefulWidget({Key key, this.title}) : super(key: key);
	  final String title;
	  
	  @override
	  _MyStatefulWidgetState createState() => new _MyStatefulWidgetState();
	}

	class _MyStatefulWidgetState extends State<MyStatefulWidget> {
	  
	  @override
	  Widget build(BuildContext context) {
		...
	  }
	}

###### 将`StatefulWidget`插入到`widget tree`中

使用自定义的`widget`

	class MyStatelessWidget extends StatelessWidget {
	  // This widget is the root of your application.
	  
	  @override
	  Widget build(BuildContext context) {
		return new MaterialApp(
		  title: 'Flutter Demo',
		  theme: new ThemeData(
			primarySwatch: Colors.blue,
		  ),
		  home: new MyStatefulWidget(title: 'State Change Demo'),
		);
	  }
	}

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/State_sample/flutter_basic_statesample/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/State_sample/reactnative-state-sample/App.js)相同功能的代码

#### Props

`react-native`中`props`用于上层`widget`传递数据给子`widget`，子`widget`使用`this.props.xx`即可使用上层传递过来的数据以及回调之类的
，相比于上面的`state`，`props`是上层传递给子`widget`的,而`state`，是自己内部维护的一个状态

	class CustomCard extends React.Component {
	  render() {
		return (
		  <View>
			<Text> Card {this.props.index} </Text>
			<Button
			  title="Press"
			  onPress={() => this.props.onPress(this.props.index)}
			/>
		  </View>
		);
	  }
	}
	class App extends React.Component {
	  
	  onPress = index => {
		console.log("Card ", index);
	  };
	  
	  render() {
		return (
		  <View>
			<FlatList
			  data={[ ... ]}
			  renderItem={({ item }) => (
				<CustomCard onPress={this.onPress} index={item.key} />
			  )}
			/>
		  </View>
		);
	  }
	}
	
而在`flutter`中,只需要用`final`修饰一个变量或者方法,并在构造方法中传递过去即可

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
		));
	  }
	}
		...
	//Usage
	new CustomCard(
	  index: index,
	  onPress: () {
		print('Card $index');
	  },
	)

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/modular/fluttermodular/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/modular/rnmodular/App.js)相同功能的代码

#### 本地储存

`react-native`中可以使用`AsyncStorage`，或者数据库`realm`进行数据存储

	await AsyncStorage.setItem( "counterkey", json.stringify(++this.state.counter));
	AsyncStorage.getItem("counterkey").then(value => {
	  if (value != null) {
		this.setState({ counter: value });
	  }
	});
	
这里推荐使用`Redux` 和 `persist`解决持久化数据.

而在`flutter`中可以使用[shared_preferences](https://github.com/flutter/plugins/tree/master/packages/shared_preferences)
,首先在`pubspec.yaml`中依赖：

	dependencies:
	  flutter:
		sdk: flutter
	  shared_preferences: "^0.3.0"

导包：

	import 'package:shared_preferences/shared_preferences.dart';

获取实例并使用：

	SharedPreferences prefs = await SharedPreferences.getInstance();
	_counter=prefs.getInt('counter');
	prefs.setInt('counter',++_counter);
	setState(() {
	  _counter=_counter;
	});
	  
## Routing

#### Flutter的页面跳转

在`react-native`中使用`react-navigation`(社区维护,官方建议),会有三个导航器：`StackNavigator`,`TabNavigator`,`DrawerNavigator`

示例如下：

	const MyApp = TabNavigator(
	  { Home: { screen: HomeScreen }, Notifications: { screen: tabNavScreen } },
	  { tabBarOptions: { activeTintColor: "#e91e63" } }
	);
	const SimpleApp = StackNavigator({
	  Home: { screen: MyApp },
	  stackScreen: { screen: StackScreen }
	});
	export default (MyApp1 = DrawerNavigator({
	  Home: {
		screen: SimpleApp
	  },
	  Screen2: {
		screen: drawerScreen
	  }
	}));

在`flutter`中,[Route](https://docs.flutter.io/flutter/widgets/Route-class.html)指的app的屏幕或者页面,而[Navigator](https://docs.flutter.io/flutter/widgets/Navigator-class.html)则是管理`route`的`widget`,用于管理一堆具有堆栈规则的`child widget`
,`Navigator`管理一堆`route`，并提供一些方法,例如`Navigator.push`和`Navigator.pop`

示例：

	class NavigationApp extends StatelessWidget {
	  // This widget is the root of your application.
	  @override
	  Widget build(BuildContext context) {
		return new MaterialApp(
				...
		  routes: <String, WidgetBuilder>{
			'/a': (BuildContext context) => new usualNavscreen(),
			'/b': (BuildContext context) => new drawerNavscreen(),
		  }
				...
	  );
	  }
	}

如果想跳转页面,可以使用`Navigator`的`pushNamed`方法,将`context`和`routeName`传递给`Navigator`即可
	
	Navigator.of(context).pushNamed('/a');

也可以使用`push`,需要提供`context`和`route`，将给定的`route`添加到最靠近给定`context`的`Navigator`的历史记录
，并跳转到对应的页面

在示例中，使用的是`MaterialPageRoute`，`MaterialPageRoute`是一种模态路由，
可以通过平台自适应过渡来切换屏幕,想要使用必需提供`WidgetBuilder`。

	Navigator.push(context, new MaterialPageRoute(builder: (BuildContext context) => new UsualNavscreen()));

#### Tab导航和Drawer导航

`react-native`可以使用`TabNavigator`和`DrawerNavigator`实现抽屉和`tab`效果

在`flutter`中使用了几个较低级别的`widget`，例如[Drawer](https://docs.flutter.io/flutter/material/Drawer-class.html)
就是一个可以水平滑动的符合`md`的[TabBar](https://docs.flutter.io/flutter/material/TabBar-class.html)
，[TabBarView](https://docs.flutter.io/flutter/material/TabBarView-class.html)用于显示`tab`和导航中的标签，
这些较低级别的`widget`与[Scaffold](https://docs.flutter.io/flutter/material/Scaffold-class.html)结合使用。
 `Scaffold`的`Drawer`和`bottomNavigationBar`参数则由上面提到的低级别的`widget`提供

###### Tab

react-native

	const MyApp = TabNavigator(
	  { Home: { screen: HomeScreen }, Notifications: { screen: tabNavScreen } },
	  { tabBarOptions: { activeTintColor: "#e91e63" } }
	);
	
flutter

在`flutter`中主要使用[TabBar](https://docs.flutter.io/flutter/material/TabBar-class.html)和[TabBarView](https://docs.flutter.io/flutter/material/TabBarView-class.html)
实现，`TabBar`用于创建`TabBar`，[Tab](https://docs.flutter.io/flutter/material/Tab-class.html)作为子项传递给`TabBar`

示例：

    TabController controller=new TabController(length: 2, vsync: this);
    
    new TabBar(
      tabs: <Tab>[
        new Tab(icon: new Icon(Icons.person),),
        new Tab(icon: new Icon(Icons.email),),
      ],
      controller: controller,
    ),


注：

必须要传递[TabController](https://docs.flutter.io/flutter/material/TabController-class.html),`TabController`是用来协调[TabBar](https://docs.flutter.io/flutter/material/TabBar-class.html)和[TabBarView](https://docs.flutter.io/flutter/material/TabBarView-class.html)的,`TabController`有个参数`length`的作用是表明了一共有多少个`Tab`，`vsync`一般使用[TickerProvider](https://docs.flutter.io/flutter/scheduler/TickerProvider-class.html)即可，`TickerProvider`需要实现一个[Ticker](https://docs.flutter.io/flutter/scheduler/Ticker-class.html)(这个可以通过看源码得知),任何想要在帧进行中被通知的对象都可以用`TickerProvider`,但一般都是通过[AnimationController](https://docs.flutter.io/flutter/animation/AnimationController-class.html)使用，`AnimationControllers`需要一个`TickerProvider`来获取`Ticker`，
如果想创建一个`AnimationController`，一般使用[TickerProviderStateMixin](https://docs.flutter.io/flutter/widgets/TickerProviderStateMixin-class.html)和[SingleTickerProviderStateMixin](https://docs.flutter.io/flutter/widgets/SingleTickerProviderStateMixin-class.html)就行了

示例：

`Scaffold`的`body`由`TabBarView`实现，
与`TabBar`的`Tab`相对应的所有页面都在`TabBarView`里面一起提供,并且两个`widget`都要提供`TabController`

    class _NavigationHomePageState extends State<NavigationHomePage> with SingleTickerProviderStateMixin {
      TabController controller=new TabController(length: 2, vsync: this);
      @override
      Widget build(BuildContext context) {
        return new Scaffold(
          bottomNavigationBar: new Material (
            child: new TabBar(
              tabs: <Tab> [
                new Tab(icon: new Icon(Icons.person),),
                new Tab(icon: new Icon(Icons.email),),
              ],
              controller: controller,
            ),
            color: Colors.blue,
          ),
          body: new TabBarView(
            children: <Widget> [
              new home.homeScreen(),
              new tabScreen.tabScreen()
            ],
            controller: controller,
          )
        );
      }
    }

###### Drawer

react-native 

    export default (MyApp1 = DrawerNavigator({
      Home: {
        screen: SimpleApp
      },
      Screen2: {
        screen: drawerScreen
      }
    }));
    
flutter

在`flutter`中，使用[Drawer](https://flutter.io/flutter-for-react-native/)，它符合`material design`，可以从`Scaffold`边缘水平滑动，
以便在应用程序中显示导航链接,使用者可以提供一个`Button`，一个`Text`或`ListView`显示在抽屉里面

在示例中，使用了[ListTile](https://docs.flutter.io/flutter/material/ListTile-class.html)并提供了导航

    new Drawer(
      child:new ListTile(
        leading: new Icon(Icons.change_history),
        title: new Text('Screen2'),
        onTap: () {
          Navigator.of(context).pushNamed("/b");
        },
      ),
      elevation: 20.0,
    ),

`Drawer`通常与`Scaffold.drawer`属性一起使用,`AppBar`会自动显示`IconButton`以便`Scaffold`在适合的地方显示`Drawer`,
而`Scaffold`会自动处理手势

    @override
    Widget build(BuildContext context) {
      return new Scaffold(
        drawer: new Drawer(
          child: new ListTile(
            leading: new Icon(Icons.change_history),
            title: new Text('Screen2'),
            onTap: () {
              Navigator.of(context).pushNamed("/b");
            },
          ),
          elevation: 20.0,
        ),
        appBar: new AppBar(
          title: new Text("Home"),
        ),
        body: new Container(),
      );
    }

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Navigation_example/flutternavigation/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/Navigation_example/reactnative-navigation-example/App.js)相同功能的代码

## 手势和触摸

#### 点击

react-native

	<TouchableOpacity
	  onPress={() => {
		console.log("Press");
	  }}
	  onLongPress={() => {
		console.log("Long Press");
	  }}
	>
	  <Text>Tap or Long Press</Text>
	</TouchableOpacity>

对于更复杂的手势操作,可以使用[panresponder](https://facebook.github.io/react-native/docs/panresponder.html)
	
	class App extends Component {

	  componentWillMount() {
		this._panResponder = PanResponder.create({
		  onMoveShouldSetPanResponder: (event, gestureState) =>
			!!getDirection(gestureState),
		  onPanResponderMove: (event, gestureState) => true,
		  onPanResponderRelease: (event, gestureState) => {
			const drag = getDirection(gestureState);
		  },
		  onPanResponderTerminationRequest: (event, gestureState) => true
		});
	  }
	  
	  render() {
		return (
		  <View style={styles.container} {...this._panResponder.panHandlers}>
			<View style={styles.center}>
			  <Text>Swipe Horizontally or Vertically</Text>
			</View>
		  </View>
		);
	  }
	}

flutter

> 在`flutter`中想实现这种效果可以使用`button`或者具有`onPress`的可触摸`widget`，
另一种方法和`react-native`类似,包裹一层[GestureDetector](https://docs.flutter.io/flutter/widgets/GestureDetector-class.html)
	
	new GestureDetector(
	  child: new Scaffold(
		appBar: new AppBar(
		  title: new Text("Gestures"),
		),
		body: new Center(
		  child: new Column(
			mainAxisAlignment: MainAxisAlignment.center,
			children: <Widget>[
			  new Text('Tap, Long Press, Swipe Horizontally or Vertically '),
			],
		  )
		),
	  ),
	  onTap: () {
		print('Tapped');
	  },
	  onLongPress: () {
		print('Long Pressed');
	  },
	  onVerticalDragEnd: (DragEndDetails value) {
		print('Swiped Vertically');
	  },
	  onHorizontalDragEnd: (DragEndDetails value) {
		print('Swiped Horizontally');
	  },
	);	
	
在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/gestures/fluttergestures/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/gestures/rngestures/App.js)相同功能的代码

## 网络请求

#### 调用api

react-native

	_getIPAddress = () => {
	  fetch("https://httpbin.org/ip")
		.then(response => response.json())
		.then(responseJson => {
		  this.setState({ _ipAddress: responseJson.origin });
		})
		.catch(error => {
		  console.error(error);
		});
	};

flutter

`flutter`的操作大致和`react-native`相同,但是`flutter`使用的是[dart-io-library](https://docs.flutter.io/flutter/dart-io/dart-io-library.html)
因此如果要网络请求则必须导入

	import 'dart:io';
	
支持`HTTP`,例如`get`,`post`...

	final url = new Uri.https('httpbin.org', 'ip');
	final httpClient = new HttpClient();
	_getIPAddress() async {
	  var request = await httpClient.getUrl(url);
	  var response = await request.close();
	  var responseBody = await response.transform(utf8.decoder).join();
	  String ip = json.decode(responseBody)['origin'];
	  setState(() {
		_ipAddress = ip;
	  });
	}

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/api-calls/flutterapicalls/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/api-calls/rnapicalls/App.js)相同功能的代码

## 输入框

#### rn-TextInput

react-native

	<TextInput
	  placeholder="Enter your Password"
	  onChangeText={password => this.setState({ password })}
	 />
	<Button title="Submit" onPress={this.validate} />

flutter

在`flutter`中使用[TextEditingController](https://docs.flutter.io/flutter/widgets/TextEditingController-class.html)管理[TextField](https://docs.flutter.io/flutter/material/TextField-class.html)
,每当文本发生变动`TextEditingController`都会通知给观察者,可以通过`TextEditingController.text`获取改变后的文本
	
	
在示例中，当用户点击提交按钮时，会弹出一个对话框显示用户输入的内容
	
	final TextEditingController _controller = new TextEditingController();
		  ...
	new TextField(
	// 注册观察者
	  controller: _controller,
	  decoration: new InputDecoration(
		hintText: 'Type something', labelText: "Text Field "
	  ),
	),
	new RaisedButton(
	  child: new Text('Submit'),
	  onPressed: () {
		showDialog(
		  context: context,
			child: new AlertDialog(
			  title: new Text('Alert'),
			  // 获取用户修改后的文本
			  content: new Text('You typed ${_controller.text}'),
			),
		 );
	   },
	 ),
	)
	
#### flutter的表单输入
	
在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/input-fields/flutterinputfields/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/input-fields/rninputfields/App.js)相同功能的代码

## 判断系统

react-native

	if (Platform.OS === "ios") {
	  return "iOS";
	} else if (Platform.OS === "android") {
	  return "android";
	} else {
	  return "not recognised";
	}

flutter

	if (Theme.of(context).platform == TargetPlatform.iOS) {
	  return "iOS";
	} else if (Theme.of(context).platform == TargetPlatform.android) {
	  return "android";
	} else if (Theme.of(context).platform == TargetPlatform.fuchsia) {
	  return "fuchsia";
	} else {
	  return "not recognised ";
	}

## 调试

#### Developer Menu

`react-native`可以通过摇晃设备或者使用快捷键的方式调用开发者菜单

在`flutter`中,如果开发工具使用的是`IDE`则可以使用其内置工具,如果使用`flutter run`启动程序则可以通过点击`h`来打开开发者菜单,在终端运行之后会有大量的快捷键提示

#### 热加载

如果使用`IDEA`或者`android studio`直接`ctrl+s`就可触发热加载或者点击`save all` 或者使用`hot reload`按钮

#### Chrome开发者工具

`flutter`中[observatory](https://dart-lang.github.io/observatory/)用于调试，这个适用于`flutter run`运行的程序,
推荐使用`IDE`的内置调试器

`flutter`插件在21版本的时候为`IntelliJ`和`Android Studio`发布了一个`Flutter Inspector`的新功能，
可以更方便的调试程序,它允许使用者：

* 以`widget`树的形式查看应用的`UI`结构。
* 在设备或模拟器上选择一个点，找到渲染这些像素的对应的`Widget`。
* 查看各个`widget`的属性。
* 更好地理解布局问题。

可以` View > Tool Windows > Flutter Inspector `打开检查器（仅在应用程序运行时显示内容）,
要检查特定的`widget`，请在工具栏中选择`Toggle inspect mode`，
然后在设备上单击所需的`widget`，该`widget`将在应用的用户界面中突出显示，开发者将在`IntelliJ`中的`widget`层次结构中看到该`widget`，并且将能够查看该`widget`的各个属性。

有关更多详细信息，请查看[调试文档](https://flutter.io/debugging/)。

## 动画

`react-native`可以用过`api`设置动画,而`flutter`使用[Animation](https://docs.flutter.io/flutter/animation/Animation-class.html)和[AnimationController](https://docs.flutter.io/flutter/animation/AnimationController-class.html)
设置动画,`Animation`是一个抽象类,可以知道当前的`state`和`value`，而`AnimationController`允许使用者播放动画,或者暂停动画

#### 淡入动画

`react-native`中使用`FadeInView`播放动画

    class FadeInView extends React.Component {
      state = {
        fadeAnim: new Animated.Value(0) // Initial value for opacity: 0
      };
      componentDidMount() {
        Animated.timing(this.state.fadeAnim, {
          toValue: 1,
          duration: 10000
        }).start();
      }
      render() {
        return (
          <Animated.View style={...this.props.style, opacity: this.state.fadeAnim } >
            {this.props.children}
          </Animated.View>
        );
      }
    }
        ...
    <FadeInView>
      <Text> Fading in </Text>
    </FadeInView>
        ...

flutter

在`flutter`示例中,使用[AnimationController](https://docs.flutter.io/flutter/animation/AnimationController-class.html)并指定其内部的持续时间
,然后定义一个[Tween](https://docs.flutter.io/flutter/animation/Tween-class.html)动画,然后使用[FadeTransition](https://docs.flutter.io/flutter/widgets/FadeTransition-class.html)
,然后将`animation`赋值给`opacity`属性,`Tween`的作用就是指定动画开始到结束的范围
，然后执行`controller.forward()`启动动画,也可以使用`controller`执行其他操作

    void main() {
      runApp(new Center(child: new LogoFade()));
    }
    
    class LogoFade extends StatefulWidget {
      _LogoFadeState createState() => new _LogoFadeState();
    }
    
    class _LogoFadeState extends State<LogoFade> with TickerProviderStateMixin {
      Animation animation;
      AnimationController controller;
    
      initState() {
        super.initState();
        controller = new AnimationController(
            duration: const Duration(milliseconds: 3000), vsync: this);
        final CurvedAnimation curve =
            new CurvedAnimation(parent: controller, curve: Curves.easeIn);
        animation = new Tween(begin: 0.0, end: 1.0).animate(curve);
        controller.forward();
      }
    
      Widget build(BuildContext context) {
        return new FadeTransition(
          opacity: animation,
          child: new Container(
            height: 300.0,
            width: 300.0,
            child: new FlutterLogo(),
          ),
        );
      }
    
      dispose() {
        controller.dispose();
        super.dispose();
      }
    }

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/animations/flutterfade/lib/main.dart)和[React-Native](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/animations/rnfade/App.js)相同功能的代码

#### item的滑动动画

在`react-native`中可以使用`PanResponder`或者第三方库进行滑动,而在`flutter`中,可以通过[Dismissible](https://docs.flutter.io/flutter/widgets/Dismissible-class.html)
嵌套子`widget`来实现动画

	child: new Dismissible(
	  key: key,
	  onDismissed: (DismissDirection dir) {
		cards.removeLast();
	  },
	  child: new Container(
		...
	  ),
	),

在这里可以检查[Flutter](https://github.com/GeekyAnts/flutter-docs-code-samples/blob/master/animations/fluttercardswipe/lib/main.dart)代码

## CheatSheet

react-native | flutter
[button](https://facebook.github.io/react-native/docs/button.html) | [RaisedButton](https://docs.flutter.io/flutter/material/RaisedButton-class.html) 
[button](https://facebook.github.io/react-native/docs/button.html) | [FlatButton](https://docs.flutter.io/flutter/material/FlatButton-class.html) 
[scrollview](https://facebook.github.io/react-native/docs/scrollview.html) | [ListView](https://docs.flutter.io/flutter/widgets/ListView-class.html) 
[flatlist](https://facebook.github.io/react-native/docs/flatlist.html) | [ListView.builder](https://docs.flutter.io/flutter/widgets/ListView/ListView.builder.html) 
[Image](https://docs.flutter.io/flutter/widgets/Image-class.html) | [Image](https://docs.flutter.io/flutter/widgets/Image-class.html) 
[modal](https://facebook.github.io/react-native/docs/modal.html) | [ModalRoute](https://docs.flutter.io/flutter/widgets/ModalRoute-class.html) 
[activityindicator](https://facebook.github.io/react-native/docs/activityindicator.html) | [CircularProgressIndicator](https://docs.flutter.io/flutter/material/CircularProgressIndicator-class.html) 
[activityindicator](https://facebook.github.io/react-native/docs/activityindicator.html) | [LinearProgressIndicator](https://docs.flutter.io/flutter/material/LinearProgressIndicator-class.html) 
[refreshcontrol](https://facebook.github.io/react-native/docs/refreshcontrol.html) | [RefreshIndicator](https://docs.flutter.io/flutter/material/RefreshIndicator-class.html)
[view](https://facebook.github.io/react-native/docs/view.html) | [Container](https://docs.flutter.io/flutter/widgets/Container-class.html) 
[view](https://facebook.github.io/react-native/docs/view.html) | [Column](https://docs.flutter.io/flutter/widgets/Column-class.html)
[view](https://facebook.github.io/react-native/docs/view.html) | [Row](https://docs.flutter.io/flutter/widgets/Row-class.html)
[view](https://facebook.github.io/react-native/docs/view.html) | [Center](https://docs.flutter.io/flutter/widgets/Center-class.html)
[view](https://facebook.github.io/react-native/docs/view.html) | [Padding](https://docs.flutter.io/flutter/widgets/Padding-class.html) 
[touchableopacity](https://facebook.github.io/react-native/docs/touchableopacity.html) | [GestureDetector](https://docs.flutter.io/flutter/widgets/GestureDetector-class.html)
[textinput](https://facebook.github.io/react-native/docs/textinput.html) | [TextInput](https://docs.flutter.io/flutter/services/TextInput-class.html)
[text](https://facebook.github.io/react-native/docs/text.htmll) | [Text](https://docs.flutter.io/flutter/widgets/Text-class.html)
[switch](https://facebook.github.io/react-native/docs/switch.html) | [Switch](https://docs.flutter.io/flutter/material/Switch-class.html)
[slider](https://facebook.github.io/react-native/docs/slider.html) | [Slider](https://docs.flutter.io/flutter/material/Slider-class.html) 
