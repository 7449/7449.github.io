---
layout:     post
title:      "使用ViewBinding代替ButterKnife"
subtitle:   "View Binding"
date:       2019-10-30
tags:
    - ViewBinding
    - android
---

`ViewBinding`是`google`新支持的一项用以取代`ButterKnife`的一种工具

目前还没到正式版，所以本文是对官网的`copy`,有能力的可直接看官网，下面给出了链接

`ButterKnife`，`DataBinding`，`ViewBinding`接二连三的出来，`Kotlin`又有`kotlin-android-extensions`

现在`Android`开发越来越容易，各种开源库层出不穷，只需要选择适合自己的即可，例如有的项目还在使用很老版本的`ButterKnife`

官网：[view-binding](https://developer.android.com/topic/libraries/view-binding)

目前稳定版`Studio`还不支持，只支持 `Android Studio 3.6 Canary 11+`

具体使用方法：

第一步开启

`ViewBinding`是根据`Library`开启的需要添加

    android {
        ...
        viewBinding {
            enabled = true
        }
    }
    
看上去和开启`DataBinding`一样

如果要忽略某一个布局，需要在该布局的根节点添加

    tools:viewBindingIgnore="true"
    
例如

    <LinearLayout
            ...
            tools:viewBindingIgnore="true" >
        ...
    </LinearLayout>
    
第二步使用

布局如下：

    <LinearLayout ... >
        <TextView android:id="@+id/name" />
        <ImageView android:cropToPadding="true" />
        <Button android:id="@+id/button"
            android:background="@drawable/rounded_button" />
    </LinearLayout>

代码：

    private lateinit var binding: ResultProfileBinding
    
    @Override
    fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        binding = ResultProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    binding.name.text = viewModel.name
    binding.button.setOnClickListener { viewModel.userClicked() }







