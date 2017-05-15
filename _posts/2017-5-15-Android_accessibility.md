---
layout:     post
title:      "Android_Accessibility使用"
subtitle:   "Accessibility实现自动安装APK"
date:       2017-05-15
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
---

## sample

[https://github.com/7449/AndroidDevelop/tree/master/accessibilityService](https://github.com/7449/AndroidDevelop/tree/master/accessibilityService)


## Accessibility

> Android辅助功能，可以完成一些特殊的功能

## 自动安装APK

首先继承`AccessibilityService`自己实现一个Service，这里是为了实现自动安装APK，就叫`InstallService`,并且在清单文件`manifests`里面添加相应的权限以及注册

    	<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
	

        <service
            android:name=".InstallService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/install_service" />
        </service>

这里有个`@xml/install_service`只要是为了配置一些信息

配置可以选择java代码配置或者xml配置，sample选择xml配置

#### xml配置 

> 4.0以下不支持

 - accessibilityEventTypes: 响应事件，包括了单击，长按，滑动等状态
 - accessibilityFeedbackType：反馈响应
 - canRetrieveWindowContent：是否允许程序读取window中的节点和内容
 - notificationTimeout:响应时间
 - description:简单解释，会在残疾人打开界面出现，就是简介
 - packageNames：想要监控的应用包名，我这里是安装界面那么包名就是`com.android.packageinstaller`

	<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
	    android:accessibilityEventTypes="typeWindowStateChanged"
	    android:accessibilityFeedbackType="feedbackVisual"
	    android:accessibilityFlags="flagDefault"
	    android:canRetrieveWindowContent="true"
	    android:description="@string/description_install"
	    android:notificationTimeout="100"
	    android:packageNames="@string/install_package_name" />

#### java代码配置

实现 `onServiceConnected` 方法，在里面注册

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = ;
        info.notificationTimeout = ;
        info.packageNames = ;
        setServiceInfo(info);


>相关方法调用时机：

 - onServiceConnected() ： 可选。系统会在成功连接上你的服务的时候调用这个方法，在这个方法里你可以做一下初始化工作，例如设备的声音震动管理，也可以调用setServiceInfo()进行配置工作。
  
 - onAccessibilityEvent() - 必须。通过这个函数可以接收系统发送来的AccessibilityEvent，接收来的AccessibilityEvent是经过过滤的，过滤是在配置工作时设置的。
  
 - onInterrupt() - 必须。这个在系统想要中断AccessibilityService返给的响应时会调用。在整个生命周期里会被调用多次。
  
 - onUnbind() - 可选。在系统将要关闭这个AccessibilityService会被调用。在这个方法中进行一些释放资源的工作。

## InstallService

在`onAccessibilityEvent`方法里面实现相应的功能，先判断packageName然后再遍历如果出现"确定", "安装", "下一步", "完成"，用代码实现点击，这里只实现了原生ROM，国产ROM可能不适应，要按需修改

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() == null) {
            log("return  source is null");
            return;
        }
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getPackageName().equals(getString(R.string.install_package_name))) {
            installation(event);
        }

    }

    private boolean installation(AccessibilityEvent event) {
        List<AccessibilityNodeInfo> nodeInfoList;
        String[] labels = new String[]{"确定", "安装", "下一步", "完成"};
        for (String label : labels) {
            nodeInfoList = event.getSource().findAccessibilityNodeInfosByText(label);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                if (click(nodeInfoList)) {
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean click(List<AccessibilityNodeInfo> nodeInfoList) {
        for (AccessibilityNodeInfo node : nodeInfoList) {
            if (node.isClickable() && node.isEnabled()) {
                return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
        return false;
    }
