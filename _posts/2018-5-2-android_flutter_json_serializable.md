---
layout:     post
title:      "Flutter系列:json序列化"
subtitle:   "处理json"
date:       2018-5-2
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

## 介绍

简单介绍下怎么在`flutter`中处理`json`数据

主要使用`json.decode()`,这个方法返回一个`Map<String, dynamic>`

#### 使用entity

这种方法比较适合数据量比较小的接口，例如下面的示例，使用时只需`new AuthorEntity.fromJson(json.decode(json))`
即可,如果想转换为`json`只需调用`String json = json.encode(author)`即可

[示例](https://github.com/7449/flutter_example/blob/master/flutter-zhihu_zhuanlan/lib/entity/entity.dart)

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

#### 自动生成

这里使用[json_serializable](https://pub.dartlang.org/packages/json_serializable)

[示例](https://github.com/7449/flutter_example/blob/master/flutter_codekk/lib/entity)

添加依赖

    dependencies:
      flutter:
        sdk: flutter
      json_serializable: ^0.5.0
      build_runner: ^0.8.7
      
创建文件：

类似于官网的示例,根据后台返回的数据创建对应的`entity`

一个简单的示例：

    import 'package:json_annotation/json_annotation.dart';
    part 'user.g.dart';
    @JsonSerializable()
    class User extends Object with _$UserSerializerMixin {
      User(this.name, this.email);
      String name;
      String email;
      factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
    }

官网的示例很简单,需要注意的是`*.g.dart`,`_$*SerializerMixin`
和`_$*FromJson`都是自动生成的,只需要按照规则写即可

可以使用`@JsonKey(name: '')`指定名称

下面是一个比较复杂的示例：

    import 'package:json_annotation/json_annotation.dart';
    import 'package:meta/meta.dart';
    
    part 'op_entity.g.dart';
    
    @JsonSerializable()
    class OpEntity extends Object with _$OpEntitySerializerMixin {
      final int code;
      final String message;
      final DataEntity data;
    
      OpEntity({@required this.data, @required this.code, @required this.message});
    
      factory OpEntity.fromJson(Map<String, dynamic> json) =>
          _$OpEntityFromJson(json);
    }
    
    @JsonSerializable()
    class DataEntity extends Object with _$DataEntitySerializerMixin {
      final List<ProjectArrayEntity> projectArray;
    
      DataEntity({@required this.projectArray});
    
      factory DataEntity.fromJson(Map<String, dynamic> json) =>
          _$DataEntityFromJson(json);
    }
    
    @JsonSerializable()
    class ProjectArrayEntity extends Object
        with _$ProjectArrayEntitySerializerMixin {
      final String projectName;
      final String createTime;
      final String updateTime;
      final int expiredTimes;
      final int usedTimes;
      final int voteUp;
      final bool recommend;
      final bool hide;
      final String projectUrl;
      final String demoUrl;
      final String committer;
      final String source;
      final String lang;
      final String authorName;
      final String authorUrl;
      final String codeKKUrl;
      @JsonKey(name: '_id')
      final String id;
      final String desc;
      final Object officialUrl;
      final List<TagsEntity> tags;
    
      ProjectArrayEntity(
          {@required this.projectName,
          @required this.createTime,
          @required this.updateTime,
          @required this.expiredTimes,
          @required this.usedTimes,
          @required this.voteUp,
          @required this.recommend,
          @required this.hide,
          @required this.projectUrl,
          @required this.demoUrl,
          @required this.committer,
          @required this.source,
          @required this.lang,
          @required this.authorName,
          @required this.authorUrl,
          @required this.codeKKUrl,
          @required this.id,
          @required this.desc,
          @required this.officialUrl,
          @required this.tags});
    
      factory ProjectArrayEntity.fromJson(Map<String, dynamic> json) =>
          _$ProjectArrayEntityFromJson(json);
    }
    
    @JsonSerializable()
    class TagsEntity extends Object with _$TagsEntitySerializerMixin {
      final String createTime;
      final String name;
      final String userName;
      final String type;
    
      TagsEntity(
          {@required this.createTime,
          @required this.name,
          @required this.userName,
          @required this.type});
    
      factory TagsEntity.fromJson(Map<String, dynamic> json) =>
          _$TagsEntityFromJson(json);
    }

###### 命令

一次性生成

    flutter packages pub run build_runner build
    
监听生成

    flutter packages pub run build_runner watch
