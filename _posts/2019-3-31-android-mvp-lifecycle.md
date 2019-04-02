---
layout:     post
title:      "老项目MVP如何非侵入式自动监控生命周期(针对RxJava)"
subtitle:   "mvp老项目利用android.arch.lifecycle非侵入式更新为自动监控生命周期"
date:       2019-3-31
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - mvp
---

> 这里不讲`MVP`和`MVVM`的优缺点，只讲一下如何将老项目的`MVP`非侵入式简单替换到可监控生命周期的方法

相关代码示例地址：[MVPLifecycle](https://github.com/android-develop-team/AndroidDevelop/blob/develop/MVPLifecycle)

对于如何写`MVP`项目可参考以前的老博客

[Android中MVP的使用](https://7449.github.io/2016/10/08/Android_MVP/)

本篇文章是讲怎么在`RxJava`+`retrofit2`的网络请求下自动监控生命周期取消网络请求而不是每次都要手动在`onDestroy`中取消网络请求

如果新项目建议使用`LiveData`+`ViewModel`,这样本身就具有了生命周期监控,而无需自行处理

本篇文章是在`假设项目已经更迭了好几代,不好替换框架,或者不想更换框架,想在原有的基础上无痛实现自动感知这一套了`这一前提下进行描述

## 老项目MVP

举一个简单的示例

### Model

    class MainActivity : AppCompatActivity(), IView {
        private lateinit var presenter: IPresenter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            presenter = PresenterImpl(this)
        }
        override fun showLoading() {}
        override fun hideLoading() {}
        override fun onDestroy() {
            presenter.onDestroy()
            super.onDestroy()
        }
    }

### View

    interface View {
        fun showLoading()
        fun hideLoading()
    }

### Presenter

#### 接口层

    interface IPresenter {
        fun onDestroy()
    }
    
#### 实现层

    class PresenterImpl(private var view: IView?) : IPresenter {
        override fun onDestroy() {
            //这里可以做一些释放资源的操作,例如取消网络请求
            if (view != null) {
                view = null
            }
        }
    }

这只是一个非常简单的`MVP`示例,如上所示,在`Activity`的`onCreate`中初始化`P`层,然后在`onDestroy`中销毁`P`层引用,并且切断`View`层的引用,
非常老套的实现方法,如果每次都这么写当然很麻烦,一般都会自己在项目中封装一层,如下所示

## 封装

#### BaseView

一般页面都会有进度展示,然后再写页面相关的逻辑,例如弹一个提示,只需要继承`BaseView`即可

    interface BaseIView {
        fun showLoading()
        fun hideLoading()
    }

#### BasePresenter

还是保持原来的不变,因为`P`层持有一般只需要监控`onDestroy`方法即可

    interface BaseIPresenter {
        fun onDestroy()
    }

#### BasePresenterImpl

这里会利用到`java`的泛型进行扩展

    open class BasePresenterImpl<V : BaseIView>(protected var mView: V?) : BaseIPresenter {
        override fun onDestroy() {
            if (mView != null) {
                mView = null
            }
        }
    }
    
#### BaseModel

一般`M`层都是`Activity`或者`Fragment`,这里只展示`Activity`,`Fragment`同理
实现一个`BaseActivity`,子类只需要关系自己的实现即可

    abstract class BaseActivity<P : BaseIPresenter> : AppCompatActivity() {
        protected lateinit var presenter: P
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            presenter = initPresenter()
        }
        protected abstract fun initPresenter(): P
        override fun onDestroy() {
            super.onDestroy()
            presenter.onDestroy()
        }
    }

简单封装完毕,那么看看如何使用

#### MainView

这里展示一个`Toast`

    interface MainView : BaseIView {
         fun showToast()
    }

#### MainPresenter

一个网络请求的`P`层接口

    interface MainPresenter : BaseIPresenter {
        fun netWork()
    }
    
#### MainPresenterImpl

如下所示,只需继承之前封装好的`BasePresenterImpl`即可,在网络请求成功之后进行各种操作,也可以重写`onDestroy`取消网络请求

    class MainPresenterImpl(view: MainView) : BasePresenterImpl<MainView>(view), MainPresenter {
        override fun netWork() {
            mView?.showToast()
        }
    }
    
#### MainModel

同上所示,子类只需要关心自己的实现,不用关心于`P`和`V`的引用问题,基类都已经处理了

    class MainActivity : BaseActivity<MainPresenter>(), MainView {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            presenter.netWork()
        }
        override fun initPresenter(): MainPresenter = MainPresenterImpl(this)
        override fun showLoading() {}
        override fun hideLoading() {}
        override fun showToast() {}
    }
    
## 网络请求

