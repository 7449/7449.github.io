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

`android`里面的`view`是所有内容的基础，屏幕上面显示的所有控件都是`view`,在`Flutter`里面就等同于一个`widget`

区别在于在`android`中`view`只会绘制一次,除非调用`invalidate`这个方法`view`才会重绘

而在`flutter`中，每一帧之间都会创建`widget`树，用于下一帧的渲染，并且`widget`是不可变的

#### 怎么更新页面

`android`中如果想更改界面,直接通过`view`即可，例如`setText(string)`，但是在`flutter`中`widget`是不可变的，不能直接更新界面,如果想更新界面
只能通过改变`widget`的`state`的状态来达到目的

所以在`flutter`中 `StatelessWidget`非常适用于一些不依赖任何配置不需要任何改动的组件,因为它是一个没有状态的`widget`

例如闪屏页的`ImageView`，如果不进行任何变动的话，非常适合使用`StatelessWidget`

但是如果想在网络请求或者其他交互之后动态改变界面的话,则必须使用`StatefulWidget`，使用`setState`通知`flutter`进行更新界面

其实`StatelessWidgets`和`StatefulWidget`特征都是相同的,不同的是`StatefulWidget`存在一个`state`对象存储状态数据

这里很好区分：

* 如果一个`widget`存在用户交互可以使其发生改变,那么使用`StatefulWidget`否则使用`StatelessWidget`即可

举个例子：

    new Text(
      'I like Flutter!',
      style: new TextStyle(fontWeight: FontWeight.bold),
    );

这里的 `Text` 没有存在任何交互,只是显示一段文字,再往下看`Text`是继承`StatelessWidget`的

但是如果想点击一个`Button`然后使`Text`显示的文字发生改变,可以将`Text`封装在`StatefulWidget`中并在点击的时候`setState`来刷新界面

例如：

其实这段代码熟悉`React`语法的人非常容易就可以理解,最主要的就是
    
      void _updateText() {
        setState(() {
          // update the text
          textToShow = "Flutter is Awesome!";
        });
      }

其余的代码都可以认为是一段刚开始学习`Flutter`的模版代码。

    import 'package:flutter/material.dart';
    
    void main() {
      runApp(new SampleApp());
    }
    
    class SampleApp extends StatelessWidget {
      // This widget is the root of your application.
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'Sample App',
          theme: new ThemeData(
            primarySwatch: Colors.blue,
          ),
          home: new SampleAppPage(),
        );
      }
    }
    
    class SampleAppPage extends StatefulWidget {
      SampleAppPage({Key key}) : super(key: key);
    
      @override
      _SampleAppPageState createState() => new _SampleAppPageState();
    }
    
    class _SampleAppPageState extends State<SampleAppPage> {
      // Default placeholder text
      String textToShow = "I Like Flutter";
    
      void _updateText() {
        setState(() {
          // update the text
          textToShow = "Flutter is Awesome!";
        });
      }
    
      @override
      Widget build(BuildContext context) {
        return new Scaffold(
          appBar: new AppBar(
            title: new Text("Sample App"),
          ),
          body: new Center(child: new Text(textToShow)),
          floatingActionButton: new FloatingActionButton(
            onPressed: _updateText,
            tooltip: 'Update Text',
            child: new Icon(Icons.update),
          ),
        );
      }
    }

#### android-xml

`android`中除了使用`java`写布局之外，还可以使用`xml`编写布局,但是在`Flutter`中，只能通过`widget`写布局

这里还是要说下,目前来说我个人感觉如果复杂的布局,使用`Flutter`感觉比较麻烦,特别容易陷入回调地狱

看看官网的例子：

    @override
    Widget build(BuildContext context) {
      return new Scaffold(
        appBar: new AppBar(
          title: new Text("Sample App"),
        ),
        body: new Center(
          child: new MaterialButton(
            onPressed: () {},
            child: new Text('Hello'),
            padding: new EdgeInsets.only(left: 10.0, right: 10.0),
          ),
        ),
      );
    }
    
只是简单的显示一个`toolbar`和一个可点击的`button`

这里并没有很好的例子去学习,如果想深入了解可以通过[Widget目录](https://7449.github.io/2018/04/12/Android_Flutter_5/)查看
第五章也只是提供一个通往官网的链接

#### 增删组件

`android`中可以通过`addChild`或者`removeChild`去添加或者删除不需要的`view`，但是在`Flutter`中`widget`是不可变的,所有没有直接的方法
添加或者删除,只能通过`setState`去改变不同的状态然后返回不同的布局,如果了解上面那个例子的话，我相信有的人肯定知道这里怎么处理

    import 'package:flutter/material.dart';
    
    void main() {
      runApp(new SampleApp());
    }
    
    class SampleApp extends StatelessWidget {
      // This widget is the root of your application.
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'Sample App',
          theme: new ThemeData(
            primarySwatch: Colors.blue,
          ),
          home: new SampleAppPage(),
        );
      }
    }
    
    class SampleAppPage extends StatefulWidget {
      SampleAppPage({Key key}) : super(key: key);
    
      @override
      _SampleAppPageState createState() => new _SampleAppPageState();
    }
    
    class _SampleAppPageState extends State<SampleAppPage> {
      // Default value for toggle
      bool toggle = true;
      void _toggle() {
        setState(() {
          toggle = !toggle;
        });
      }
    
      _getToggleChild() {
        if (toggle) {
          return new Text('Toggle One');
        } else {
          return new MaterialButton(onPressed: () {}, child: new Text('Toggle Two'));
        }
      }
    
      @override
      Widget build(BuildContext context) {
        return new Scaffold(
          appBar: new AppBar(
            title: new Text("Sample App"),
          ),
          body: new Center(
            child: _getToggleChild(),
          ),
          floatingActionButton: new FloatingActionButton(
            onPressed: _toggle,
            tooltip: 'Update Text',
            child: new Icon(Icons.update),
          ),
        );
      }
    }

