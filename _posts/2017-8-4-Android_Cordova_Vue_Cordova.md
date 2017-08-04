---
layout:     post
title:      "Cordova系列---第五章：Cordova中加载Vue项目"
subtitle:   "本篇博客介绍了如何通过Vue去调用Cordova的插件并且在手机上"
date:       2017-8-4
author:     "y"
header-mask: 0.3
header-img: "img/cordova.png"
catalog: true
tags:
    - cordova
---

本文相关资料：

[vue](https://cn.vuejs.org/index.html)<br>
[vue-cordova](https://github.com/kartsims/vue-cordova)<br>


## 运行

创建好一个`Vue`项目，确定`npm run dev`能显示出正确的`HelloVue`页面,如果不会，可以通过[Android_Cordova_vue_Install](https://7449.github.io/2017/08/02/Android_Cordova_vue_Install/)或者去Vue官网查看如何创建Vue项目


一个初始的Vue项目，展示的是一个`Hello.vue`文件,在这里新加一个Button，用于调用一个Cordova的Dialog

#### 打包Vue项目

必须：

* Vue项目
* Cordova项目

两者可放在同级目录下，这个不是硬性要求，只是打包时需要路径

修改 Vue 项目`config/index.js`的`build`命令


* 修改前

		  build: {
		    env: require('./prod.env'),
		    index: path.resolve(__dirname, '../dist/index.html'),
		    assetsRoot: path.resolve(__dirname, '../dist'),
		    assetsSubDirectory: 'static',
		    assetsPublicPath: '/',
		    productionSourceMap: true,
		    productionGzip: false,
		    productionGzipExtensions: ['js', 'css'],
		    bundleAnalyzerReport: process.env.npm_config_report
		  },

* 修改后


		  build: {
		    env: require('./prod.env'),
		    index: path.resolve(__dirname, '../../corodva_project/www/index.html'),
		    assetsRoot: path.resolve(__dirname, '../../corodva_project/www'),
		    assetsSubDirectory: '',
		    assetsPublicPath: '',
		    productionSourceMap: true,
		    productionGzip: false,
		    productionGzipExtensions: ['js', 'css'],
		    bundleAnalyzerReport: process.env.npm_config_report
		  },


Vue 直接执行生成的资源文件在`dist/`目录下，想要运行在Cordova项目上还需要手动将里面的内容转移至`corodva/www目录下`，这里我们直接将两个项目放置在同个目录下，修改`build`下的 `index` `assetsRoot` `assetsSubDirectory ` `assetsPublicPath`,
然后直接执行`npm run build`，Vue会主动将打包好的文件放置在Cordova项目下的`www`目录下面

#### cordova

如果上述步骤没有问题，我们会在cordova项目的 `www` 目录下看见打包好的资源文件，但是`Cordova`运行需要加载`cordova.js`，Vue打包时是不会主动将`cordova.js`添加到`index.html`的，这里我们先手动将

``
<script type=text/javascript src=cordova.js></script>
``

添加到`index.html`文件里面

	<!DOCTYPE html>
	<html>
	<head>
	    <meta charset=utf-8>
	    <title>vue-project</title>
	    <link href=css/app.af3f90030a3694fd56c552c11bed9c9b.css rel=stylesheet>
	</head>
	<body>
	<div id=app></div>
	
	//这是Vue压缩的js文件
	<script type=text/javascript src=js/manifest.9be81447179f9dfa8d85.js></script>
	<script type=text/javascript src=js/vendor.ae75c6b5bea60f5d8cec.js></script>
	<script type=text/javascript src=js/app.4ce96b32c4d425125662.js></script>
	
	//手动添加 cordova.js
	<script type=text/javascript src=cordova.js></script>
	</body>
	</html>


然后执行`cordova run android`，如果没问题，我们会在手机或者虚拟机上看到如下图所示的页面

![_config.yml]({{ site.baseurl }}/img/cordova_vue_sample.jpg)



## vue-cordova



插件[vue-cordova](https://github.com/kartsims/vue-cordova)


#### 在线安装

`npm install --save vue-cordova`

安装成功之后在`main.js` 导包

	import Vue from 'vue'  //这句可忽略，因为Vue初始化时已经自动导入了这个
	import VueCordova from 'vue-cordova'
	Vue.use(VueCordova)
	
#### 添加cordova.js

main.js

* if 只是加了判断，可根据实际情况加判断


		if (window.location.protocol === 'file:' || window.location.port === '3000') {
		  var cordovaScript = document.createElement('script')
		  cordovaScript.setAttribute('type', 'text/javascript')
		  cordovaScript.setAttribute('src', 'cordova.js')
		  document.body.appendChild(cordovaScript)
		}


#### 调试


chrome打开

	chrome://inspect/#devices
	
选择你的设备，`inspect`即可看见控制台



#### 运行

随便做个点击事件输入`Vue.cordova`，注意导包` import Vue from 'vue'`

例如：

	<script>
	  // eslint-disable-next-line no-unused-vars
	  import Vue from 'vue'
	
	  export default {
	    name: 'hello',
	    data () {
	      return {
	        msg: 'Welcome to Your Vue.js App'
	      }
	    },
	    methods: {
	// eslint-disable-next-line space-before-function-paren
	      showCordova () {
	        console.log(Vue.cordova)
	      }
	    }
	  }
	</script>

如果不出问题控制台会输出如下日志<br>
并且会发现`index.html`已经有了`<script type=text/javascript src=cordova.js></script>`




![_config.yml]({{ site.baseurl }}/img/cordova_vue_inspect_console.png)




这时已经可以拿到`cordova`对象了，就可以操作cordova 的插件，但是需要注意的是，操作的插件必须要 cordova 项目已经安装了，例如

	Vue.cordova.camera.getPicture((imageURI) => {
	  window.alert('Photo URI : ' + imageURI)
	}, (message) => {
	  window.alert('FAILED : ' + message)
	}, {
	  quality: 50,
	  destinationType: Vue.cordova.camera.DestinationType.FILE_URI
	})


如果调用 Camera，你的 corova 项目必须安装 camera 插件 ，否则直接调用会出现如下情况：

`console.log(Vue.cordova.camera)`


![_config.yml]({{ site.baseurl }}/img/cordova_vue_inspect_undefined.png)




#### 本地安装

* 为什么要本地安装？
* 现有的插件不够，如果要调用自己开发的插件，在线的不能用，只能将项目放在Vue项目中根据实际情况自己添加插件


如果已安装了 `vue-cordova`,控制台执行`npm uninstall vue-cordova`删除掉

在 github上 clone 下 `vue-cordova` 项目


	git clone https://github.com/kartsims/vue-cordova.git
	


* 在 Vue 项目的`src`目录下新建一个`cordova`目录
* 将下载的 vue-cordova `src`目录下的`index.js` copy 到Vue项目新建的 `cordova`目录下面，命名为`cordova-plugin-index.js`,不要使用`index.js`，否则会出现命名冲突
* 在`cordova`目录下新建一个`plugins`目录，将插件的 js  文件都放进去
* `main.js`的导包改成下面的样子：

		import VueCordova from './cordova/cordova-plugin-index.js'
		Vue.use(VueCordova)


![_config.yml]({{ site.baseurl }}/img/cordova_vue_sample_project.png)




然后运行 `npm run build` 打包到手机上输出`Vue.cordova`

![_config.yml]({{ site.baseurl }}/img/cordova_vue_inspect_console.png)

如果是 `undefined`请检查步骤是否缺漏





#### 添加本地插件

以 Dialog 为示例


在 [Android_Cordova_plugin](https://7449.github.io/2017/07/31/Android_Cordova_plugin/)中 我们已经开发了一个Dialog插件用于测试，这里就直接用下.


* 在`src/cordova/plugins/`目录下新建`cordova-plugin-dialog.js`，完全按照`camera`插件仿造的一个 js 文件


		exports.install = function (Vue, options, cb) {
		  document.addEventListener('deviceready', () => {
		    if (typeof navigator.dialog === 'undefined') {
		      return cb(false)
		    }
		
		    // pass through the camera object
		    Vue.cordova.dialog = navigator.dialog
		
		    return cb(true)
		  }, false)
		}


* 将其添加到`cordova-plugin-index.js`

		/* eslint-disable padded-blocks */
		// list here all supported plugins
		const pluginsList = [
		  'cordova-plugin-camera',
		  
		  //主要是这句，将 dialog 插件添加到 vue-cordova 中
		  'cordova-plugin-dialog'
		]
		
		exports.install = (Vue, options) => {
		
		// eslint-disable-next-line no-irregular-whitespace
		  Vue.cordova = Vue.cordova || {
		    deviceready: false,
		    plugins: []
		  }
		
		  Vue.cordova.on = (eventName, cb) => {
		    document.addEventListener(eventName, cb, false)
		  }
		
		  // let Vue know that deviceready has been triggered
		  document.addEventListener('deviceready', () => {
		    Vue.cordova.deviceready = true
		  }, false)
		
		  // load supported plugins
		  pluginsList.forEach(pluginName => {
		    let plugin = require('./plugins/' + pluginName)
		    plugin.install(Vue, options, pluginLoaded => {
		      if (pluginLoaded) {
		        Vue.cordova.plugins.push(pluginName)
		      }
		      if (Vue.config.debug) {
		        console.log('[VueCordova]', pluginName, '→', pluginLoaded ? 'loaded' : 'not loaded')
		      }
		    })
		  })
		
		}



* 然后在 vue 中调用 

        Vue.cordova.dialog.showDialog((data) => {
          window.alert('Photo URI : ' + data)
        }, (message) => {
          window.alert('FAILED : ' + message)
        }, {
        })
        



![_config.yml]({{ site.baseurl }}/img/cordova_vue_plugin_dialog.png)






## 疑惑

按照这个过程，作者打的包，一些图片不知道为什么无法加载，手动将图片在 cordova 项目 `www/css` 目录下也放置一份 图片才能完全显示，猜测是路径不对,尝试了网上很多方法，但是始终没找到解决办法


至此 一个简单的 VUE-CORDOVA 交互就已经完成了，那么 下章见~






































