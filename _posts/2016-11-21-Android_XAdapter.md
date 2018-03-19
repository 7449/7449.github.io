---
layout:     post
title:      "Android_XAdapter"
subtitle:   "BaseRVAdapter,支持下拉刷新加载和添加多个header和footer的RecyclerViewAdapter"
date:       2016-11-12
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - widget
---



# XAdapter
支持下拉刷新加载和添加多个header和footer的RecyclerViewAdapter


# Screenshots

![](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

[https://github.com/7449/XAdapter/blob/master/xadapter.gif](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

### gradle

>compile 'com.ydevelop:rv-adapter:0.0.1
 


### tips

initXData(); 并不是强制性的，只有RecyclerView刚开始就需要有数据的List集合时才必须要调用 initXData();

## 完整示例


    recyclerView.setAdapter(
            xRecyclerViewAdapter
                    .initXData(mainBeen)
                    .setLoadMoreView(View)
                    .setRefreshView(View)
                    .addRecyclerView(recyclerView)
                    .setLayoutId(R.layout.item)
                    .setPullRefreshEnabled(true)
                    .setLoadingMoreEnabled(true)
                    .addHeaderView(View)
                    .addFooterView(View)
                    .onXBind(this)
                    .setOnLongClickListener(this)
                    .setOnItemClickListener(this)
                    .setLoadListener(this)
                    .setFooterListener(this)
                    .refresh()
    );

onXBind  
这里进行数据的展示

    @Override
    public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
        holder.setTextView(R.id.tv_name, mainBean.getName());
        holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
    }

## emptyView

>是否显示由用户自己手动决定，在网络异常或者数据为空的时候调用xRecyclerViewAdapter.isShowEmptyView();具体情况simple有例子
>true 可选，自己响应点击事件，点击自动触发 下拉刷新

        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBean)
                        .addRecyclerView(recyclerView)
                        .setEmptyView(viewById, true) 
                        .setPullRefreshEnabled(true)
                        .setLoadListener(this)
                        .setLayoutId(R.layout.item)
        );


## 下拉刷新和上拉加载

默认不打开，如果有必要，请手动打开，并调用addRecyclerView

                xRecyclerViewAdapter
					.initXData(mainBean)
	                .setLayoutId(R.layout.item)
	                .addRecyclerView(recyclerView)
	                .setPullRefreshEnabled(true)
	                .setPullRefreshEnabled(true)
	                .setLoadingListener(new XBaseAdapter.LoadingListener() {
	                    @Override
	                    public void onRefresh() {
	                        
	                    }
	
	                    @Override
	                    public void onLoadMore() {
	
	                    }
	                })

下拉刷新完成之后

这取决于用户选择刷新是否失败或成功

>xRecyclerViewAdapter.refreshState(XRefresh.SUCCESS);



上拉加载完成之后

这取决于用户选择加载是否失败或成功

>xRecyclerViewAdapter.loadMoreState(XLoadMore.ERROR);


### 添加header和footer

		xRecyclerViewAdapter
		 .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
		 .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
		 
		 
### 多种type

继承 `MultiAdapter` 并且`T`必须继承`MultiCallBack`
已经内置了一个简单的示例，详情查看 `SimpleMultiItem`

### 自定义刷新头部和尾部

	public class RefreshView extends XRefreshView {
	
	    public RefreshView(Context context) {
	        super(context);
	    }
	
	    public RefreshView(Context context, @Nullable AttributeSet attrs) {
	        super(context, attrs);
	    }
	
	    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	
	    @Override
	    public void initView() {
	    }
	    
	    @Override
	    protected int getLayoutId() {
	        return 0;
	    }
	
	    @Override
	    protected void onStart() {
	    }
	
	    @Override
	    protected void onNormal() {
	    }
	
	    @Override
	    protected void onReady() {
	    }
	
	    @Override
	    protected void onRefresh() {
	    }
	
	    @Override
	    protected void onSuccess() {
	    }
	
	    @Override
	    protected void onError() {
	    }
	
	
	}
	
	
	public class LoadMoreView extends XLoadMoreView {
	
	
	    public LoadMoreView(Context context) {
	        super(context);
	    }
	
	    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
	        super(context, attrs);
	    }
	
	    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	
	    @Override
	    protected void initView() {
	    }
	
	    @Override
	    protected int getLayoutId() {
	        return 0;
	    }
	
	    @Override
	    protected void onStart() {
	    }
	
	    @Override
	    protected void onLoad() {
	    }
	
	    @Override
	    protected void onNoMore() {
	    }
	
	    @Override
	    protected void onSuccess() {
	    }
	
	    @Override
	    protected void onError() {
	    }
	
	    @Override
	    protected void onNormal() {
	    }
	}

License
--
    Copyright (C) 2016 yuebigmeow@gamil.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
