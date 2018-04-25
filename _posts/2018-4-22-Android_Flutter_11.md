---
layout:     post
title:      "Flutter系列--第十一章:处理 assets 和 图像"
subtitle:   "在Flutter中添加 assets 和 image"
date:       2018-4-22
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

## 介绍

`flutter`中项目中可以放置代码和资源,资源是除却代码之外项目中需要的文件，可以在运行的时候访问,常见的资源有：

* 静态数据(例如：Json 数据)
* 配置文件
* 图标
* 图片

## 指定 `assets`

`flutter`使用[pubspec.yaml](https://www.dartlang.org/tools/pub/pubspec)指定资源的位置并识别这些资源

例如指定本地图片：

	flutter:
	  assets:
	    - assets/my_icon.png
	    - assets/background.png

该`assets`指定一些图片,每个图片都要通过`pubspec.yaml`指定路径,顺序没有强制要求,随意即可，目录名称也不重要，可以随意更改。例如：

	flutter:
	  assets:
	    - images/my_icon.png
	    - images/background.png

在运行的时候,`fltter`会将这些资源放在`asset bundle`，可以在运行时读取这些资源

#### 资源变体

资源变体的概念：不同版本的资源可能会显示在不同的上下文中,在`assets`部分中在`pubspec.yaml`中指定资源路径时，构建时会在相邻的目录中查找具有相同名称的文件,这些文件随后会和指定的资源一起被打包在`assets bundle`中

例如应用程序中有以下文件：
	
	  .../pubspec.yaml
	  .../graphics/my_icon.png
	  .../graphics/background.png
	  .../graphics/dark/background.png
	  ...etc.
	 
并且在`pubspec.yaml`指定了资源文件：

	flutter:
	  assets:
	    - graphics/background.png

那么`graphics/background.png`和`graphics/dark/background.png`将会包含在`assets bundle`中，前者是主要资源,后者则被认为是一种变体

目前会根据不同分辨率的设备加载图片,将来可能会扩展到本地化，提示等方面

## 加载 `assets`

应用可以通过[AssetBundle](https://docs.flutter.io/flutter/services/AssetBundle-class.html)访问`assets`

有两种方法可以加载字符串或者图片，只需要在`pubspec.yaml`中指定这些资源的路径即可

#### 加载文本

每个程序中都会有一个[rootBunlde](https://docs.flutter.io/flutter/services/rootBundle.html)对象,可以很轻松的访问主资源包,可以直接使用`package:flutter/services.dart`中全局静态`rootBundle`对象来加载`assets`

但是建议使用[DefaultAssetBundle](https://docs.flutter.io/flutter/widgets/DefaultAssetBundle-class.html)来获取`BuildContext`的`AssetBundle`， 这种方法不是使用应用程序构建的默认`asset bundle`，而是使父`widget`在运行时替换的不同的`AssetBundle`，这对于本地化或者测试很有用。

通常，可以使用`DefaultAssetBundle.of()`从应用运行时的`rootBundle`加载`asset`（例如`JSON`文件）。

在`Widget`上下文之外，或`AssetBundle`不可用时，可以使用`rootBundle`直接加载这些`asset`

例如：

	import 'dart:async' show Future;
	import 'package:flutter/services.dart' show rootBundle;
	
	Future<String> loadAsset() async {
	  return await rootBundle.loadString('assets/config.json');
	}

#### 加载图片

###### 声明图片

`flutter`可以根据当前设备的像素加载不同分辨率的图片，[AssetImage](https://docs.flutter.io/flutter/painting/AssetImage-class.html)了解怎么根据设备的像素加载不同的图片,只需要在`pubspec.yaml`中指定不同分辨率的图片即可

	  .../image.png
	  .../Mx/image.png
	  .../Nx/image.png
	  ...etc.

`M`和`N`是数字标识符,类似于`iOS`中的那样,

假如主要的资源对应`1.0`分辨率的设备,那么考虑用以下命名方式指定`assets`

	  .../my_icon.png
	  .../2.0x/my_icon.png
	  .../3.0x/my_icon.png
	  
在设备像素比例为`1.8`的设备上，会选择`2.0x`下的文件,对于`2.7`的比例设备上则会选择`3.0x`下的问题件

如果没有在`Image`控件中指定图片的宽高,则使用默认分辨率来缩放资源，以便和主要资源占用相同的大小,只不过分辨率会更好,也就是说,如果`.../my_icon.png`是`72px`乘`72px`，那么`.../3.0x/my_icon.png`应该是`216px`乘`216px`， 但是如果未指定宽高，它们都将渲染为`72`像素×`72`像素（以逻辑像素为单位）

`pubspec.yaml`中`asset`部分的每一项都应该与实际文件相对应，但主资源项除外,当主资源缺少某个资源时，会按分辨率从低到高的顺序去选择 

###### 加载图片

如果要加载图片,请在`widget`的`build`方法中使用[AssetImage](https://docs.flutter.io/flutter/painting/AssetImage-class.html)

	Widget build(BuildContext context) {
	  // ...
	  return new DecoratedBox(
	    decoration: new BoxDecoration(
	      image: new DecorationImage(
	        image: new AssetImage('graphics/background.png'),
	        // ...
	      ),
	      // ...
	    ),
	  );
	  // ...
	}
	
如果使用默认的`assets bundle`加载资源时，内部会自动处理分辨率,如果使用更低级别的类,例如[ImageStream](https://docs.flutter.io/flutter/painting/ImageStream-class.html)和[ImageCache](https://docs.flutter.io/flutter/painting/ImageCache-class.html)，你还会注意到与缩放有关的参数

#### 依赖包中的资源

要加载依赖包中的图像，必须要给`AssetImage`提供`package`参数

例如应用程序依赖于一个名为`my_icons`的包,这个包具有以下结构：

	  .../pubspec.yaml
	  .../icons/heart.png
	  .../icons/1.5x/heart.png
	  .../icons/2.0x/heart.png
	  ...etc.
	  

那么加载图像：


 	`new AssetImage('icons/heart.png', package: 'my_icons')`
 	
 	
包使用自己的资源也应该加上`package`参数来获取。

###### 打包 package assets

如果在`pubspec.yaml`中指定了期望的资源,那么它将打包到相应的`package`中,特别是,`package`本身使用的资源必须在`pubspec.yaml`中指定

软件包也可在选择在`lib/`文件夹中包含未在`pubspec.yaml`中指定的资源,在这种情况下,对于要打包的图片,应用程序必须在`pubspec.yaml`中指定包含了哪些图片

例如一个名为`fancy_backgrounds`的包可能包含以下文件

	  .../lib/backgrounds/background1.png
	  .../lib/backgrounds/background2.png
	  .../lib/backgrounds/background3.png
	  
要包含第一张图片,应该在`pubspec.yaml`中指定它：

	flutter:
	  assets:
	    - packages/fancy_backgrounds/backgrounds/background1.png

而`lib/`是隐含的，所以它不应该出现在路径中

## 和平台共享`assets`

通过`Android`上的`AssetManager`和`iOS`上的`NSBundle`，平台代码也随时可以使用`Flutter`资源。

#### android

`android`上可以通过[AssetManager](https://developer.android.com/reference/android/content/res/AssetManager.html)获取`asset`，
例如使用[openFd](https://developer.android.com/reference/android/content/res/AssetManager.html#openFd(java.lang.String))
根据`key`查找。

`key`可以使用[PluginRegistry.Registrar](https://docs.flutter.io/javadoc/io/flutter/plugin/common/PluginRegistry.Registrar.html)的`lookupKeyForAsset`和[FlutterView](https://docs.flutter.io/javadoc/io/flutter/view/FlutterView.html)的`getLookupKeyForAsset`获得，
`PluginRegistry.Registrar`在开发插件的时候非常适用,而`FlutterView`则在开发包括平台`view`的`app`时非常适用

示例：

    flutter:
      assets:
        - icons/heart.png
        
项目目录：

      .../pubspec.yaml
      .../icons/heart.png
      ...etc.
      
如果想在插件中访问`heart.png`，则可以

    AssetManager assetManager = registrar.context().getAssets();
    String key = registrar.lookupKeyForAsset("icons/heart.png");
    AssetFileDescriptor fd = assetManager.openFd(key);

#### ios

在`ios`中,`assets`可以使用[mainbundle](https://developer.apple.com/documentation/foundation/nsbundle/1410786-mainbundle)获取，
例如使用[ pathForResource:ofType: ](https://developer.apple.com/documentation/foundation/nsbundle/1410989-pathforresource)
根据`key`查找。

`key`可以使用[FlutterPluginRegistrar](https://docs.flutter.io/objcdoc/Protocols/FlutterPluginRegistrar.html)的`lookupKeyForAsset`和`lookupKeyForAsset:fromPackage:`,
或者[FlutterViewController](https://docs.flutter.io/objcdoc/Classes/FlutterViewController.html)的`lookupKeyForAsset`和`lookupKeyForAsset:fromPackage:`
`FlutterPluginRegistrar`在开发插件的时候非常适用,而`FlutterViewController`则在开发包括平台`view`的`app`时非常适用

和`android`示例相同,`ios`获取则可以

    NSString* key = [registrar lookupKeyForAsset:@"icons/heart.png"]; 
    NSString* path = [[NSBundle mainBundle] pathForResource:key ofType:nil];

更完整的示例，请查看[Flutter video_payer](https://pub.dartlang.org/packages/video_player)插件的实现。

## 使用平台资源

有时候可以直接在平台项目中使用`asset`。以下是在`Flutter`框架加载并运行之前使用资源的两种常见情况

#### 更新app图标

###### android

导航到`.../android/app/src/main/res`目录,`mipmap-`开头的各种文件夹放置的就是不同分辨率的图标,如果想替换,根据[Android开发人员指南](https://developer.android.com/guide/practices/ui_guidelines/icon_design_launcher.html#size)
替换相应的图片即可

注：
 
 如果想重命名图标,记得要在`AndroidManifest.xml`的`application`标签中替换修改后的名称

###### ios

导航到`.../ios/Runner`，该目录中的`Assets.xcassets/AppIcon.appiconset`已经包含了占位符图片，只需要根据[ios开发人员指南](https://developer.apple.com/ios/human-interface-guidelines/graphics/app-icon)
将它们替换为适当大小的图像即可，

#### 更新启动页

在`flutter`加载时，`flutter`也使用本地平台机制将过渡启动屏幕绘制到`flutter`应用程序,此启动屏幕将持续到`flutter`渲染应用程序的第一帧

也就意味着只要不调用`void main()`,屏幕将会一直显示启动页

###### android

导航到`.../android/app/src/main`，在`res/drawable/launch_background.xml`中已经有一个示例,根据示例可以自定义启动页

使用者可以通过[LayerList](https://developer.android.com/guide/topics/resources/drawable-resource.html#LayerList)自定义启动页,也可以使用其他[drawable](https://developer.android.com/guide/topics/resources/drawable-resource.html)

###### ios

导航至`.../ios/Runner`,在`Assets.xcassets/LaunchImage.imageset`，替换`LaunchImage.png`，`LaunchImage@2x.png`，`LaunchImage@3x.png`即可，如果要修改名字,则要更新`Contents.json`

也可以通过`xcode`自定义启动页
