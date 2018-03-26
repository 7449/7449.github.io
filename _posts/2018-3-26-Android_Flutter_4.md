---
layout:     post
title:      "Flutter系列--第四章:框架预览"
subtitle:   "Flutter Widget框架之旅"
date:       2018-3-26
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

#### Introduction

`Flutter`的小部件逻辑采用的是[react](https://reactjs.org/)

#### Hello World

    import 'package:flutter/material.dart';
    
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

#### Basic widgets

[详细文章](https://flutter.io/widgets/layout/)

`Flutter`自带了一些非常常用的小部件

例如：

* [Text](https://docs.flutter.io/flutter/widgets/Text-class.html):简单的文字显示
* [Row](https://docs.flutter.io/flutter/widgets/Row-class.html)  [Column](https://docs.flutter.io/flutter/widgets/Column-class.html):创建布局,`flex`布局
* [Stack](https://docs.flutter.io/flutter/widgets/Stack-class.html):一个`Stack`可以在绘制顺序上堆叠小部件，`Stack`以相对于堆栈的顶部，右侧，底部或左侧边缘定位它们,堆栈基于Web的绝对定位布局模型。
* [Container](https://docs.flutter.io/flutter/widgets/Container-class.html):创建矩形视觉元素,一个容器可以装饰一个[BoxDecoration](https://docs.flutter.io/flutter/painting/BoxDecoration-class.html)，比如背景，阴影


实例：

    import 'package:flutter/material.dart';
    
    class MyAppBar extends StatelessWidget {
      MyAppBar({this.title});
    
      // Fields in a Widget subclass are always marked "final".
    
      final Widget title;
    
      @override
      Widget build(BuildContext context) {
        return new Container(
          height: 56.0, // in logical pixels
          padding: const EdgeInsets.symmetric(horizontal: 8.0),
          decoration: new BoxDecoration(color: Colors.blue[500]),
          // Row is a horizontal, linear layout.
          child: new Row(
            // <Widget> is the type of items in the list.
            children: <Widget>[
              new IconButton(
                icon: new Icon(Icons.menu),
                tooltip: 'Navigation menu',
                onPressed: null, // null disables the button
              ),
              // Expanded expands its child to fill the available space.
              new Expanded(
                child: title,
              ),
              new IconButton(
                icon: new Icon(Icons.search),
                tooltip: 'Search',
                onPressed: null,
              ),
            ],
          ),
        );
      }
    }
    
    class MyScaffold extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        // Material is a conceptual piece of paper on which the UI appears.
        return new Material(
          // Column is a vertical, linear layout.
          child: new Column(
            children: <Widget>[
              new MyAppBar(
                title: new Text(
                  'Example title',
                  style: Theme.of(context).primaryTextTheme.title,
                ),
              ),
              new Expanded(
                child: new Center(
                  child: new Text('Hello, world!'),
                ),
              ),
            ],
          ),
        );
      }
    }
    
    void main() {
      runApp(new MaterialApp(
        title: 'My app', // used by the OS task switcher
        home: new MyScaffold(),
      ));
    }

请确保`pubspec.yaml`的`uses-material-design`为`true`

    name: my_app
    flutter:
      uses-material-design: true


#### Using Material Components

使用 `Material` 组件

[详细文章](https://flutter.io/widgets/material/)


#### Handling gestures

处理手势

[详细文章](https://flutter.io/gestures/)




#### Changing widgets in response to input

根据输入更改 `widget`

#### Bringing it all together

组合组件

#### Responding to widget lifecycle events

`widget`的生命周期

#### Keys

按键

#### Global Keys

全局秘钥
