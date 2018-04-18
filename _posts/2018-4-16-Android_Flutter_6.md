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

	
其实这篇`Blog`在写的过程中是越写越不想写,因为作者本身就有一定的`React-Native`开发经验,而`flutter`是类似于那种语法的,
所以很多东西在还没有写之前就已经知道怎么在`flutter`中写,可能估计大概还是因为懒吧...
	

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

`android`中`intent`主要用于跳转或者通信，但是`flutter`没有`intent`这个概念,如果想在`flutter`中启动`intent`
，可以使用[android_intent](https://pub.dartlang.org/packages/android_intent)实现,`flutter`并不等同于`activity`或者`fragment`，
尽管在`flutter`中使用`route`和`Navigator`进行界面跳转，其实都是在一个`actity`中实现的。

在`flutter`中`Route`是`app`的`screen`或者`page`，`Navigator`则是管理`route`的`widget`,`navigator`可以使用`push`或者`pop`来进行
跳转或者退出页面

和`android`的`AndroidManifest`类似,在`flutter`中可以将`routes`传递给顶级的`MaterialApp`来声明所有`route`

	void main() {
	  runApp(new MaterialApp(
		home: new MyAppHome(), // becomes the route named '/'
		routes: <String, WidgetBuilder> {
		  '/a': (BuildContext context) => new MyPage(title: 'page A'),
		  '/b': (BuildContext context) => new MyPage(title: 'page B'),
		  '/c': (BuildContext context) => new MyPage(title: 'page C'),
		},
	  ));
	}
	
然后可以使用`Navigator`push到想要的界面

	Navigator.of(context).pushNamed('/b');
	
`Intent`还可以调用外部组件,例如`Camera`或者`File picker`,可以使用[现有的插件](https://pub.dartlang.org/flutter/)或者自己集成

#### 处理其他app传入的`intent`

`flutter`可以直接与`activity`通信

这个例子在`flutter`的`android`代码中注册一个文本共享器，然后其他应用分享文本到`flutter`程序

首先注册`intent`

	<activity
	  android:name=".MainActivity"
	  android:launchMode="singleTop"
	  android:theme="@style/LaunchTheme"
	  android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale|layoutDirection"
	  android:hardwareAccelerated="true"
	  android:windowSoftInputMode="adjustResize">
	  <!-- ... -->
	  <intent-filter>
		<action android:name="android.intent.action.SEND" />
		<category android:name="android.intent.category.DEFAULT" />
		<data android:mimeType="text/plain" />
	  </intent-filter>
	</activity>

然后在`MainActivity`中处理接收到的文本并分享给`flutter`


	package com.yourcompany.shared;

	import android.content.Intent;
	import android.os.Bundle;

	import java.nio.ByteBuffer;

	import io.flutter.app.FlutterActivity;
	import io.flutter.plugin.common.ActivityLifecycleListener;
	import io.flutter.plugin.common.MethodCall;
	import io.flutter.plugin.common.MethodChannel;
	import io.flutter.plugins.GeneratedPluginRegistrant;

	public class MainActivity extends FlutterActivity {

	  private String sharedText;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GeneratedPluginRegistrant.registerWith(this);
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
		  if ("text/plain".equals(type)) {
			handleSendText(intent); // Handle text being sent
		  }
		}

		new MethodChannel(getFlutterView(), "app.channel.shared.data")
		  .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
			@Override
			public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
			  if (methodCall.method.contentEquals("getSharedText")) {
				result.success(sharedText);
				sharedText = null;
			  }
			}
		  });
	  }

	  void handleSendText(Intent intent) {
		sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	  }
	}
	