#### 设置动画

`android`中可以通过`xml`或者`animate`实现炫酷的动画

在`flutter`可以通过`animation`库去实现

对应于`Animator`在`flutter`中存在`AnimationController`,继承自`Animation<double>`，

可以通过创建一个或者多个`Animation`，然后将它们添加在`Controller`

举个例子:

> 官网的示例按下去会有一个淡入淡出的动画

    import 'package:flutter/material.dart';
    
    void main() {
      runApp(new FadeAppTest());
    }
    
    class FadeAppTest extends StatelessWidget {
      // This widget is the root of your application.
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'Fade Demo',
          theme: new ThemeData(
            primarySwatch: Colors.blue,
          ),
          home: new MyFadeTest(title: 'Fade Demo'),
        );
      }
    }
    
    class MyFadeTest extends StatefulWidget {
      MyFadeTest({Key key, this.title}) : super(key: key);
      final String title;
      @override
      _MyFadeTest createState() => new _MyFadeTest();
    }
    
    class _MyFadeTest extends State<MyFadeTest> with TickerProviderStateMixin {
      AnimationController controller;
      CurvedAnimation curve;
    
      @override
      void initState() {
        controller = new AnimationController(duration: const Duration(milliseconds: 2000), vsync: this);
        curve = new CurvedAnimation(parent: controller, curve: Curves.easeIn);
      }
    
      @override
      Widget build(BuildContext context) {
        return new Scaffold(
          appBar: new AppBar(
            title: new Text(widget.title),
          ),
          body: new Center(
              child: new Container(
                  child: new FadeTransition(
                      opacity: curve,
                      child: new FlutterLogo(
                        size: 100.0,
                      )))),
          floatingActionButton: new FloatingActionButton(
            tooltip: 'Fade',
            child: new Icon(Icons.brush),
            onPressed: () {
              controller.forward();
            },
          ),
        );
      }
    }

更多的细节可以参考[https://flutter.io/widgets/animation/](https://flutter.io/widgets/animation/) 和 [https://flutter.io/tutorials/animation](https://flutter.io/tutorials/animation)

#### 使用`Canvas`绘制

`android`可以通过 `Canvas`或者`Drawable`在屏幕上绘制图,`flutter`也具有类似的`api`

`CustomPaint`和`CustomPainter`，这两个类可以在`Canvas`上进行绘制

在[stackoverflow](https://stackoverflow.com/questions/46241071/create-signature-area-for-mobile-app-in-dart-flutter)这个链接里面可以看到怎么在`flutter`中实现一个签名功能的`Canvas`

	import 'package:flutter/material.dart';
	class SignaturePainter extends CustomPainter {
	  SignaturePainter(this.points);
	  final List<Offset> points;
	  void paint(Canvas canvas, Size size) {
	    var paint = new Paint()
	      ..color = Colors.black
	      ..strokeCap = StrokeCap.round
	      ..strokeWidth = 5.0;
	    for (int i = 0; i < points.length - 1; i++) {
	      if (points[i] != null && points[i + 1] != null)
	        canvas.drawLine(points[i], points[i + 1], paint);
	    }
	  }
	  bool shouldRepaint(SignaturePainter other) => other.points != points;
	}
	class Signature extends StatefulWidget {
	  SignatureState createState() => new SignatureState();
	}
	class SignatureState extends State<Signature> {
	  List<Offset> _points = <Offset>[];
	  Widget build(BuildContext context) {
	    return new GestureDetector(
	      onPanUpdate: (DragUpdateDetails details) {
	        setState(() {
	          RenderBox referenceBox = context.findRenderObject();
	          Offset localPosition =
	          referenceBox.globalToLocal(details.globalPosition);
	          _points = new List.from(_points)..add(localPosition);
	        });
	      },
	      onPanEnd: (DragEndDetails details) => _points.add(null),
	      child: new CustomPaint(painter: new SignaturePainter(_points), size: Size.infinite),
	    );
	  }
	}
	class DemoApp extends StatelessWidget {
	  Widget build(BuildContext context) => new Scaffold(body: new Signature());
	}
	void main() => runApp(new MaterialApp(home: new DemoApp()));


#### 自定义小部件

类似于`android`中的自定义控件,通过封装然后实现一些通用特性的组件

官网示例：

	class CustomButton extends StatelessWidget {
	  final String label;
	
	  CustomButton(this.label);
	
	  @override
	  Widget build(BuildContext context) {
	    return new RaisedButton(onPressed: () {}, child: new Text(label));
	  }
	}

使用：

	@override
	Widget build(BuildContext context) {
	  return new Center(
	    child: new CustomButton("Hello"),
	  );
	}

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
