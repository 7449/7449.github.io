---
layout:     post
title:      "Flutter项目--添加Tab和Drawer"
subtitle:   "构建大体框架"
date:       2018-4-24
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

## blog

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


## drawer

`drawer`一般和`Scaffold`一起使用,这里也没有什么需要注意的地方,按照官方文档一步一步来就可以了

## tab

`tab`需要注意的是`length`属性不能动态设置，但是`app`要的是根据抽屉选择的不同加载不同个数的`Tab`
,现在想到的是在`setState`的时候重新`new TabController`，但是有个问题

切换抽屉`item`，虽然`tab`显示的是第一个,页面却一直显示上一次点击的`tab`页数

如果切换的`tab`标签数小于上一次点击的页数,那么就显示的最后一页


完整代码如下：


    import 'package:flutter/material.dart';
    import 'package:zhihu_zhuan_lan/tab_screen.dart';
    import 'package:zhihu_zhuan_lan/values.dart';
    
    //已知问题:切换抽屉item，虽然`tab`显示的是第一个,页面却一直显示上一次点击的`tab`页数
    class HomeScreen extends StatefulWidget {
      //初始化 State
      @override
      State<StatefulWidget> createState() => new DrawerState();
    }
    
    class DrawerState extends State<HomeScreen> with TickerProviderStateMixin {
      //记录Drawer目前选中的角标
      int selectIndex = 0;
    
      //Tab管理器
      TabController tabController;
    
      var tabListener;
    
      @override
      void initState() {
        super.initState();
        //初始化Tab管理器
        tabController =
            new TabController(vsync: this, length: getTabLength(selectIndex));
        tabListener = () {};
        tabController.addListener(tabListener);
      }
    
      @override
      void dispose() {
        tabController.removeListener(tabListener);
        tabController.dispose();
        super.dispose();
      }
    
      /// 初始化Drawer，这里使用两个Widget组合,一个Header，一个ListView
      /// ListView的好处是如果抽屉内容过多的话,可以自己处理滑动
      Widget drawer() {
        return new Column(
          children: <Widget>[
            new Container(
              color: Colors.grey,
              height: 160.0,
              child: new Center(child: new Text(HomeTitle)),
            ),
            new Expanded(
                child: new ListView.builder(
                    itemCount: drawerTabs.length,
                    itemBuilder: (context, index) =>
                        drawerListItem(context, index)))
          ],
        );
      }
    
      /// Drawer ListView Item
      /// 如果只是简单的Item ListTitle是个不错的选择
      /// 点击之后有个SnackBar提示然后重新初始化Tab管理器
      /// 因为目前为止Tab管理器的length不能动态设置
      /// 关闭抽屉 Navigator.pop(context);
      Widget drawerListItem(context, index) {
        var text = drawerTabs[index];
        return new ListTile(
            leading: new Icon(Icons.android),
            title: new Text(text,
                style: new TextStyle(
                    color: selectIndex == index ? Colors.blue : Colors.black)),
            onTap: () {
              Navigator.pop(context);
              Scaffold
                  .of(context)
                  .showSnackBar(new SnackBar(content: new Text('开始加载$text')));
              setState(() {
                tabController.removeListener(tabListener);
                tabController =
                    new TabController(vsync: this, length: getTabLength(index));
                tabController.addListener(tabListener);
                selectIndex = index;
              });
            });
      }
    
      List getTabView(index) {
        return getTabTitle(index).map((text) => new Tab(text: text)).toList();
      }
    
      List getBodyView(index) {
        List<String> tabList = getTabTitle(index);
        List<Widget> tabViews = [];
        for (int i = 0; i < tabList.length; i++) {
          tabViews.add(new TabScreen(suffix: getTabSuffix(index, i)));
        }
        return tabViews;
      }
    
      /// 这里没有使用 DefaultTabController,如果Tab的个数是固定的，推荐使用,
      /// 使用起来非常方便
      @override
      Widget build(BuildContext context) {
        return new Scaffold(
          drawer: new Drawer(child: drawer()),
          appBar: new AppBar(
              title: new Text(drawerTabs[selectIndex]),
              bottom: new TabBar(
                controller: tabController,
                tabs: getTabView(selectIndex),
                isScrollable: true,
              )),
          body: new TabBarView(
              controller: tabController, children: getBodyView(selectIndex)),
        );
      }
    }


![_config.yml]({{ site.baseurl }}/img/flutter-zhihu-tab.gif)
