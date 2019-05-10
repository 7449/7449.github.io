---
layout:     post
title:      "Cordova系列---第七章：遇到的问题收集"
subtitle:   "使用过程中遇到的问题"
date:       2017-9-11
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
 ，通过测试发现，是因为`callbackContext`发送消息时是在子线程发送，如果在主线程发送则没有这个问题
 
 
## 连续调用插件

* 这里建议传递`callbackContext`,使用方便一些

因为插件是异步的，所以只要连续调用插件,最后只会回调最后一次调用插件的`callbackContext`,
`cordova` 为每个`callbackContext`设置了一个`CallbackId`,
`callbackContext.getCallbackId()`可以获取到,这里为了处理这种问题(例如网络插件一个页面多次调用)

使用一个`SimpleArrayMap`记录下每个`CallbackContext`,然后通过`callbackId`获取对应的`CallbackContext`发送消息

    private SimpleArrayMap<String, CallbackContext> simpleMap;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (simpleMap == null) {
            simpleMap = new SimpleArrayMap<>();
        }
        if (TextUtils.equals(action, NET_WORK_ACTION)) {
            EventBus eventBus = EventBus.getDefault();
            String type = args.getString(0);
            String parameter = args.getString(1);

            String callbackId = callbackContext.getCallbackId();
            if (simpleMap.containsKey(callbackId)) {
                simpleMap.remove(callbackId);
            }
            simpleMap.put(callbackId, callbackContext);
            return true;
        }
        callbackContext.error("action != requestNetWork");
        return false;
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onNetWorkEvent(SendJSEvent event) {
        if (simpleMap == null || !simpleMap.containsKey(event.callbackId) || simpleMap.get(event.callbackId) == null) {
            callbackContext.error("出现错误");
            return;
        }
        KLog.i(simpleMap.get(event.callbackId).getCallbackId());
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, event.message);
        pluginResult.setKeepCallback(true);
        simpleMap.get(event.callbackId).sendPluginResult(pluginResult);
    }
    
    
## 插件初始化

作者在使用插件的时候，发现如果第一次打开，走完整的流程，插件调用是没有任何问题的，但是如果在最近任务里面划掉app之后，再重新打开

这个时候App已经存在数据了，所以会直接进入页面，但是由于插件初始化需要一定的时间，如果进来就立即调用插件，会提示找不到这个插件

解决办法为：在插件初始化成功之后再初始化VUE相关的东西，插件初始化成功会触发`deviceready`，这个时候再初始化app，就没有这个问题了

示例代码：

    document.addEventListener('deviceready', () => {
        new Vue({
            el: '#app',
            router,
            store,
            template: '<App/>',
            data: {
              eventBus: new Vue(),
            },
            components: {App}
          })
    })
    
## EventBus传递消息

其实算不上bug，充其量是作者在使用过程中没注意到一个处理细节

EventBus注销不能放在`onStop`，不跳转`Activity`还好，跳转了会被注销掉，建议在`onDestroy`注销
    
## tips

 在 vue 中， `img`标签可直接加载手机本地图片,`file://`开头即可
