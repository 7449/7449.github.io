---
layout:     post
title:      "Android_MVP"
subtitle:   "Android中MVP的使用"
date:       2016-10-8
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - mvp
    - 框架
    - android
---

本文通过mvp来理解不同项目中mvp模式，并且对MVP进一步封装，所有的模式都是为了方便代码管理，解耦，模式并不重要，重要的是自己的理解以及代码的可读性


## 代码示例：

[https://github.com/7449/ZLSimple](https://github.com/7449/ZLSimple)


## 什么是MVP？

	MVP是model,view,presenter的缩写，代表了三个不同的模块，

	model:用来处理数据的加载或者存储，比如从网络或本地数据库获取数据
	
	view :负责界面数据的展示
	
	presenter:类似于桥梁的功能,是M层和V层的桥梁。

>百度一下可得到如下数据：

	优点:
	
	1、模型与视图完全分离，我们可以修改视图而不影响模型
	
	2、可以更高效地使用模型，因为所有的交互都发生在一个地方——Presenter 内部
	
	3、我们可以将一个 Presenter 用于多个视图，而不需要改变 Presenter 的逻辑。这个特性非常的有用，因为视图的变化总是比模型的变化频繁。
	
	4、如果我们把逻辑放在 Presenter 中，那么我们就可以脱离用户接口来测试这些逻辑（单元测试）

![_config.yml]({{ site.baseurl }}/img/mvp.jpg)

>这样分层的好处就是减少了 Model 与 View 层之间的耦合度。一方面可以使得 View 层和 Model 层单独开发与测试，互不依赖。另一方面 Model 层可以封装复用，可以极大的减少代码量

项目示例中多一个widget 存放activity或者adapter,fragment之类的文件。

## 先封装一下MVP框架

>BaseView:

现在每个页面几乎都会有网络连接和用动画来显示进度

	public interface BaseView {
	    void showProgress();
	
	    void hideProgress();

		void netWorkError();
	}

>BasePresenterImpl:

因为PresenterImpl需要持有View对象 所以使用泛型来减少代码量
	
	public abstract class BasePresenterImpl<V> {
	
	    protected final V view;
	
	    public BasePresenterImpl(V view) {
	        this.view = view;
	    }

	}

>Model：

如果有code和message,那么可以封装，如果没有 就没必要，这个看情况而定。

## 简单的封装已经完成，再来看看MVP在项目中的使用：
	
Model没有过多的解释  就是json的实体类

view 继承BaseView 单独写个传输数据的方法,然后Activity或者Fragment继承View

	public interface IListView extends BaseView {
	    void setData(List<ListModel> data);
	}

>presenter 只是一个单独的请求网络的接口

	public interface ListPresenter {
	    void netWorkRequest(String suffix, int limit);
	}

>presenterImpl 继承BasePresenterImpl 

	public class ListPresenterImpl extends BasePresenterImpl<IListView>
	        implements ListPresenter {
	
	
	    public ListPresenterImpl(IListView view) {
	        super(view);
	    }
	
	
	    @Override
	    public void netWorkRequest(String suffix, int limit) {
	        view.showProgress();
	        NetWorkRequest.getList(suffix, limit, new MySubscriber<List<ListModel>>() {
	            @Override
	            public void onNext(List<ListModel> listModel) {
	                super.onNext(listModel);
	                view.setData(listModel);
	                view.hideProgress();
	            }
	        });
	    }
	
	    @Override
	    protected void netWorkError() {
	        view.hideProgress();
	        view.netWorkError();
	    }
	}

	
这里使用的Rxjava和retrofit2请求的网络，所以看起来和上面讲的有些不同，因为我为了偷懒直接用RxBus去实现的NetWorkError(); 毕竟页面较少，这样用起来虽然所有地方都会收到报错响应，但没什么影响


>在Fragment 里面初始化Presenter，然后请求网络，这里应该实在网络成功之后adapter才RemoveAll()，但是已经这样写了 就一直没改

		@Override
	    public void onRefresh() {
	        adapter.removeAll();
	        listPresenter.netWorkRequest(getSuffix(pos, type), Constant.LIMIT);
	    }

>最后，我这里用的Fragment实现的页面逻辑操作

	   @Override
	    public void setData(List<ListModel> data) {
	        if (!data.isEmpty()) {
	            adapter.addAll(data);
	        }
	    }
	
	    @Override
	    public void netWorkError() {
	        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
	    }
	
	    @Override
	    public void showProgress() {
	        swipeRefreshLayout.setRefreshing(true);
	    }
	
	    @Override
	    public void hideProgress() {
	        swipeRefreshLayout.setRefreshing(false);
	    }

![_config.yml]({{ site.baseurl }}/img/mvpimage.gif)