最后在`flutter`的`widget`中获取传递的数据

	import 'package:flutter/material.dart';
	import 'package:flutter/services.dart';

	void main() {
	  runApp(new SampleApp());
	}

	class SampleApp extends StatelessWidget {
	  // This widget is the root of your application.
	  @override
	  Widget build(BuildContext context) {
		return new MaterialApp(
		  title: 'Sample Shared App Handler',
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
	  static const platform = const MethodChannel('app.channel.shared.data');
	  String dataShared = "No data";

	  @override
	  void initState() {
		super.initState();
		getSharedText();
	  }

	  @override
	  Widget build(BuildContext context) {
		return new Scaffold(body: new Center(child: new Text(dataShared)));
	  }

	  getSharedText() async {
		var sharedData = await platform.invokeMethod("getSharedText");
		if (sharedData != null) {
		  setState(() {
			dataShared = sharedData;
		  });
		}
	  }
	}

#### startActivityForResult

在`flutter`中可以通过`Navigator`传递数据,

例如想得知用户选择的结果：

* 跳转到指定的页面

	Map coordinates = await Navigator.of(context).pushNamed('/location');
	
* 然后回退的时候传递数据

	Navigator.of(context).pop({"lat":43.821757,"long":-79.226392});


## 异步

#### android-runOnUiThread

`dart`是单线程的,但是支持`Isolate`进行多线程操作、事件循环、异步编程，除非使用`Isolate`，否则`dart`一直
都会在`UI`线程运行,并由事件循环驱动,`flutter`的事件循环相当于`android`的`Looper`

`dart`的单线程模型并不意味者使用者要把所有的操作都作为阻塞操作去运行,和`android`不同的是,`flutter`可以使用`dart`
提供的异步工具进行异步操作(例如`async` or `await`).

例如示例,运行网络请求代码并不会导致`UI`线程阻塞

	loadData() async {
	  String dataURL = "https://jsonplaceholder.typicode.com/posts";
	  http.Response response = await http.get(dataURL);
	  setState(() {
		widgets = json.decode(response.body);
	  });
	}
	
`await`等网络请求完成之后，可以重新调用`setState`刷新`UI`,官网提供了一个异步请求数据并刷新`ListView`的示例

	import 'dart:convert';

	import 'package:flutter/material.dart';
	import 'package:http/http.dart' as http;

	void main() {
	  runApp(new SampleApp());
	}

	class SampleApp extends StatelessWidget {
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
	  List widgets = [];

	  @override
	  void initState() {
		super.initState();

		loadData();
	  }

	  @override
	  Widget build(BuildContext context) {
		return new Scaffold(
		  appBar: new AppBar(
			title: new Text("Sample App"),
		  ),
		  body: new ListView.builder(
			  itemCount: widgets.length,
			  itemBuilder: (BuildContext context, int position) {
				return getRow(position);
			  }));
	  }

	  Widget getRow(int i) {
		return new Padding(
		  padding: new EdgeInsets.all(10.0),
		  child: new Text("Row ${widgets[i]["title"]}")
		);
	  }

	  loadData() async {
		String dataURL = "https://jsonplaceholder.typicode.com/posts";
		http.Response response = await http.get(dataURL);
		setState(() {
		  widgets = json.decode(response.body);
		});
	  }
	}

#### 切换线程

`android`中如果要进行网络请求都会放在子线程，避免出现`ANR`,例如`AsyncTask`.
但是`flutter`是单线程并且由事件驱动(例如`node`),所以这里不存在什么`AsyncTask`或者其他的工具。

例如上面的示例：

对于`IO`操作,可以声明为一个`async`，并使用`await`等待方法执行完成即可。
这在网络请求或者数据库操作中是一个非常常见的例子

	loadData() async {
	  String dataURL = "https://jsonplaceholder.typicode.com/posts";
	  http.Response response = await http.get(dataURL);
	  setState(() {
		widgets = json.decode(response.body);
	  });
	}
	
在`android`中有`AsyncTask`去执行耗时操作,但是在`flutter`中没有这么麻烦,使用者只需要`await`住一个方法,剩下的事`dart`会搞定.
	
当然网络请求中会有延迟,有时候网络不好的情况下时间会很久,不可能让使用者就在那里傻等着,我们应该给使用者一个进度提示或者其他的
比较醒目的提示.

如果大量数据的请求操作,可以使用`Isolate`完成,`Isolate`是一个独立的线程,它运行的时候不会和主线程进行任何的内存共享,
这就意味者如果使用`Isolate`,那么就不能直接从主线程进行`setState`或者使用变量,

官网提供了一个简单的示例,讲了如果在使用`Isolate`的时候共享数据或者刷新`UI`

`dataLoader`方法是使用`Isolate`独立运行的，在这个方法中,可以进行更复杂的数据处理,例如很多的`Json`数据

	loadData() async {
	  ReceivePort receivePort = new ReceivePort();
	  await Isolate.spawn(dataLoader, receivePort.sendPort);

	  // The 'echo' isolate sends its SendPort as the first message
	  SendPort sendPort = await receivePort.first;

	  List msg = await sendReceive(sendPort, "https://jsonplaceholder.typicode.com/posts");

	  setState(() {
		widgets = msg;
	  });
	}

	// The entry point for the isolate
	static dataLoader(SendPort sendPort) async {
	  // Open the ReceivePort for incoming messages.
	  ReceivePort port = new ReceivePort();

	  // Notify any other isolates what port this isolate listens to.
	  sendPort.send(port.sendPort);

	  await for (var msg in port) {
		String data = msg[0];
		SendPort replyTo = msg[1];

		String dataURL = data;
		http.Response response = await http.get(dataURL);
		// Lots of JSON to parse
		replyTo.send(json.decode(response.body));
	  }
	}

	Future sendReceive(SendPort port, msg) {
	  ReceivePort response = new ReceivePort();
	  port.send([msg, response.sendPort]);
	  return response.first;
	}
	
下面这个是完整的可运行的一个示例：

	import 'dart:convert';

	import 'package:flutter/material.dart';
	import 'package:http/http.dart' as http;
	import 'dart:async';
	import 'dart:isolate';

	void main() {
	  runApp(new SampleApp());
	}

	class SampleApp extends StatelessWidget {
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
	  List widgets = [];

	  @override
	  void initState() {
		super.initState();
		loadData();
	  }

	  showLoadingDialog() {
		if (widgets.length == 0) {
		  return true;
		}

		return false;
	  }

	  getBody() {
		if (showLoadingDialog()) {
		  return getProgressDialog();
		} else {
		  return getListView();
		}
	  }

	  getProgressDialog() {
		return new Center(child: new CircularProgressIndicator());
	  }

	  @override
	  Widget build(BuildContext context) {
		return new Scaffold(
			appBar: new AppBar(
			  title: new Text("Sample App"),
			),
			body: getBody());
	  }

	  ListView getListView() => new ListView.builder(
		  itemCount: widgets.length,
		  itemBuilder: (BuildContext context, int position) {
			return getRow(position);
		  });

	  Widget getRow(int i) {
		return new Padding(padding: new EdgeInsets.all(10.0), child: new Text("Row ${widgets[i]["title"]}"));
	  }

	  loadData() async {
		ReceivePort receivePort = new ReceivePort();
		await Isolate.spawn(dataLoader, receivePort.sendPort);

		// The 'echo' isolate sends its SendPort as the first message
		SendPort sendPort = await receivePort.first;

		List msg = await sendReceive(sendPort, "https://jsonplaceholder.typicode.com/posts");

		setState(() {
		  widgets = msg;
		});
	  }

	// the entry point for the isolate
	  static dataLoader(SendPort sendPort) async {
		// Open the ReceivePort for incoming messages.
		ReceivePort port = new ReceivePort();

		// Notify any other isolates what port this isolate listens to.
		sendPort.send(port.sendPort);

		await for (var msg in port) {
		  String data = msg[0];
		  SendPort replyTo = msg[1];

		  String dataURL = data;
		  http.Response response = await http.get(dataURL);
		  // Lots of JSON to parse
		  replyTo.send(json.decode(response.body));
		}
	  }

	  Future sendReceive(SendPort port, msg) {
		ReceivePort response = new ReceivePort();
		port.send([msg, response.sendPort]);
		return response.first;
	  }
	}

#### android-okhttp

`flutter`中网络请求可以使用[http软件包](https://pub.dartlang.org/packages/http)，
虽然它没有完全实现`okhttp`的所有功能,但是已经扩展出了很多自己的方法,让网络请求变得非常简单

可以通过在`pubspec.yaml`中添加依赖去使用

	dependencies:
	  ...
	  http: '>=0.11.3+16'

然后进行网络请求,使用者只需要`await`执行一个`async`方法即可

	import 'dart:convert';

	import 'package:flutter/material.dart';
	import 'package:http/http.dart' as http;
	[...]
	  loadData() async {
		String dataURL = "https://jsonplaceholder.typicode.com/posts";
		http.Response response = await http.get(dataURL);
		setState(() {
		  widgets = json.decode(response.body);
		});
	  }
	}

#### 显示进度条

`android`中可以通过`ProgressBar`在执行耗时操作的时候,
在`flutter`中可以使用`ProgressIndicator`，可以在耗时方法执行之前显示,并在执行完成之后隐藏掉。

	import 'dart:convert';

	import 'package:flutter/material.dart';
	import 'package:http/http.dart' as http;

	void main() {
	  runApp(new SampleApp());
	}

	class SampleApp extends StatelessWidget {
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
	  List widgets = [];

	  @override
	  void initState() {
		super.initState();
		loadData();
	  }

	  showLoadingDialog() {
		return widgets.length == 0;
	  }

	  getBody() {
		if (showLoadingDialog()) {
		  return getProgressDialog();
		} else {
		  return getListView();
		}
	  }

	  getProgressDialog() {
		return new Center(child: new CircularProgressIndicator());
	  }

	  @override
	  Widget build(BuildContext context) {
		return new Scaffold(
			appBar: new AppBar(
			  title: new Text("Sample App"),
			),
			body: getBody());
	  }

	  ListView getListView() => new ListView.builder(
		  itemCount: widgets.length,
		  itemBuilder: (BuildContext context, int position) {
			return getRow(position);
		  });

	  Widget getRow(int i) {
		return new Padding(padding: new EdgeInsets.all(10.0), child: new Text("Row ${widgets[i]["title"]}"));
	  }

	  loadData() async {
		String dataURL = "https://jsonplaceholder.typicode.com/posts";
		http.Response response = await http.get(dataURL);
		setState(() {
		  widgets = json.decode(response.body);
		});
	  }
	}

## 项目资源和结构

#### 图片

`android`的图片都是放在`drawable`目录下,但是`flutter`没有这个概念,
`flutter`采用的是`ios`那种格式,图片可以是`1x`,`2x`,`3x`或者其他的倍数

`flutter`上的图片或者某些文件可以放在任意文件夹中; 没有强制性的文件夹结构。
只需要将该文件夹在`pubspec`文件中引用,`flutter`会自己处理。

例如新添加一个`my_icon.png`图片,放在`images`文件夹中

	images/my_icon.png       // Base: 1.0x image
	images/2.0x/my_icon.png  // 2.0x image
	images/3.0x/my_icon.png  // 3.0x image

然后在`pubspec.yaml`中声明路径：

	assets:
	 - images/my_icon.jpeg
	 
使用`AssetImage`获取图片：

	return new AssetImage("images/a_dot_burr.jpeg");

或者使用`Image Widget`直接获取：

	@override
	Widget build(BuildContext context) {
	  return new Image.asset("images/my_image.png");
	}

`flutter`也不存在`dp`这种概念,具体的可以看看[devicePixelRatio](https://docs.flutter.io/flutter/dart-ui/Window/devicePixelRatio.html)

Android密度限定符 |	  颤动像素比例
ldpi	         |	  0.75x
mdpi	         |	  1.0x
hdpi	         |	  1.5x
xhdpi	         |	  2.0x
xxhdpi	         |	  3.0x
xxxhdpi	         |	  4.0x


* 在`flutter beta 2`之前，`flutter`中定义的图片或者某些文件无法从本地访问，
反之亦然，`flutter`不提供`assets`资产和资源，因为它们位于单独的文件夹中。

从`flutter beta 2`开始，`flutter` `assets`存储在本地`assets`文件夹中，
可以通过`Android`访问`AssetManager`

	val flutterAssetStream = assetManager.open("flutter_assets/assets/my_flutter_asset.png")

从`flutter beta 2`开始，`flutter`仍然无法访问本地资源，也无法访问本地资源.

#### 字符串

`flutter`没有专门的字符串资源系统,所以直接声明即可：

	class Strings {
	  static String welcomeMessage = "Welcome To Flutter";
	}

然后在代码中：

	new Text(Strings.welcomeMessage)

推荐使用[intl](https://pub.dartlang.org/packages/intl)进行国际化和本地化
	
#### android-gradle

在`pubspec.yaml`声明依赖

可以在[packages](https://pub.dartlang.org/flutter/packages/)寻找自己需要的依赖

## Activity or Fragment

#### android-activity-fragment

在`android`中,`activity`就是一个页面,而`fragment`都是依赖于`activity`存在，
但是在`flutter`中这两种都等价于`widget`

正如`intent`部分所述,`flutter`中的屏幕由`Widget`表示，
因为所有东西都是`flutter`中的一个`widget`.
可以使用`Navigator`在不同的`Route`之间移动，这些`Route`代表不同的屏幕或页面，
或者可能只是不同的状态或同一数据的呈现。

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
