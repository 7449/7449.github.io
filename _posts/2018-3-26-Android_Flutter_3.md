---
layout:     post
title:      "Flutter系列--第三章:编写一个FlutterApp"
subtitle:   "本章讲解如何编写一个FlutterApp"
date:       2018-3-26
author:     "y"
header-mask: 0.3
header-img: "img/header_flutter.png"
catalog: true
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

[Flutter番外篇:Dart](https://7449.github.io/2018/03/18/Android_Flutter_dart/)<br>
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
[第十一章：处理 assets 和 图像](https://7449.github.io/2018/04/22/Android_Flutter_11/)<br>

如果没有意外，本系列的相关代码都是由Android Studio 创建并打开

## Demo

> 按照官网的流程来创建第一个小demo

[Write Your First Flutter App](https://flutter.io/get-started/codelab/)


#### 修改 Main Dart

删除`Main.dart`下面所有的代码并将下面代码复制进去,不出意外的话 首页会显示一个`Hello World` title变为`First Flutter Demo`


    import 'package:flutter/material.dart';
    
    void main() => runApp(new MyApp());
    
    class MyApp extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'First Flutter Demo',
          home: new Scaffold(
            appBar: new AppBar(
              title: new Text('First Flutter Demo'),
            ),
            body: new Center(
              child: new Text('Hello World'),
            ),
          ),
        );
      }
    }

相关的[ Material Widgets ](https://material.io/guidelines/)挺丰富的，可以抽空去看下

#### 引用开源包

在`pubspec.yaml`下添加`english_words: ^3.1.0`，如代码：

    name: first_flutter_app
    description: A new Flutter application.
    
    dependencies:
      flutter:
        sdk: flutter
    
      # The following adds the Cupertino Icons font to your application.
      # Use with the CupertinoIcons class for iOS style icons.
      cupertino_icons: ^0.1.0
      english_words: ^3.1.0  //添加这句
      
      
然后点击`Packages get`,如果网络没有问题依赖成功的话控制台会输出：

    Running "flutter packages get" in first_flutter_app...
    Process finished with exit code 0


在`Main.dart`中添加依赖，就是导包

    import 'package:flutter/material.dart';
    import 'package:english_words/english_words.dart';

暂时没有引用的话，导入的包和`java`类似，是灰色的，使用`english_word`替换`hello world`,代码如下所示


    import 'package:english_words/english_words.dart';
    import 'package:flutter/material.dart';
    
    void main() => runApp(new MyApp());
    
    class MyApp extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'First Flutter Demo',
          home: new Scaffold(
            appBar: new AppBar(
              title: new Text('First Flutter Demo'),
            ),
            body: new Center(
              child: new Text(new WordPair.random().asPascalCase),
            ),
          ),
        );
      }
    }

生成随机字符串，如果每次`Hot Code`的话，就会发现主页会显示不同的英文单词
这里官网推荐的是按钮操作，其实 `command + s` 也会触发热更新，就算没有修改任何代码


#### 有状态的 widget

> 其实 widget 分为有状态的或者无状态的，这里不做解释，只是简单使用下

其实这里就是创建了一个组件然后替换 `new Text(new WordPair.random().asPascalCase)`

就是教使用者如何创建一个类似于`ReactNative`组件的 `widget`


将以下代码放在`Main.dart`底部, `new RandomWordsState()` 会爆红，这里直接使用`IDEA`的快捷键创建。

    class RandomWords extends StatefulWidget {
      @override
      createState() => new RandomWordsState();
    }
    
如以下代码:

    class RandomWords extends StatefulWidget {
      @override
      createState() => new RandomWordsState();
    }
    
    class RandomWordsState {
    }
    
还在爆红,原因是 `RandomWordsState` 没有初始化的原因，将其修改为：

    class RandomWordsState extends State<RandomWords> {
      @override
      Widget build(BuildContext context) {
        final wordPair = new WordPair.random();
        return new Text(wordPair.asPascalCase);
      }
    }


