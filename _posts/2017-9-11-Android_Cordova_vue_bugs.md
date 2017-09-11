---
layout:     post
title:      "Cordova系列---第七章：遇到的问题收集"
subtitle:   "使用过程中遇到的问题"
date:       2017-9-11
author:     "y"
header-mask: 0.3
header-img: "img/cordova.png"
catalog: true
tags:
    - cordova
---


## 多次调用插件

使用过程中发现第一次调用插件正确返回数据之后，再次调用却不会回调`successCallback`,通过查阅资料得知：

    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, message);
    // keepCallback默认为true，如果需要多次调用插件，则需要手动设置keepcallback为true
    pluginResult.setKeepCallback(true);
    callbackContext.sendPluginResult(pluginResult);


源码为：

    public void sendPluginResult(PluginResult pluginResult) {
        synchronized (this) {
            if (finished) {
                LOG.w(LOG_TAG, "Attempted to send a second callback for ID: " + callbackId + "\nResult was: " + pluginResult.getMessage());
                return;
            } else {
                finished = !pluginResult.getKeepCallback();
            }
        }
        webView.sendPluginResult(pluginResult, callbackId);
    }


这里也不难看出，如果`finished`为 true 的时候，则直接 `return`，传递数据的时候，`finished` 默认为`false`
,`keepCallback`默认也为`false`,`sendPluginResult`的时候会为`finished`赋值。


## vue this指向

        console.log(this)
        Vue.cordova.network.start((data) => {
        console.log(this)
        }, (error) => {
        console.info(error);
        }, {
         options
        });
        
 使用插件时，发现只有第一次进来的时候控制台输出的`this`相同，往后除非退出app，否则拿到的`this`一直是第一次进来的`this`
 