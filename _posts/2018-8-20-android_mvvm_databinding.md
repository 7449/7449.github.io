---
layout:     post
title:      "Android中的MVVM和databinding"
subtitle:   "MVVM和databinding在android中的简单使用"
date:       2018-8-20
tags:
    - android
    - mvvm
    - databinding
---


## 相关代码

[Kotlin-Sample-App](https://github.com/7449/Kotlin-Sample-App)

## 相关资料

[Databinding](https://developer.android.com/topic/libraries/data-binding/)

## mvp

[Android中MVP的使用](https://7449.github.io/2016/10/08/Android_MVP/)

## mvvm

* View: 对应于`Activity`和`XML`，负责`View`的绘制以及与用户交互。
* Model: 实体模型。
* ViewModel: 负责完成`View`与`Model`间的交互，负责业务逻辑。

当然这里只是简单说明下,具体代码建议参考`google`的[mvvm-databinding](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-databinding)

以前也使用过`databinding`但是当时发现使用过程中还是有一些比较棘手的问题,例如出现问题很难排查原因,只支持单向绑定，就没有继续使用下去,当时只是简单的写了一个`Demo`,
截至目前,`databinding`和`mvvm`在`android`中也日渐成熟,越来越多的应用采用了`databinding`,`mvvm`,`rxjava`,`kotlin`...等等具有优势的框架或者应用框架

## 个人对`mvvm`的一些理解

* 为什么在`mvc`->`mvp`的情况下，现在又出现了`mvvm`?

`mvc`以及`mvp`的对比这里就不再赘述,简单说下`mvp`的缺点
1. 不适合小项目,这里的小项目指的是就几个页面的，没有复杂逻辑的项目，如果采用`mvp`只会增加一大堆接口,给后来者会带来一定的阅读难度
2. 随着业务逻辑的递增,如果项目不做减法,`presenter`只会越来越复杂,这个时候因为`presenter`持有`view`的引用，改动起来很麻烦
3. 需要考虑组件的生命周期,`presenter`越来越臃肿那么`mvp`想解决的问题就在`presenter`中又出现了

* `mvvm` 相比 `mvp` 有什么优点?

1. 平时开发中需要更新`UI`时,需要先获取`UI`控件的引用，然后再更新`UI`,在`MVVM`中,数据变化后会自动更新`UI`,`UI`的改变也能自动反馈到数据层，数据成为主导因素。
这样`VM`层在业务逻辑处理中只要关心数据，不需要直接和`UI`打交道
2. 低耦合,数据和业务逻辑在`ViewModel`中，`ViewModel`只需要关注数据和业务逻辑，不需要和控件打交道。
控件想怎么处理数据都由控件决定，`ViewModel`不持有控件的引用,即便是控件层变了，`ViewModel`也几乎不需要更改任何代码
3. 在`MVVM`中，数据发生变化后，可以直接在线程安全的情况下修改`ViewModel`的数据，不需要考虑要切到主线程更新`UI`
4. `ViewModel`带来的低耦合非常适用于团队合作

## databingding

`databinding`是`google`推出的一款扩展`xml`的框架,直接在`gradle`中声明即可

    android {
        dataBinding {
            enabled = true
        }
        ...
    }
    
   
举个简单的例子:

    <?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">
    
    
        <data>
    
            <variable
                name="layoutManager"
                type="android.support.v7.widget.RecyclerView.LayoutManager" />
        </data>
    
        <com.common.widget.LoadMoreRecyclerView
            android:id="@+id/codekk_recyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layoutManager="@{layoutManager}" />
    
    </layout>

然后重新`build`一下项目,工具会自动生成相对应的类文件,命名默认为布局文件名 + Binding ，驼峰式命名

例如布局名是`activity_main`,则生成的默认文件名为`ActivityMainBinding`

如上面示例,如果为`recyclerView`设置`LayoutManager`在代码中直接`binding.layoutManager = LinearLayoutManager(this)`即可

但是在使用中还是发现了一个问题,在`item`中设置`View`属性时，还是碰到了延迟的问题，
在[item_codekk_op_list](https://github.com/7449/Kotlin-Sample-App/blob/master/Codekk/src/main/res/layout/item_codekk_op_list.xml)布局中导入了几个类和一个实体类，
在`layout`中设置`autoLink`，碰到了`AppCompatTextView`第一页的链接并没有变成`web`链接
滑动复用之后才会变化,但是在`addData`之后延迟2秒再刷新一下页面，效果就会起效,所以暂时没有找到其他解决办法,这种属性只能在代码中设置

## 结合使用

数据可以使用`LiveData`驱动,`ViewModel`继承`android.arch.lifecycle`下的`ViewModel`即可,这里提一下`google`在里面也实现了一个默认的`AndroidViewModel`

    public class AndroidViewModel extends ViewModel {
        @SuppressLint("StaticFieldLeak")
        private Application mApplication;
    
        public AndroidViewModel(@NonNull Application application) {
            mApplication = application;
        }
    
        /**
         * Return the application.
         */
        @SuppressWarnings("TypeParameterUnusedInFormals")
        @NonNull
        public <T extends Application> T getApplication() {
            //noinspection unchecked
            return (T) mApplication;
        }
    }
    
这个可以获取一个`Application`对象,个人暂时觉得没有多大用...,如果需要在`ViewModel`中获取`Application`对象则可以用这个,
不过推荐使用时自己实现一个`ViewModel`，然后结合项目的实际应用实现不同的`ViewModel`

    abstract class NetWorkViewModel<T> : ViewModel(), RxNetWorkListener<T> {
    
        val viewModelData: MediatorLiveData<BaseEntity<T>> = MediatorLiveData()
    
        override fun onNetWorkError(e: Throwable) {
        }
    
        override fun onNetWorkComplete() {
        }
    }

在`activity`或者`fragment`则获取`ViewModel`对象，然后去获取数据或者实现其他功能

    ViewModelProviders.of(this).get(UserViewModel.class)
    
按照`googlesample`中的写法则是使用`ViewModel`获取`LiveData`然后注册一个`observe`,这样在`ViewModel`中则直接使用`liveData.setValue()`或者`liveData.postValue()`即可.
其中的`postValue`适用于子线程中操作数据

如果这里使用`ObservableArrayList`则数据更方便,具体操作可以参考[Kotlin-Sample-App](https://github.com/7449/Kotlin-Sample-App)

## 总结

总的来讲,开发确实越来越方便,不像以前需要考虑到各个方面,这里直接使用即可

但是吐槽一句题外话,使用`kotlin`的时候电脑卡的要死,`java`项目则没有这个问题...

