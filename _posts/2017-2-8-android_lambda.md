---
layout:     post
title:      "Android_Lambda表达式"
subtitle:   "Lambda表达式使用"
date:       2017-2-08
tags:
    - android
    - java
---


## update

`android studio 3.x` 之后已经支持 `java8`


## 加入Lambda依赖

如果想在android中使用Lambda表达式，就需要依赖[gradle-retrolambda](https://github.com/evant/gradle-retrolambda)并且项目要处于jdk8的环境下<br>
在 `dependencies `里面添加 

	classpath 'me.tatarka:gradle-retrolambda:version'
	这篇blog的时候version = 3.5.0

在`app/gradle`里面添加

	apply plugin: 'me.tatarka.retrolambda'

	android {
	    
	    compileOptions {
	        sourceCompatibility JavaVersion.VERSION_1_8
	        targetCompatibility JavaVersion.VERSION_1_8
	    }
	}

然后sync project,项目就支持 Lambda 了

## 简单测试


用一个Butotn点击以及长按事件简单测试<br>


以前的点击事件简单的有以下两种：

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show()
            }
        });

	
	    button.setOnClickListener(onClickListener);
	    View.OnClickListener onClickListener = new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
				Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show()
	        }
	    };

改用Lambda就成了 

        button.setOnClickListener(v -> Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show());

	    button.setOnClickListener(onClickListener);
	    View.OnClickListener onClickListener = v -> Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show();


这里的 `v` 就是当前View对象，你可以写成 `a-z` 任意一个字母；<br>

如果是 `OnLongClickListener` 这种需要返回值的方法，则可以写成如下这种：


        buttonOnLongClick.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), "onLong", Toast.LENGTH_SHORT).show();
            return false;
        });


		buttonOnLongClick.setOnLongClickListener(onLongClickListener);
	    View.OnLongClickListener onLongClickListener = v -> {
	        Toast.makeText(v.getContext(), "onLong", Toast.LENGTH_SHORT).show();
	        return false;
	    };


对于那种无参方法，例如：

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
			Log.i(getClass().getSimpleName(), "runOnUiThread")；
            }
        });


改用 `Lambda` 则可以用 `()`代替：


        this.runOnUiThread(() -> Log.i(getClass().getSimpleName(), "runOnUiThread"));



而且如果你的项目支持Lambda的话，直接new OnClickListener() ，Android Studio或者IDEA会提示这里可以使用Lambda：

	Anonymous new View.OnClickListener() can be replaced with lambda less... (Ctrl+F1) 
	This inspection reports all anonymous classes which can be replaced with lambda expressions
	Lambda syntax is not supported under Java 1.7 or earlier JVMs.


需要注意的是  Lambda 表达式中引用的局部变量必须是 final 或既成事实上的 final 变量<br>





## 相关书籍

[Java8 函数式编程 下载地址](http://download.csdn.net/detail/oaitan/9749938)
