---
layout:     post
title:      "Android混淆"
subtitle:   "总结Android混淆相关的一些知识"
date:       2017-3-19
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - 混淆
---

## 前言

android使用的ProGuard，可以起到压缩，混淆，预检，优化的作用，有的公司直接选择加固而不是用混淆来防止app被反编译的，这里只是讲解一下关于混淆的一些处理，对于加固不作说明，现在加固也是很正常的处理方式，例如360市场不加固就不让上架，不过如果不想加固也不用担心，如果app活跃量过大360会自己抓包....


## 提示

这里的混淆规则只是我项目中用到的，不一定适应你的项目，请酌情删减或者增加相应的混淆规则

## 一些指令的含义

`-optimizationpasses 5` : 代码混淆的压缩比例，值在0-7之间

`-dontusemixedcaseclassnames` : 混淆后类名都为小写

`-dontskipnonpubliclibraryclasses` : 指定不去忽略非公共的库的类

`-dontskipnonpubliclibraryclassmembers` : 指定不去忽略非公共的库的类的成员

`-dontpreverify` : 不做预校验的操作

`-verbose`
`-printmapping proguardMapping.txt` : 生成原类名和混淆后的类名的映射文件

`-optimizations !code/simplification/cast,!field/*,!class/merging/*` : 指定混淆采用的算法

`-keepattributes *Annotation*,InnerClasses` : 不混淆Annotation

`-keepattributes Signature` : 不混淆泛型

`-keepattributes SourceFile,LineNumberTable` : 不抛出异常时保留代码行号

`-keep class XXXX` : 类名不混淆，而类中的成员名不保证

`-keepclasseswithmembers class XXXX` : 保留类名和成员名
,也可以是类中特定方法



## proguard-rules.pro

混淆主要就两部分，一部分基本不用动，另一部分要根据项目的实际情况自己修改

#### 基本不用动

##---------------------------------基本指令区----------------------------------

	-optimizationpasses 5
	
	-dontskipnonpubliclibraryclassmembers
	
	-optimizations !code/simplification/cast,!field/*,!class/merging/*
	
	-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature
	
	-dontpreverify
	
	-dontshrink
	
	-dontoptimize
	
	-ignorewarnings



##--------------------------注解--------------------

	-keep class * extends java.lang.annotation.Annotation { *; }
	
	-keep interface * extends java.lang.annotation.Annotation { *; }


##---------------------------------默认---------------------------------

	-keep public class * extends android.app.Activity
	
	-keep public class * extends android.app.Application
	
	-keep public class * extends android.app.Service
	
	-keep public class * extends android.content.BroadcastReceiver
	
	-keep public class * extends android.app.Fragment
	
	-keep public class * extends android.support.v4.app.Fragment
	
	-keep public class * extends android.content.ContentProvider
	
	-keep public class * extends android.app.backup.BackupAgentHelper
	
	-keep public class * extends android.preference.Preference
	
	-keep public class * extends android.view.View
	
	-keep public class com.android.vending.licensing.ILicensingService
	
	-keep class android.support.** {*;}
	
	-keep class com.google.inject.** { *; }
	
	-keep class javax.inject.** { *; }
	
	-keep class javax.annotation.** { *; }
	
	-keep class roboguice.** { *; }
	

	-keep public class * extends android.view.View{
	    *** get*();
	    void set*(***);
	    public <init>(android.content.Context);
	    public <init>(android.content.Context, android.util.AttributeSet);
	    public <init>(android.content.Context, android.util.AttributeSet, int);
	}
	
	-keep class **.R$* {
	*;
	}
	
	-keepclassmembers class * {
	    void *(**On*Event);
	}
	
	-keepclassmembers class * extends android.app.Activity{
	    public void *(android.view.View);
	}
	
	-keepclassmembers enum * {
	    public static **[] values();
	    public static ** valueOf(java.lang.String);
	}

#保持 Parcelable 不被混淆

	-keep class * implements android.os.Parcelable {
	  public static final android.os.Parcelable$Creator *;
	}


#保持 Serializable 不被混淆并且enum 类也不被混淆

	-keepclassmembers class * implements java.io.Serializable {
	    static final long serialVersionUID;
	    private static final java.io.ObjectStreamField[] serialPersistentFields;
	    !static !transient <fields>;
	    !private <fields>;
	    !private <methods>;
	    private void writeObject(java.io.ObjectOutputStream);
	    private void readObject(java.io.ObjectInputStream);
	    java.lang.Object writeReplace();
	    java.lang.Object readResolve();
	    public void set*(***);
	    public *** get*();
	}
	
##保持自定义控件类不被混淆

	-keepclasseswithmembers class * {
	    public <init>(android.content.Context);
	}
	
	-keepclasseswithmembernames class * {
	    public <init>(android.content.Context, android.util.AttributeSet);
	}
	
	-keepclasseswithmembernames class * {
	    public <init>(android.content.Context, android.util.AttributeSet, int);
	}

##----------------------------webview-----------------------------------

	-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
	  public *;
	}
	
	-keepclassmembers class * extends android.webkit.WebViewClient {
	    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
	    public boolean *(android.webkit.WebView, java.lang.String);
	}
	
	-keepclassmembers class * extends android.webkit.WebViewClient {
	    public void *(android.webkit.WebView, jav.lang.String);
	}

##--------------------删掉所有log------------------------------

	-assumenosideeffects class android.util.Log {
	
	    public static *** d(...);
	    
	    public static *** w(...);
	    
	    public static *** v(...);
	    
	    public static *** i(...);
	    
	}
	
	
	
	
#### 由项目决定改动的地方

>实体类

	-keep class 你的实体类所在的包.** { *; }
	
>compile包

这里可以直接去官网找他们的混淆规则，如果没有自己google或者百度也能找到相对应的规则

例如Glide

	-keep public class * implements com.bumptech.glide.module.GlideModule
	-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
	  **[] $VALUES;
	  public *;
	}
	
>jar包（不混淆，不报warn）

如果报错，可以注释掉	-libraryjars 名字.jar

	-libraryjars 名字.jar
	-dontwarn 包名.**
	-keep class  包名.** { *;}
	
>反射 或者 Js


	-keep class 你的类所在的包.** { *; }

>内部类


	-keepclasseswithmembers class 你的类所在的包.父类$子类 { <methods>; }