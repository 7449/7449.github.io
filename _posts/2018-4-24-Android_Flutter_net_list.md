---
layout:     post
title:      "Flutter项目--列表页完善,网络请求"
subtitle:   "知乎专栏列表页"
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


## 效果图

![_config.yml]({{ site.baseurl }}/img/flutter-zhihu-net-list.gif)

## 知识点

* 网络加载
* Future
* Json
* ListView

>其实直接使用网络获取后的数据赋值页可以,为了规范,建议创建`entity`获取数据

## 代码

需要的依赖：

一个是网络请求,另一个是简单的加载图片出现的淡出效果
    
      http: ^0.11.3+16
      transparent_image: ^0.1.0

#### listView

    import 'dart:async';
    import 'dart:convert';
    
    import 'package:flutter/material.dart';
    import 'package:http/http.dart' as http;
    import 'package:meta/meta.dart';
    import 'package:zhihu_zhuan_lan/detail_screen.dart';
    import 'package:zhihu_zhuan_lan/entity/entity.dart';
    import 'package:zhihu_zhuan_lan/values.dart';
    import 'package:transparent_image/transparent_image.dart';
    
    ///请求网络获取数据并使用Json转换
    Future<List<ListEntity>> fetchList(suffix) async {
      final response = await http.get(getListUrl(suffix));
      return ListEntity.fromJson(json.decode(response.body));
    }
    
    ///传递一个 suffix 参数
    class TabScreen extends StatelessWidget {
      final String suffix;
    
      TabScreen({@required this.suffix});
    
      ///使用 InkWell 点击有视觉反馈
      Widget listItem(context, index, ListEntity info) {
        String imageUrl = info.titleImage;
        return new Card(
            child: new InkWell(
          onTap: () {
            Navigator.push(
                context,
                new MaterialPageRoute(
                    builder: (context) => new DetailScreen(
                        slug: info.slug.toString(), title: info.title)));
          },
          child: new Column(
            children: <Widget>[
              new FadeInImage.memoryNetwork(
                  placeholder: kTransparentImage,
                  image: imageUrl.isEmpty ? defaultImageUrl : imageUrl,
                  height: 180.0,
                  width: 1000.0,
                  fit: BoxFit.cover),
              new Padding(
                padding: new EdgeInsets.all(8.0),
                child: new Text(info.title),
              )
            ],
          ),
        ));
      }
    
      /// 这里使用`FutureBuilder`
      /// 不得不说`Flutter`封装使用的非常方便，简单的就能实现不同情况下显示不同`widget`
      /// 默认是返回一个`loading`加载框,出现错误只是简单的显示一个`Text`
      /// 数据之后使用`ListView.Builder`构建出样式
      @override
      Widget build(BuildContext context) {
        return new Center(
          child: new FutureBuilder<List<ListEntity>>(
            future: fetchList(suffix),
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return new ListView.builder(
                    itemCount: snapshot.data.length,
                    itemBuilder: (context, index) =>
                        listItem(context, index, snapshot.data[index]));
              } else if (snapshot.hasError) {
                return new Center(child: new Text('${snapshot.error}'));
              }
              return CircularProgressIndicator();
            },
          ),
        );
      }
    }

#### entity

    class ListEntity {
      final String title;
      final String titleImage;
      final int slug;
      AuthorEntity author;
    
      ListEntity({this.title, this.titleImage, this.slug, this.author});
    
      static List<ListEntity> fromJson(List json) {
        return json
            .map((string) => new ListEntity(
                title: string['title'],
                titleImage: string['titleImage'],
                slug: string['slug'],
                author: new AuthorEntity.fromJson(string['author'])))
            .toList();
      }
    }
    
    class AuthorEntity {
      final String profileUrl;
      final String bio;
      final String name;
    
      AuthorEntity({this.profileUrl, this.bio, this.name});
    
      factory AuthorEntity.fromJson(Map<String, dynamic> json) {
        return new AuthorEntity(
          profileUrl: json['profileUrl'],
          bio: json['bio'],
          name: json['name'],
        );
      }
    }