这里使用简单封装的[RxNetWork](https://github.com/7449/RxNetWork)

如下所示,框架需要三个参数,分别是`tag`,`observable`,`listener`

`tag`是为了取消网络请求,在老项目中每次都要手动在`onDestroy`中取消网络请求,

如果忘记了,那么很容易就会出现页面销毁了,但是`P`层网络请求成功了,这时走了成功回调,就会造成空指针异常,

当然了,如果在回调中判断`view`是否为`Null`,如果为`Null`则直接`return`掉也可以,

但是既然页面已经销毁了,为了节省资源就没必要在请求网络操作了

    class MainPresenterImpl(view: MainView) : BasePresenterImpl<MainView>(view), MainPresenter, RxNetWorkListener<Any> {
        override fun netWork() {
            RxNetWork
                .instance
                .getApi(tag, observable, this)
            mView?.showToast()
        }
        override fun onNetWorkComplete() {}
        override fun onNetWorkError(e: Throwable) {}
        override fun onNetWorkStart() {}
        override fun onNetWorkSuccess(data: Any) {}
        override fun onDestroy() {
            RxNetWork.instance.cancel(tag)
            super.onDestroy()
        }
    }

## 那么问题来了

这就出现了上面说的局面,忘了手动在`onDestroy`中取消网络请求怎么办,最好的办法就是`P`层能自动感知到页面的生命周期变化,然后自动取消掉这次请求

这里假设项目已经更迭了好几代,不好替换框架,或者不想更换框架,想在原有的基础上无痛实现自动感知这一套

可能很多人都使用过`RxLifecycle`这一框架来自动取消掉`RxJava`的引用,不过本人不太喜欢这种框架,原因是需要继承这种框架的`Activity`或者`Fragment`

个人认为这种方式对老项目不太友好,而且在替换的过程中,`P`层可能还会需要持有`RxLifecycle`的引用,替换过程比较麻烦

`RxJava`如何实现对生命周期的感知这里不做过多描述,都是使用`BehaviorSubject`在`Activity`中每个生命周期阶段发射一个消息去通知`RxJava`

如果是`onDestroy`则取消订阅,有兴趣的可以自行查询.

## 感知生命周期

`google`已经推出了一套完整的`Lifecycle`框架,这里借助这个框架去实现自动感知功能

`google`推荐在`java8`中使用`DefaultLifecycleObserver`而不是使用注解功能去感知生命周期,因此项目需要依赖

    compileOnly "android.arch.lifecycle:common-java8:1.1.1"
    
并继承`DefaultLifecycleObserver`,如果没有使用`java8`使用`@OnLifecycleEvent`也可以一样达到目的

> 按照上面所说再来改造一下`MVP`框架

## 再次封装

`BasePresenter`继承`DefaultLifecycleObserver`

    interface BaseIPresenter : DefaultLifecycleObserver {
        fun onDestroy()
    }

更新`BasePresenterImpl`

    open class BasePresenterImpl<V : BaseIView>(protected var mView: V?) : BaseIPresenter {
        override fun onCreate(owner: LifecycleOwner) {}
        override fun onDestroy(owner: LifecycleOwner) {}
        override fun onPause(owner: LifecycleOwner) {}
        override fun onResume(owner: LifecycleOwner) {}
        override fun onStart(owner: LifecycleOwner) {}
        override fun onStop(owner: LifecycleOwner) {}
        override fun onDestroy() {
            if (mView != null) {
                mView = null
            }
        }
    }

这个时候只需要在`BaseActivity`初始化`Presenter`添加对`Activity`的生命周期观察,那么`BasePresenterImpl`就可以接收到生命周期变化

    abstract class BaseActivity<P : BaseIPresenter> : AppCompatActivity() {
        protected lateinit var presenter: P
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            presenter = initPresenter()
            //添加一个观察者对象
            lifecycle.addObserver(presenter)
        }
        protected abstract fun initPresenter(): P
        override fun onDestroy() {
            super.onDestroy()
            lifecycle.removeObserver(presenter)
            presenter.onDestroy()
        }
    }
    
主要就是添加`lifecycle.addObserver(presenter)`这样行了,`Lifecycle`库使用起来还是很方便的

记得在`onDestroy`中取消订阅`lifecycle.removeObserver(presenter)`

这里要注意的是`Fragment`中需要在`onDestroy`中取消而不是`onDestroyView`中取消,否则接收不到消息

这个时候更新下`BasePresenterImpl`来实现`RxJava`的自动取消订阅功能

    open class BasePresenterImpl<V : BaseIView>(protected var mView: V?) : BaseIPresenter {
    
        private val behaviorSubject = BehaviorSubject.create<Lifecycle.Event>()
    
        protected fun <T> bindLifecycle(): ObservableTransformer<T, T> =
            ObservableTransformer { observable -> observable.takeUntil(behaviorSubject.skipWhile { event -> event !== Lifecycle.Event.ON_DESTROY }) }
    
        override fun onStop(@NonNull owner: LifecycleOwner) {
            behaviorSubject.onNext(Lifecycle.Event.ON_STOP)
        }
    
        override fun onStart(@NonNull owner: LifecycleOwner) {
            behaviorSubject.onNext(Lifecycle.Event.ON_START)
        }
    
        override fun onResume(@NonNull owner: LifecycleOwner) {
            behaviorSubject.onNext(Lifecycle.Event.ON_STOP)
        }
    
        override fun onPause(@NonNull owner: LifecycleOwner) {
            behaviorSubject.onNext(Lifecycle.Event.ON_PAUSE)
        }
    
        override fun onCreate(@NonNull owner: LifecycleOwner) {
            behaviorSubject.onNext(Lifecycle.Event.ON_CREATE)
        }
    
        override fun onDestroy(@NonNull owner: LifecycleOwner) {
            behaviorSubject.onNext(Lifecycle.Event.ON_DESTROY)
            onDestroy()
        }
    
        override fun onDestroy() {
            if (mView != null) {
                mView = null
            }
        }
    }
    
这样只要在原有的`observable`中添加一个`compose`即可,完整示例如下

    RxNetWork
        .instance
        .getApi(tag, observable.compose(bindLifecycle()), this)
    
就不用再手动取消订阅,也不用担心哪个页面忘记取消订阅而造成的空指针异常了。







