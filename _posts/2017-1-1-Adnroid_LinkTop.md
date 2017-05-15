---
layout:     post
title:      "Android_design_上拉悬停"
subtitle:   "利用design实现控件上拉悬停效果"
date:       2017-01-1
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
---


## sample

[https://github.com/7449/AndroidDevelop/tree/master/linkTop](https://github.com/7449/AndroidDevelop/tree/master/linkTop)

## 效果图

![_config.yml]({{ site.baseurl }}/img/linktop.gif)


## 布局


	<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent">
	
	    <android.support.design.widget.AppBarLayout
	        android:id="@+id/app_bar"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content">
	
	        <android.support.design.widget.CollapsingToolbarLayout
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"
	            android:background="#fff"
	            app:layout_scrollFlags="scroll">
	
	            <android.support.v7.widget.AppCompatImageView
	                android:layout_width="match_parent"
	                android:layout_height="160dp"
	                android:src="@mipmap/ic_launcher" />
	
	
	        </android.support.design.widget.CollapsingToolbarLayout>
	
	
	        <LinearLayout
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"
	            android:orientation="horizontal">
	
	            <android.support.v7.widget.AppCompatButton
	                android:layout_width="match_parent"
	                android:layout_height="wrap_content"
	                android:layout_weight="1"
	                android:text="@string/app_name" />
	
	            <android.support.v7.widget.AppCompatButton
	                android:layout_width="match_parent"
	                android:layout_height="wrap_content"
	                android:layout_weight="1"
	                android:text="@string/app_name" />
	
	            <android.support.v7.widget.AppCompatButton
	                android:layout_width="match_parent"
	                android:layout_height="wrap_content"
	                android:layout_weight="1"
	                android:text="@string/app_name" />
	
	
	        </LinearLayout>
	
	    </android.support.design.widget.AppBarLayout>
	
	    <android.support.v4.widget.NestedScrollView
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        app:layout_behavior="@string/appbar_scrolling_view_behavior">
	
	        <LinearLayout
	            android:layout_width="match_parent"
	            android:layout_height="match_parent"
	            android:orientation="vertical">
	
	            <TextView
	                android:id="@+id/tv"
	                android:gravity="center"
	                android:text=" 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n 山上有座庙 \n 庙里有个老和尚讲故事 \n 讲的故事是 \n ... "
	                android:layout_width="match_parent"
	                android:layout_height="wrap_content" />
	        </LinearLayout>
	    </android.support.v4.widget.NestedScrollView>
	
	</android.support.design.widget.CoordinatorLayout>

