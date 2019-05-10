---
layout:     post
title:      "Cordova系列---第六章：Cordova打包"
subtitle:   "如何使用Cordova打正式版本的APK"
date:       2017-8-7
tags:
    - cordova
---

本文相关资料：

[Cordova 打包 Android release app 过程详解](https://segmentfault.com/a/1190000005177715)


## 前言

本文主要内容都摘录自 相关资料的连接，看到最后作者发现完全不如用 Android Studio 打包，


推荐使用 Android Studio 打包，不建议用命令行，相比较 Android Studio 打包流程更为清晰直观


`cordova build android` 会在 `android/build/outputs/apk/` 目录下生成 debug 包


## update

检查安装包是否是DeBug版，如果包含`Android DeBug`字样则为测试版

	jarsigner -verify -verbose -certs apkfile

## build.json

这里推荐一种更为方便的打包方式：

根目录下创建`build.json`，然后 使用`cordova build android --release` 即可

    {
      "android": {
        "release": {
          "keystore": "jks",
          "storePassword": "",
          "alias": "",
          "password": ""
        }
      }
    }
	
	
#### gradle配置打包(推荐)

gradle 配置好，执行`cordova build android`生成的就是签了名的正式包

`platforms/android`目录下建立 `release-signing.properties` 文件

	storeFile=jksFile
	storePassword=password
	keyAlias=alias
	keyPassword= password
	

`build.gradle` （android Module）下可以看见：


    if (!project.hasProperty('cdvReleaseSigningPropertiesFile')) {
        cdvReleaseSigningPropertiesFile = null
    }
    
 判断`cdvReleaseSigningPropertiesFile ` 有没有值，有值则置为Null
 
	 if (ext.cdvReleaseSigningPropertiesFile == null && file('release-signing.properties').exists()) {
	    ext.cdvReleaseSigningPropertiesFile = 'release-signing.properties'
	}
	
如果`ext.cdvReleaseSigningPropertiesFile ` == null , 并且 目录下含有`release-signing.properties`，则赋值

再往下看 ，可以看到 gradle 的 android 模块 做了判断

	
    if (cdvReleaseSigningPropertiesFile) {
        signingConfigs {
            release {
                keyAlias = ""
                keyPassword = "__unset"
                storeFile = null
                storePassword = "__unset"
            }
        }
        buildTypes {
            release {
                signingConfig signingConfigs.release
            }
            debug {
                signingConfig signingConfigs.release
            }
        }
        addSigningProps(cdvReleaseSigningPropertiesFile, signingConfigs.release)
    }
    //这是 DeBug 的判断，代码和 release 的差不多
    if (cdvDebugSigningPropertiesFile) {
        addSigningProps(cdvDebugSigningPropertiesFile, signingConfigs.debug)
    }

由代码可得知：

 如果`cdvReleaseSigningPropertiesFile ` == null ,则不会走 `signingConfigs ` 和 `buildTypes ` ,如果我们需要 在`buildTypes `中配置 BuildConfig 的变量，有两种办法：
 
 * 配置`release-signing.properties`
 * 注释掉 if 判断（不推荐）

 这里推荐第一种 配置 文件的方法
 
 我们可以将 `signingConfig signingConfigs.release` 在 `debug` 中也执行，这样`cordova build android` 的 apk包虽然名字是debug的，但是是签了名的apk,属于 `release` 包
 
#### cordova 配置打包
 
 在 cordova  的 目录下 新建一个`build.json`
 
	 {
	  "android": {
	    "release": {
	      "keystore": "jksFile",
	      "alias": "alias",
	      "storePassword": "password",
	      "password": "password"
	    }
	  }
	}
 
 
 然后执行：
 
 
 		cordova build --release
 


## Android APK 手动打包流程

Android app 的打包流程大致分为 build , sign , align 三部分。

build 是构建 APK 的过程，分为 debug 和 release 两种。release 是发布到应用商店的版本。

sign 是为 APK 签名。不管是哪一种 APK 都必须经过数字签名后才能安装到设备上，签名需要对应的证书（keystore），大部分情况下 APK 都采用的自签名证书，就是自己生成证书然后给应用签名。

align 是压缩和优化的步骤，优化后会减少 app 运行时的内存开销。

debug 版本的的打包过程一般由开发工具（比如 Android Studio）自动完成的。开发工具在构建时会自动生成证书然后签名，不需要我们操心。而 release 版本则需要开发者自己生成证书文件。Cordova 作为 hybrid app 的框架不像纯 Android 开发那么自动化，所以第一次打 release 包我们需要了解一下手动打包的过程。

## Build

首先，我们生成一个 `release` APK 。这点在 `cordova build `命令后加一个 `--release` 参数局可以。如果成功，你可以在 `android/build/outputs/apk/` 目录下看到一个 `android-release-unsigned.apk` 文件。

	cordova build android --release
	
## Sign

我们需要先生成一个数字签名文件（keystore）。这个文件只需要生成一次。以后每次 sign 都用它。

	keytool -genkey -v -keystore release-key.keystore -alias cordova-demo -keyalg RSA -keysize 2048 -validity 10000
	
上面的命令意思是，生成一个 `release-key.keystore` 的文件，别名（alias）为 cordova-demo 。
过程中会要求设置 keystore 的密码和 key 的密码。我们分别设置为 `testing` 和 `testing2`。这四个属性要记牢，下一步有用。

然后我们就可以用下面的命令对 APK 签名了：

	jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore release-key.keystore android-apk/android-release-unsigned.apk cordova-demo
	
这个命令中需要传入证书名 `release-key.keystore`，要签名的 APK `android-release-unsigned.apk`，和别名 `cordova-demo`。签名过程中需要先后输入` keystore` 和 `key` 的密码。命令运行完后，这个 APK 就已经改变了。注意这个过程没有生成新文件。

## Align

最后我们要用 zipalign 压缩和优化 APK ：

	zipalign -v 4 android-apk/android-release-unsigned.apk android-apk/cordova-demo.apk
	

这一步会生成最终的 APK，我们把它命名为 `cordova-demo.apk` 。它就是可以直接上传到应用商店的版本。

## 自动打包

一旦有了 `keystore` 文件，下次打包就可以很快了。你可以在 `cordova build` 中指定所有参数来快速打包。这会直接生成一个 `android-release.apk` 给你。

	cordova build android --release -- --keystore="release-key.keystore" --alias=cordova-demo --storePassword=testing --password=testing2
	
但每次输入命令行参数是很重复的，Cordova 允许我们建立一个 `build.json` 配置文件来简化操作。文件内容如下：

	{
	  "android": {
	    "release": {
	      "keystore": "release-key.keystore",
	      "alias": "cordova-demo",
	      "storePassword": "testing",
	      "password": "testing2"
	    }
	  }
	}
	
下次就可以直接用 `cordova build --release` 了。

为了安全性考虑，建议不要把密码放在在配置文件或者命令行中，而是手动输入。你可以把密码相关的配置去掉，下次 build 过程中会弹出一个 Java 小窗口，提示你输入密码。


