---
layout:     post
title:      "Cordova系列---第二章：Cordova插件的使用"
subtitle:   "本章介绍了如何使用Cordova插件以及自己编写插件"
date:       2017-7-31
author:     "y"
header-mask: 0.3
header-img: "img/cordova.png"
catalog: true
tags:
    - cordova
---


* 本篇文章涉及到的资料

[CordovaCn](https://github.com/CordovaCn/CordovaCn)<br>
[Cordova-Examples](https://github.com/cfjedimaster/Cordova-Examples)<br>


## 示例

[cordova_plugin_network](https://github.com/7449/AndroidDevelop/tree/master/cordova_plugin_network)<br>


##  update 

`cordova`8.0版本可以支持直接`add`本地插件

cordova 改变了获取插件的方式,如果是本地安装，有两种办法：

* 将cordova的版本限制到 6.5.0

		sudo npm i -g cordova@6.5.0
		
* 使用旧版本的方法安装

		cordova plugin add ../my-plugin --nofetch


[failed-to-get-absolute-path-to-installed-module](https://stackoverflow.com/questions/43856285/failed-to-get-absolute-path-to-installed-module)

## 插件

android的Cordova插件是由webview来触发相应的功能

这里以`camera`作为实例：

安装指令：

`cordova plugin add cordova-plugin-camera`

	BUILD SUCCESSFUL
	
	Total time: 0.997 secs
	Subproject Path: CordovaLib
	Adding cordova-plugin-camera to package.json
	Saved plugin info for "cordova-plugin-camera" to config.xml
	
安装成功之后在`plugins`目录下发现下载相应的插件代码

[camera插件的使用方法以及api](https://github.com/CordovaCn/CordovaCn/blob/master/02%E6%8F%92%E4%BB%B6%E4%BD%BF%E7%94%A8(About%20Plugin)/02.cordova-plugin-camera.md)<br>
[Sample](https://github.com/cfjedimaster/Cordova-Examples/tree/master/camera_vintagejs)<br>

GitHub上面已经有详细的使用方法以及示例，所以这里就不多做介绍，接下来会介绍怎么自己写一个cordova插件

## 编写插件

> 需要注意的是，这个示例是在android代码中直接添加插件，所以`config.xml`为`android/res/xml/config.xml`而不是cordova目录下的`config.xml`

* 为什么编写插件？

* 答案很简单，现有的插件不足以满足我们多变的需求，或者已有的插件存在bug<br>
作者就在使用`camera`时发现在某些平板上面调用自带的裁剪无法返回正确的base64或者路径，返回的是未裁剪的，当然这个是平板的Bug<br>
也是因为Android本身的WebView太多太多坑了，相信做过webapp的都吃过WebView的坑<br>
作者在使用webView的时候发现相同的代码，在大部分平板以及大部分测试机上完美显示，但是在某些平板只是一片空白，一直没找到原因，但是使用`crosswalk-webview`的话是没有这些问题的，付出的代码就是App体积大了将近22M.代价确实有点大，但是为了适配暂时只能这样解决


所以适配的话还是由自己去写插件比较容易适配

* 怎么去编写一个Cordova插件？

* 很简单，上面我们已经成功安装了 Cordova Camera 插件，我们只要通过查看安装与未安装之前哪些文件发生了变化，新增了哪些文件，我们照猫画虎即可学习怎么去编写一个Cordova插件

 
通过观察安装插件前后的代码对比会发下如下变化：


* 多了`org.apache.cordova.camera`目录，里面包括了相应的java代码
* `config.xml` 里面多了如下代码：

	    <feature name="Camera">
	        <param name="android-package" value="org.apache.cordova.camera.CameraLauncher" />
	    </feature>
    
* `/assets/www` 目录下多了`plugins.cordova-plugin-camera.www`文件夹并且`cordova_plugins.js`多了如下格式的代码：

	    {
	        "id": "cordova-plugin-camera.camera",
	        "file": "plugins/cordova-plugin-camera/www/Camera.js", // js路径
	        "pluginId": "cordova-plugin-camera",
	        "clobbers": [
	            "navigator.camera"//调用方法名
	        ]
	    }

当然`package.json`中代码也发生了变化，但是我们不必去理会这些改动，和我们自己写插件没有关系。

这样通过这些变动我们可以自己照猫画虎动手去编写一个插件

接下来我们去编写一个简单的Dialog的插件

首先在`org.apache.cordova`目录下创建一个文件夹放置自己的代码,当然这个目录是随意的，但是为了整洁，插件代码建议统一放置在该目录下，文件夹名字暂且命名为`dialog`吧。

创建一个 `DialogPlugin ` 文件代码如下：

	public class DialogPlugin extends CordovaPlugin {
	
	    private static final String DIALOG_ACTION = "dialog:action";
	
	    @Override
	    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	        if (TextUtils.equals(action, DIALOG_ACTION)) {
	            new AlertDialog.Builder(cordova.getActivity())
	                    .setTitle("接受到的消息")
	                    .setMessage(args.toString()).show();
	            return true;
	        }
	        return false;
	    }
	}


继承自 `CordovaPlugin` ,并且重载`execute`，通过阅读代码可以得知，`execute` 有三个方法可以重载，最终调用的都是`execute(String, CordovaArgs, CallbackContext)`



    public boolean execute(String action, String rawArgs, CallbackContext callbackContext) throws JSONException {
        JSONArray args = new JSONArray(rawArgs);
        return execute(action, args, callbackContext);
    }


    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        CordovaArgs cordovaArgs = new CordovaArgs(args);
        return execute(action, cordovaArgs, callbackContext);
    }


    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        return false;
    }
    
    
在`config.xml`代码中注册编写好的插件

    <feature name="Dialog">
    		// value: 对应的java文件位置
        <param name="android-package" value="org.apache.cordova.dialog.DialogPlugin" />
    </feature>
    
添加之后代码如下所示：

	<?xml version='1.0' encoding='utf-8'?>
	<widget xmlns:cdv="http://cordova.apache.org/ns/1.0" id="com.hellocordova" version="1.0.0"
	    xmlns="http://www.w3.org/ns/widgets">
	    <feature name="Whitelist">
	        <param name="android-package" value="org.apache.cordova.whitelist.WhitelistPlugin" />
	        <param name="onload" value="true" />
	    </feature>
	    <feature name="Camera">
	        <param name="android-package" value="org.apache.cordova.camera.CameraLauncher" />
	    </feature>
	    <feature name="Dialog">
	        <param name="android-package" value="org.apache.cordova.dialog.DialogPlugin" />
	    </feature>
	    <name>HelloCordova</name>
	    <description>
	        A sample Apache Cordova application that responds to the deviceready event.
	    </description>
	    <author email="dev@cordova.apache.org" href="http://cordova.io">
	        Apache Cordova Team
	    </author>
	    <content src="index.html" />
	    <access origin="*" />
	    <allow-intent href="http://*/*" />
	    <allow-intent href="https://*/*" />
	    <allow-intent href="tel:*" />
	    <allow-intent href="sms:*" />
	    <allow-intent href="mailto:*" />
	    <allow-intent href="geo:*" />
	    <allow-intent href="market:*" />
	    <preference name="loglevel" value="DEBUG" />
	</widget>

    
在`cordova_plugins.js`中添加指令：

	cordova.define('cordova/plugin_list', function(require, exports, module) {
	module.exports = [
	    {
	        "id": "cordova-plugin-camera.camera",
	        "file": "plugins/cordova-plugin-camera/www/Camera.js",
	        "pluginId": "cordova-plugin-camera",
	        "clobbers": [
	            "navigator.camera"
	        ]
	    },
	    {
	        "id": "cordova-plugin-dialog.dialog",//cordova.define的id
	        "file": "plugins/cordova-plugin-dialog/Dialog.js",//js文件
	        "clobbers": [
	            "navigator.dialog" //js调用时方法名
	        ]
	    }
	];
	});

作者删掉了一些其他插件的代码避免看起来困难

接下来创建调用插件需要的js文件。在`www/plugins`目录下创建`cordova-plugin-dialog`目录，并且创建`Dialog.js`，如图所示：

![_config.yml]({{ site.baseurl }}/img/cordova_plugin_dialog.png)


对应的`Dialog.js`代码如下所示：


	// define中填写 cordova_plugins 中 填写的相应的 id
	cordova.define("cordova-plugin-dialog.dialog", function(require, exports, module) {
	
	var exec = require('cordova/exec'),
	var dialogExport = {};
	dialogExport.showDialog = function(successCallback, errorCallback, options) {
	    var args = [0,1,2,3];
		// 成功回调，失败回调，config.xml 中的 featureName,action,携带的参数
	    exec(successCallback, errorCallback, "Dialog", "dialog:action", args);
	};
	
	module.exports = dialogExport;
	
	});
	
至此，一个简单的插件编写完成了，可以在js代码中尝试运行查看下效果,修改`index.html`如下，只是简单的显示一个button：

	<!DOCTYPE html>
	<html>
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	    <title></title>
	    <meta name="description" content="">
	    <meta name="viewport" content="width=device-width">
	    <link rel="stylesheet" type="text/css" href="css/app.css"/>
	    <script>
			document.addEventListener("deviceready", init, false);
			        function init() {
			            function onSuccess(message) {
			                var options = {
			                    onError: function() {
			                        alert('ERROR');
			                    }
			                };
			            }
			
			            function onFail(message) {
			                alert('Failed because: ' + message);
			            }
			
			            document.querySelector("#showDialog").addEventListener("touchend", function() {
			                navigator.dialog.showDialog(onSuccess, onFail, {});
			            });
			}
	    </script>
	</head>
	<body>
	<button id="showDialog">showDialog</button>
	
	<script src="cordova.js"></script>
	</body>
	</html>
	
	
如果没问题，如图所示，可以接收到传递过来的参数，并且成功的弹在Dialog中，如果有错误，可以在chrome 中运行`chrome://inspect`查看设备报的什么错误


![_config.yml]({{ site.baseurl }}/img/cordova_plugin_success.jpg)


如果你使用Camera的插件会发现插件可以携带参数，就像这样：

		navigator.camera.getPicture(onSuccess, onFail, { 
			quality: 50,
			sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
			destinationType: Camera.DestinationType.FILE_URI
		});
		
接下来就简单的为Dialog添加携带参数，基本套路和编写插件没什么区别，

在 `Dialog.js` 同级目录下创建`DialogConstants.js`

	cordova.define("cordova-plugin-dialog.Dialog", function(require, exports, module) {
	    module.exports = {
	        DialogType:{
	
	            NIGHT:666
	
	        }
	    };
	});
	
在`cordova_plugins.js`中添加依赖：

	cordova.define('cordova/plugin_list', function(require, exports, module) {
	module.exports = [
	    {
	        "id": "cordova-plugin-dialog.dialog",//cordova.define的id
	        "file": "plugins/cordova-plugin-dialog/Dialog.js",//js文件
	        "clobbers": [
	            "navigator.dialog" //js调用时方法名
	        ]
	    },
	    {
	        "id": "cordova-plugin-dialog.Dialog",//cordova.define的id
	        "file": "plugins/cordova-plugin-dialog/DialogConstants.js",//js文件
	        "clobbers": [
	            "Dialog" //js调用时方法名
	        ]
	    }
	];
	});
	
然后修改`Dialog.js`：


	cordova.define("cordova-plugin-dialog.dialog", function(require, exports, module) {
	
	var exec = require('cordova/exec');
	var argscheck = require('cordova/argscheck');
	var dialogExport = {};
	
	dialogExport.showDialog = function(successCallback, errorCallback, options) {
	
	    var getValue = argscheck.getValue;
	    var dialogType = getValue(options.dialogType, -1);
	    var args = [dialogType];
	    exec(successCallback, errorCallback, "Dialog", "dialog:action", args);
	};
	module.exports = dialogExport;
	});

修改`index.js`：

		document.querySelector("#showDialog").addEventListener("touchend", function() {
			     navigator.dialog.showDialog(onSuccess, onFail, {

			         dialogType: Dialog.DialogType.NIGHT,

			      });
		});

运行成功之后如下图所示：

![_config.yml]({{ site.baseurl }}/img/cordova_plugin_success_type.jpg)


## 编写可安装的插件

> 这里的`config.xml`为cordova目录下的`config.xml`

目的：编写一个可以 通过 `cordova plugin add file(git)` 命令来安装的插件

通过安装Camera插件得知，插件都在`plugins`目录下，

通过阅读[Plugin配置文件指南](https://github.com/CordovaCn/CordovaCn/blob/master/01%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86(Basic%20Knowledge)/08.Plugin.xml%20Guide(Plugin%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E6%8C%87%E5%8D%97).md) [PluginDemoReadme](https://github.com/CordovaCn/CordovaPluginsDemo/blob/master/cordova-plugin-custom/README.md),掌握的信息足以我们编写一个可安装的插件

* 创建相应的文件，这里可以直接创建一个`dialog`文件夹，然后直接把`camera`插件文件夹下的东西copy过来，不需要的直接删除即可
，大概会剩下`LICENSE` `package.json` `plugin.xml` `readme` `src` `www`

LICENSE 可以不用去管，这只是一个开源协议

package.json: 里面包含了插件名称，描述，版本，id,平台，远程库地址，和一些作者信息

plugin.xml: 这个文件主要包含了 一些注入时需要的信息

src：文件夹里面放置 java 文件

www: 文件夹里面放置 js 文件


修改 package.json 文件，这里并没有添加远程库相关的东西：

	{
	      "name": "cordova-plugin-dialog",   // 插件名称
	      "version": "1.0.0",                // 版本号
	      "description": "Cordova dialog Plugin",  //版本说明
	      "cordova": {
	        "id": "cordova-plugin-dialog",   // id，重要，要和 plugin 目录名保持一致
	        "platforms": [                    
	          "android" //支持的平台，这里只是支持android平台
	        ]
	      },
	      // 用于搜索时的Key
	      "keywords": [                       
	        "cordova",
	        "dialog"
	      ],
	      "author": "xxx.com",      //作者信息 
	      "license": "Apache 2.0"   //开源协议 
	}

修改 plugin.xml :


	<?xml version="1.0" encoding="UTF-8"?>
	
	<plugin xmlns="http://apache.org/cordova/ns/plugins/1.0"
	           id="cordova-plugin-dialog"
	      version="1.0.0">
	    <name>cordova-plugin-dialog</name>
	    <description>Cordova dialog Plugin</description>
	    <license>Apache 2.0</license>
	    <keywords>cordova,dialog</keywords>
	    <js-module src="www/Dialog.js" name="dialog">
	        <clobbers target="navigator.dialog" />
	    </js-module>
	  
	    <platform name="android">
	        <config-file target="res/xml/config.xml" parent="/*">
	            <feature name="Dialog" >
	                <param name="android-package" value="org.apache.cordova.dialog.DialogPlugin"/>
	                <param name="onload" value="true" />
	            </feature>
	        </config-file>
	        <source-file src="src/android/DialogPlugin.java" target-dir="src/org/apache/cordova/dialog" />
	    </platform>
	</plugin>
	
src：

		新建目录，名为Android，放置 DialogPlugin.java
	
	
		public class DialogPlugin extends CordovaPlugin {
	
	    private static final String DIALOG_ACTION = "dialog:action";
	
	    @Override
	    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	        if (TextUtils.equals(action, DIALOG_ACTION)) {
	            new AlertDialog.Builder(cordova.getActivity())
	                    .setTitle("接受到的消息")
	                    .setMessage("通过可安装的插件接收到消息").show();
	            return true;
	        }
	        return false;
	    }
	}
	
www:

		放置 Dialog.js，这里需要注意的是，如果是编写可安装的插件时，js目录中不能出现 cordova.define 安装插件时会自动安装的
	
		var exec = require('cordova/exec');
		var dialogExport = {};
	
		dialogExport.showDialog = function(successCallback, errorCallback, options) {
		    exec(successCallback, errorCallback, "Dialog", "dialog:action", []);
		};
		module.exports = dialogExport;
	
如果没有问题，在 cordova 项目中`cordova plugin add file` 来安装插件

![_config.yml]({{ site.baseurl }}/img/cordova_plugins_success.jpg)


#### 携带参数

plugin.xml :

	<?xml version="1.0" encoding="UTF-8"?>
	
	<plugin xmlns="http://apache.org/cordova/ns/plugins/1.0"
	           id="cordova-plugin-dialog"
	      version="1.0.0">
	    <name>cordova-plugin-dialog</name>
	    <description>Cordova dialog Plugin</description>
	    <license>Apache 2.0</license>
	    <keywords>cordova,dialog</keywords>
	    <js-module src="www/Dialog.js" name="dialog">
	        <clobbers target="navigator.dialog" />
	    </js-module>
		// 新增对 DialogConstants.js 的支持
	    <js-module src="www/DialogConstants.js" name="Dialog">
	        <clobbers target="Dialog" />
	    </js-module>
	
	  
	    <platform name="android">
	        <config-file target="res/xml/config.xml" parent="/*">
	            <feature name="Dialog" >
	                <param name="android-package" value="org.apache.cordova.dialog.DialogPlugin"/>
	                <param name="onload" value="true" />
	            </feature>
	        </config-file>
	        <source-file src="src/android/DialogPlugin.java" target-dir="src/org/apache/cordova/dialog" />
	    </platform>
	</plugin>

www 目录下新增 `DialogConstants.js` 

		module.exports = {
		  DialogType:{
		    NIGHT: 666
		  }
		};
		
Dialog.js修改如下所示：

	var exec = require('cordova/exec');
	var argscheck = require('cordova/argscheck');
	var dialogExport = {};
	
	dialogExport.showDialog = function(successCallback, errorCallback, options) {
	
	    var getValue = argscheck.getValue;
	    var dialogType = getValue(options.dialogType, -1);
	    var args = [dialogType];
	    exec(successCallback, errorCallback, "Dialog", "dialog:action", args);
	};
	module.exports = dialogExport;
	
	
	
	//上面这个是简易版，在Cordova中传参数是可以生效的，但是作者在VUE中使用时这样调用时得不到传到的参数，略微修改了下，
	//按照下面的代码在VUE中穿参是可以拿到参数的，至于VUE和Cordova一起使用，往后会抽空更新Blog
	
	
	var argscheck = require('cordova/argscheck'),
	    exec = require('cordova/exec'),
	    Dialog = require('./Dialog');
	
	var dialogExport = {};
	
	for (var key in Dialog) {
	    dialogExport[key] = Dialog[key];
	}
	
	dialogExport.showDialog = function(successCallback, errorCallback, options) {
	    argscheck.checkArgs('fFO', 'Dialog.showDialog', arguments);
	    options = options || {};
	    var getValue = argscheck.getValue;
	    var dialogType = getValue(options.dialogType, -1);
	    var args = [dialogType];
	    exec(successCallback, errorCallback, "Dialog", "dialog:action", args);
	};
	
	module.exports = dialogExport;
	
然后执行`cordova plugin remove dialog` `cordova plugin add file`重新安装插件，

index.html调用方法修改：

		document.querySelector("#showDialog").addEventListener("touchend", function() {
			     navigator.dialog.showDialog(onSuccess, onFail, {

			         dialogType: Dialog.DialogType.NIGHT,

			      });
		});
		
至此一个完整的插件编写完成，当然本文没有对git库进行支持，如果有兴趣的可以自行添加对Git库的支持，因为 Cordova 支持 远程从Github安装

那么，我们下一章见！

