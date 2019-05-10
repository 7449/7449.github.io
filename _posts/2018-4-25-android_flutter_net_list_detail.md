---
layout:     post
title:      "Flutter项目--列表详情页"
subtitle:   "知乎专栏列表详情页"
date:       2018-4-25
tags:
    - android
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

[Flutter番外篇:Dart](https://7449.github.io/2018/3/18/android_flutter_dart.html)<br>
[第一章：什么是Flutter](https://7449.github.io/2018/3/19/android_flutter_1.html)<br>
[第二章：安装Flutter](https://7449.github.io/2018/3/19/android_flutter_2.html)<br>
[第三章：编写一个FlutterApp](https://7449.github.io/2018/3/26/android_flutter_3.html)<br>
[第四章：框架预览](https://7449.github.io/2018/3/26/android_flutter_4.html)<br>
[第五章：Widget目录](https://7449.github.io/2018/4/12/android_flutter_5.html)<br>
[第六章：面对Android开发的Flutter说明](https://7449.github.io/2018/4/16/android_flutter_6.html)<br>
[第七章：面对ReactNative开发的Flutter说明](https://7449.github.io/2018/4/17/android_flutter_7.html)<br>
[第八章：手势](https://7449.github.io/2018/4/20/android_flutter_8.html)<br>
[第九章：动画](https://7449.github.io/2018/4/20/android_flutter_9.html)<br>
[第十章：布局约束](https://7449.github.io/2018/4/21/android_flutter_10.html)<br>
[第十一章：处理 assets 和 图像](https://7449.github.io/2018/4/22/android_flutter_11.html)<br>
[json序列化](https://7449.github.io/2018/5/02/android_flutter_json_serializable.html)<br>
[初始化项目和闪屏页](https://7449.github.io/2018/4/23/android_flutter_splash.html)<br>
[添加Tab和Drawer](https://7449.github.io/2018/4/24/android_flutter_drawer.html)<br>
[列表页完善,网络请求](https://7449.github.io/2018/4/24/android_flutter_net_list.html)<br>
[列表详情页](https://7449.github.io/2018/4/25/android_flutter_net_list_detail.html)<br>

## 效果图

> `html`文本暂时没有找到比较好的解决办法,试过`webview``markdown`插件,都不能达到想要的效果,暂时用`text`显示一段简单的文本


尝试使用[WebView](https://github.com/dart-flitter/flutter_webview_plugin)这个插件,显示有点问题,图片不能显示,
而且无法使用`SliverAppBar`，通过修改源码可以实现使用`SliverAppBar`，但是滑动有冲突,
只能等待`flutter`自身支持加载一段`html`代码

![_config.yml]({{ site.baseurl }}/assets/screenshot/18/flutter-zhihu-net-list-detail.gif)

## 代码

>代码比较简单只是用到了`SliverAppBar`和`CustomScrollView`以及`SliverToBoxAdapter`

    import 'dart:async';
    import 'dart:convert';
    
    import 'package:flutter/material.dart';
    import 'package:http/http.dart' as http;
    import 'package:meta/meta.dart';
    import 'package:transparent_image/transparent_image.dart';
    import 'package:zhihu_zhuan_lan/entity/entity.dart';
    import 'package:zhihu_zhuan_lan/values.dart';
    
    ///请求网络获取数据并使用Json转换
    Future<DetailEntity> fetchDetail(slug) async {
      final response = await http.get(getDetailUrl(slug));
      return DetailEntity.fromJson(json.decode(response.body));
    }
    
    class DetailScreen extends StatelessWidget {
      final String slug;
      final String title;
      final String titleImage;
    
      DetailScreen(
          {@required this.slug, @required this.title, @required this.titleImage});
    
      Widget boxAdapterWidget(context) {
        return new FutureBuilder<DetailEntity>(
          future: fetchDetail(slug),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              return new Center(
                  child: new Padding(
                      padding: new EdgeInsets.all(10.0),
                      child: new RichText(
                        text: new TextSpan(
                            text: snapshot.data.content,
                            style: DefaultTextStyle.of(context).style),
                      )));
            } else if (snapshot.hasError) {
              return new Center(child: new Text('${snapshot.error}'));
            }
            return new Center(child: new CircularProgressIndicator());
          },
        );
      }
    
      @override
      Widget build(BuildContext context) {
        return new Scaffold(
          body: new CustomScrollView(
            slivers: <Widget>[
              new SliverAppBar(
                pinned: true,
                expandedHeight: 180.0,
                flexibleSpace: new FlexibleSpaceBar(
                  title: new Text(
                    title,
                    maxLines: 1,
                  ),
                  background: new FadeInImage.memoryNetwork(
                      placeholder: kTransparentImage,
                      image: titleImage,
                      height: 180.0,
                      width: 1000.0,
                      fit: BoxFit.cover),
                ),
              ),
              new SliverToBoxAdapter(child: boxAdapterWidget(context))
            ],
          ),
        );
      }
    }


## entity

    class DetailEntity {
      final String titleImage;
      final String title;
      final String content;
      AuthorEntity author;
    
      DetailEntity({this.title, this.titleImage, this.content, this.author});
    
      static DetailEntity fromJson(Map<String, dynamic> json) {
        return new DetailEntity(
          title: json['title'],
          titleImage: json['titleImage'],
          content: json['content'],
          author: new AuthorEntity.fromJson(json['author']),
        );
      }
    }
