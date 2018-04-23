---
layout:     post
title:      "Flutter项目--初始化项目和闪屏页"
subtitle:   "初始化一个flutter项目并添加闪屏页"
date:       2018-4-23
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

## 目标

写一个[知乎专栏](https://github.com/7449/ZLSimple)的`flutter`版

## 初始化项目

使用`android studio`创建一个`flutter`打开并运行.这里并没有使用命令行去创建项目,
而且以后的项目博客都是在`android studio`中完成

选择`Flutter Application`然后填好需要的名称和包名`next`即可

## update

这里推荐一种更为方便的设置启动页的方法：

在`android`中导航到`.../android/app/src/main`，在`res/drawable/launch_background.xml`中已经有一个示例,根据示例可以自定义启动页

当然,这有个缺点就是`ios`和`android`想要相同的效果就要一起替换

具体的可以查看[处理 assets 和 图像](https://7449.github.io/2018/04/22/Android_Flutter_11/)

    import 'package:flutter/material.dart';
    import 'package:zhihu_zhuan_lan/HomeScreen.dart';
    
    // 入口，这里使用MaterialApp作为顶级入口,启动项目的启动页
    // 推荐使用flutter推荐的启动页方法
    // 具体可以查看这篇blog：https://7449.github.io/2018/04/22/Android_Flutter_11/
    void main() => runApp(new MaterialApp(home: new HomeScreen()));
    
    // 这只是一种推荐方法,不是很推荐,如果在启动页放广告可以这样弄,但是私以为启动页应该
    //以最快的速度进入app主页
    //void main() => runApp(new MaterialApp(home: new SplashScreen()));
    

## 项目整理

#### 配置本地资源路径

在这个项目里并没有创建`assets`目录，而在将所有东西都放在了`lib`目录下

如图所示：

![_config.yml]({{ site.baseurl }}/img/flutter-zhihu-package.png)

然后在`pubspec.yaml`目录下配置好资源图

    flutter:
    
      uses-material-design: true
    
      assets:
        - lib/image/icon_splash.jpg

由于修改了`pubspec.yaml`，这个时候`IDE`会提示点击`Packages get`或者忽略,这里建议
不管是改了什么都点`Packages get`,养成一个好习惯。

#### 修改`main.dart`

这里使用`MaterialApp`作为顶层入口，而`SplashScreen`则是自己新建的一个启动页

    import 'package:flutter/material.dart';
    import 'package:zhihu_zhuan_lan/SplashScreen.dart';
    
    // 入口，这里使用MaterialApp作为顶级入口,启动项目的启动页
    void main() => runApp(new MaterialApp(home: new SplashScreen()));


#### splash

这里选择的启动页是一张图片,然后启动的时候给一个渐变的动画,然后等动画结束就进入`app`
,基本上注释写的很清楚,我认为需要注意的就两点：

* 动画的监听，启动
* 跳转到新的页面


动画的监听和启动,这里是通过查阅[animations](https://flutter.io/animations/)
得知的

跳转的话需要注意的是,`splash`只是一个过渡页面,也就意味着只要跳转到了新的页面,
这个页面就需要销毁了,就是说,使用者按`back`键直接返回到桌面而不是回退到`splash`页面
,那么就是说`Navigator.of(context).push`达不到我们想要的需求,而系统肯定也会提供相应的办法,
通过查阅源码可以知道`pushAndRemoveUntil`刚好可以达到我们的需求


整个启动页代码：

    import 'package:flutter/material.dart';
    import 'package:zhihu_zhuan_lan/HomeScreen.dart';
    import 'package:zhihu_zhuan_lan/values.dart';
    
    // 初始化一个闪屏页
    class SplashScreen extends StatefulWidget {
      @override
      State<StatefulWidget> createState() => new SplashState();
    }
    
    class SplashState extends State<SplashScreen> with TickerProviderStateMixin {
      // 动画
      Animation animation;
    
      // 动画管理器
      AnimationController controller;
    
      initState() {
        super.initState();
        //初始化动画管理器
        controller = new AnimationController(
            duration: const Duration(milliseconds: 1500), vsync: this);
        //初始化动画
        animation = new Tween(begin: 0.0, end: 1.0).animate(controller);
        //注册动画观察者
        animation.addStatusListener((status) => animationListener(status));
        //启动动画
        controller.forward();
      }
    
      /// 观察动画状态,在结束的时候启动到新的页面,
      /// 这里使用的是`pushAndRemoveUntil`而不是`push`
      /// 因为闪屏页跳转之后需要销毁,而`pushAndRemoveUntil`会删除之前的所有页面,只留下跳转的那个
      animationListener(status) {
        if (status == AnimationStatus.completed) {
          Navigator.of(context).pushAndRemoveUntil(
              new MaterialPageRoute(builder: (context) => new HomeScreen()),
              (route) => route == null);
        }
      }
    
      Widget build(BuildContext context) {
        return new FadeTransition(
            opacity: animation,
            child: new Image.asset(
              SplashImage,
              fit: BoxFit.cover,
            ));
      }
    
      dispose() {
        controller.dispose();
        controller.removeStatusListener(animationListener);
        super.dispose();
      }
    }

这里的`HomeScreen`很简单,只是显示一个`Text`，
这里为了熟悉源码,并没有使用`MaterialApp`而是自定义了一个`Theme`
    
    import 'package:flutter/material.dart';
    import 'package:zhihu_zhuan_lan/values.dart';
    
    class HomeScreen extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return new Theme(
            data: new ThemeData(
              primaryColor: Colors.cyan,
            ),
            child: new Scaffold(
                appBar: new AppBar(title: new Text(HomeTitle)),
                body: new Center(
                  child: new Text(HomeTitle),
                )));
      }
    }

启动页基本没什么难点,一个简单的思路,其实还可以通过定时器,
如果项目初始化的时候需要处理一些耗时数据的话,那么定时器的体验肯定会比动画好一点
![_config.yml]({{ site.baseurl }}/img/flutter-zhihu_splash-android.gif)
