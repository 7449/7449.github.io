---
layout:     post
title:      "使用ViewBinding代替ButterKnife"
subtitle:   "View Binding(更新对ViewBind使用)"
date:       2020-3-20
tags:
    - ViewBind
    - android
---

[ViewBindingSample](https://www.github.com/7449/codeKK-Android)

简单封装:

[BaseViewBindActivity](https://github.com/7449/codeKK-Android/blob/master/app/src/main/java/com/codekk/ui/base/BaseViewBindActivity.kt)

[BaseViewBindFragment](https://github.com/7449/codeKK-Android/blob/master/app/src/main/java/com/codekk/ui/base/BaseViewBindFragment.kt)

## ViewBind 使用

0.不用再写布局了...

1.必须为`include`标签命名一个`id`,而`include`里面的`View`使用则是`viewBind.includeBind.id`,麻烦

2.`BK`是自动生成的文件,`ViewBind`也是,不过是由`IDE`深度集成支持的

3.和`DataBinding`比较,个人觉得区别不大...仅仅是`xml`文件不使用标签的形式而已,至少目前是这样的...

4.和`kotlin`一样,`IDE`深度集成的优点和`BK`相比就是就算不重新`Build`也支持`viewBind.id`,命名上也是驼峰式命名,如果不适应`kotlin`在`xml`中`id`命名驼峰式,可以用`ViewBind`

5.谈不上优劣,目前个人觉得`viewBind.id`有点麻烦,相比`kotlin`来说,`kotlin`创建一个`Map`在类中还是比较方面...

6.`kotlin`的问题是如果`xml`多了命名有重复,容易引入错误的布局类,而`ViewBind`不存在这种情况,因为它是为每个布局文件都生成了一个类


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







