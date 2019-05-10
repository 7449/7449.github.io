---
layout:     post
title:      "Android_XAdapter"
subtitle:   "BaseRVAdapter,支持下拉刷新加载和添加多个header和footer的RecyclerViewAdapter"
date:       2016-11-21
tags:
    - android
    - widget
---


# XAdapter

支持下拉刷新加载和添加多个header和footer的RecyclerViewAdapter

# 截图

![](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

[https://github.com/7449/XAdapter/blob/master/xadapter.gif](https://github.com/7449/XAdapter/blob/master/xadapter.gif)

### gradle

    compile 'com.ydevelop:rv-adapter:0.0.8'

## 示例

    mRecyclerView.adapter = xRecyclerViewAdapter.apply {
        dataContainer = mainBeen
        loadMoreView = LoadMoreView(applicationContext)
        refreshView = RefreshView(applicationContext)
        recyclerView = mRecyclerView
        pullRefreshEnabled = true
        loadingMoreEnabled = true
        scrollLoadMoreItemCount = 10
        headerViewContainer.apply {
            add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
            add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_2, findViewById(android.R.id.content), false))
            add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_3, findViewById(android.R.id.content), false))
        }
        footerViewContainer.apply {
            add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
            add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_2, findViewById(android.R.id.content), false))
            add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_3, findViewById(android.R.id.content), false))
        }
        onXBindListener = this@LinearLayoutManagerActivity
        onLongClickListener = this@LinearLayoutManagerActivity
        onItemClickListener = this@LinearLayoutManagerActivity
        xAdapterListener = this@LinearLayoutManagerActivity
        onFooterListener = this@LinearLayoutManagerActivity
        itemLayoutId = R.layout.item
    }

onXBind  

    override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
        holder.setTextView(R.id.tv_name, entity.name)
        holder.setTextView(R.id.tv_age, entity.age.toString() + "")
    }

## emptyView

>必须要添加RecyclerView才会起作用
	
    mRecyclerView.adapter = xRecyclerViewAdapter
            .apply {
                emptyView = findViewById(R.id.emptyView)
                recyclerView = mRecyclerView
            }

## 刷新

默认关闭，如果打开则要添加RecyclerView，因为要监控触摸

    mRecyclerView.adapter = xRecyclerViewAdapter.apply {
        xAdapterListener = object : OnXAdapterListener {
            override fun onXRefresh() {
            }
            override fun onXLoadMore() {
            }
        }
    }

更新状态

>xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS

>xRecyclerViewAdapter.loadMoreState = XLoadMoreView.NOMORE

### addHeader addFooter

    xRecyclerViewAdapter
     .addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header_1, (ViewGroup) findViewById(android.R.id.content), false))
     .addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer_1, (ViewGroup) findViewById(android.R.id.content), false))
		 
### 多种状态布局

see [multi](https://github.com/7449/XAdapter/tree/master/xadapterLibrary/src/main/java/com/xadapter/adapter/XMultiAdapter.kt)

#### 自定义刷新头部 

    class RefreshView : XRefreshView {
    
        constructor(context: Context) : super(context)
    
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
        public override fun initView() {
        }
    
        override fun getLayoutId(): Int {
        }
    
        override fun onStart() {
        }
    
        override fun onNormal() {
        }
    
        override fun onReady() {
        }
    
        override fun onRefresh() {
        }
    
        override fun onSuccess() {
        }
    
        override fun onError() {
        }
    }

#### 自定义刷新尾部

    class LoadMoreView : XLoadMoreView {
    
        constructor(context: Context) : super(context)
    
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
        override fun initView() {
        }
    
        override fun getLayoutId(): Int {
        }
    
        override fun onStart() {
        }
    
        override fun onLoad() {
        }
    
        override fun onNoMore() {
        }
    
        override fun onSuccess() {
        }
    
        override fun onError() {
        }
    
        override fun onNormal() {
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