完整代码：


    import 'package:english_words/english_words.dart';
    import 'package:flutter/material.dart';
    
    void main() => runApp(new MyApp());
    
    class MyApp extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'First Flutter Demo',
          home: new Scaffold(
            appBar: new AppBar(
              title: new Text('First Flutter Demo'),
            ),
            body: new Center(
              child: new RandomWords(),
            ),
          ),
        );
      }
    }
    
    class RandomWords extends StatefulWidget {
      @override
      createState() => new RandomWordsState();
    }
    
    class RandomWordsState extends State<RandomWords> {
      @override
      Widget build(BuildContext context) {
        final wordPair = new WordPair.random();
        return new Text(wordPair.asPascalCase);
      }
    }
    
    
通过这里可以学习到，如果抽取相同功能，然后封装

#### ListView

注意点:

* `Dart` 中使用下划线 `_` 开头的表示强制私有

修改之前创建的`RandomWordsState`

创建一个 变量 `_suggestions` 为数组，待会要用到，再创建一个 style  `biggerFont` 用来增大字体


代码如下：

    class RandomWordsState extends State<RandomWords> {
      final _suggestions = <WordPair>[];
    
      final _biggerFont = const TextStyle(fontSize: 18.0);
    
      @override
      Widget build(BuildContext context) {
        final wordPair = new WordPair.random();
        return new Text(wordPair.asPascalCase);
      }
    }
    
然后创建`_buildSuggestions`方法,初始化一个`ListView`

    class RandomWordsState extends State<RandomWords> {
      final _suggestions = <WordPair>[];
    
      final _biggerFont = const TextStyle(fontSize: 18.0);
    
      @override
      Widget build(BuildContext context) {
        final wordPair = new WordPair.random();
        return new Text(wordPair.asPascalCase);
      }
    
    
      Widget _buildSuggestions() {
        return new ListView.builder(
            padding: const EdgeInsets.all(16.0),
            itemBuilder: (context, i) {
              if (i.isOdd) return new Divider();
              final index = i ~/ 2;
              if (index >= _suggestions.length) {
                _suggestions.addAll(generateWordPairs().take(10));
              }
              return _buildRow(_suggestions[index]);
            }
        );
      }
    }

`ListView` 提供了一个 `itemBuilder` 属性，这是一个工厂 `builder` 并作为匿名函数进行回调。
它有两个传入参数`BuildContext` 和`index` .

`_buildRow`生成需要的`item`

      Widget _buildRow(WordPair pair) {
        return new ListTile(
          title: new Text(
            pair.asPascalCase,
            style: _biggerFont,
          ),
        );
      }

然后使用`ListView`,完整的`RandomWordsState`代码如下：


    class RandomWordsState extends State<RandomWords> {
      final _suggestions = <WordPair>[];
    
      final _biggerFont = const TextStyle(fontSize: 18.0);
    
      @override
      Widget build(BuildContext context) {
    //    final wordPair = new WordPair.random();
    //    return new Text(wordPair.asPascalCase);
        return new Scaffold (
          appBar: new AppBar(
            title: new Text('List Demo'),
          ),
          body: _buildSuggestions(),
        );
      }
    
    
      Widget _buildSuggestions() {
        return new ListView.builder(
            padding: const EdgeInsets.all(16.0),
            itemBuilder: (context, i) {
              if (i.isOdd) return new Divider();
              final index = i ~/ 2;
              if (index >= _suggestions.length) {
                _suggestions.addAll(generateWordPairs().take(10));
              }
              return _buildRow(_suggestions[index]);
            }
        );
      }
    
      Widget _buildRow(WordPair pair) {
        return new ListTile(
          title: new Text(
            pair.asPascalCase,
            style: _biggerFont,
          ),
        );
      }
    }

这个时候会发现Demo有两个`title`，简单想下就应该知道是什么原因了.

这个时候替换`MyApp`的`home`，让`RandomWordsState`统一管理界面


    class MyApp extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'First Flutter Demo',
          home: new RandomWords(),
        );
      }
    }

## 点击

> 每个item都有个爱心，点击收藏或者取消收藏，创建一个集合保存收藏过的数据

`set`不允许重复数据，所以这里比较适合一点

    class RandomWordsState extends State<RandomWords> {
      final _suggestions = <WordPair>[];
      final _saved = new Set<WordPair>();
      final _biggerFont = const TextStyle(fontSize: 18.0);
    }

修改`_buildRow`添加一个参数来确定是否收藏过并添加一个心形图标
    
    Widget _buildRow(WordPair pair) {
      final alreadySaved = _saved.contains(pair);
      return new ListTile(
        title: new Text(
          pair.asPascalCase,
          style: _biggerFont,
        ),
        trailing: new Icon(
          alreadySaved ? Icons.favorite : Icons.favorite_border,
          color: alreadySaved ? Colors.red : null,
        ),
      );
    }

这个时候会在每个`item`上显示一个心形图标，但是没有点击效果，接下来继续修改`buildRow`添加`item`的点击事件

      Widget _buildRow(WordPair pair) {
        final alreadySaved = _saved.contains(pair);
        return new ListTile(
          title: new Text(
            pair.asPascalCase,
            style: _biggerFont,
          ),
          trailing: new Icon(
            alreadySaved ? Icons.favorite : Icons.favorite_border,
            color: alreadySaved ? Colors.red : null,
          ),
          onTap: () {
            setState(() {
              if (alreadySaved) {
                _saved.remove(pair);
              } else {
                _saved.add(pair);
              }
            });
          },
        )
        
这里和`react native`相似，调用`setState`实现对数据的改变以及UI的重绘，而且自带水波纹效果,这个比较赞


#### 跳转新页面

要实现的功能：

状态栏添加一个图标，点击图标跳转到新页面然后新页面显示已经收藏过的词组

> 添加图标并实现点击事件

    class RandomWordsState extends State<RandomWords> {
      @override
      Widget build(BuildContext context) {
        return new Scaffold (
          appBar: new AppBar(
            title: new Text('List Demo'),
            actions: <Widget>[
              new IconButton(icon: new Icon(Icons.list), onPressed: _pushSaved),
            ],
          ),
          body: _buildSuggestions(),
        );
      }
      void _pushSaved() {
      }
    }
    
重载会发现`toolbar`出现了一个图标并可以点击

页面的跳转可以简单使用`push` or `pull`来前进或者回退页面


push：

    Navigator.of(context).push(
    );
    
可以点进去看下，需要的是一个`Route`对象,这里使用`MaterialPageRoute`

完整代码如下所示，并已经添加注释：

      void _pushSaved() {
        Navigator.of(context).push(
          new MaterialPageRoute(
            // builder 返回一个 widget
            builder: (context) {
              //这里使用map循环生成 tiles
              final tiles = _saved.map(
                    (pair) {
                  return new ListTile(
                    title: new Text(
                      pair.asPascalCase,
                      style: _biggerFont,
                    ),
                  );
                },
              );
              // 添加水平间距，并转换成list
              final divided = ListTile.divideTiles(
                context: context,
                tiles: tiles,
              ).toList();
              //最终返回一个 ListView widget
              return new Scaffold(
                appBar: new AppBar(
                  title: new Text('Saved Suggestions'),
                ),
                body: new ListView(children: divided),
              );
            },
          ),
        );
      }


#### 修改主题

使用`ThemeData`修改主题

如下代码：

    class MyApp extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return new MaterialApp(
          title: 'First Flutter Demo',
          home: new RandomWords(),
          //修改主题
          theme: new ThemeData(
            primaryColor: Colors.white,
          ),
        );
      }
    }



## 结语


性能非常不错，如果熟悉`react native`开发，使用比较容易上手，不熟悉`dart`也可以，但还是建议抽时间看下`dart`

但是现在有个非常严重的缺点，回调地狱

相关代码也已经上传到github:[first_flutter_app](https://github.com/7449/AndroidDevelop/tree/studio3/first_flutter_app)
