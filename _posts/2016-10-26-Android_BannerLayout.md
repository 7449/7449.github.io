---
layout:     post
title:      "Android_BannerLayout"
subtitle:   "支持图片无限轮播的BannerLayout"
date:       2016-10-26
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - banner
    - android
    - widget
---



支持图片无限轮播的BannerLayout


> RecyclerView Banner


利用`RecyclerView`实现的Banner轮播


[RecyclerBanner](https://github.com/7449/BannerLayout/tree/RecyclerBanner)


## 代码示例：

[BannerLayout](https://github.com/7449/BannerLayout)

# BannerLayout

支持图片无限轮播的BannerLayout


## 支持功能


- 可自定义小圆点,title,提示栏位置，支持自定义selector选择器

- 可自定义是否自动轮播，轮播时间

- 支持点击事件以及轮播速度及viewPager滑动切换速度

- 支持是否显示小圆点，title,或者整个提示栏

- 支持加载时和加载失败时显示自定义图片

- 支持选择暂停 恢复 轮播状态

- 支持动画以及垂直滚动

#### 使用效果


![_config.yml]({{ site.baseurl }}/img/banner_simple.gif)
![_config.yml]({{ site.baseurl }}/img/banner_transformer.gif)
![_config.yml]({{ site.baseurl }}/img/banner_refresh.gif)
![_config.yml]({{ site.baseurl }}/img/banner_imagemanager.gif)


## 基础使用方法

>gradle

    api 'com.ydevelop:bannerlayout:1.1.3'
    
>maven

	<dependency>
	  <groupId>com.ydevelop</groupId>
	  <artifactId>bannerlayout</artifactId>
	  <version>1.1.3</version>
	  <type>pom</type>
	</dependency>


建议先看一下示例 ： [SimpleActivity](https://github.com/7449/BannerLayout/tree/master/app/src/main/java/com/bannersimple/simple)


>如果是网络加载图片 记得添加

	<uses-permission android:name="android.permission.INTERNET" />

>简单使用方式

Bean类请实现 [BannerModelCallBack](https://github.com/7449/BannerLayout/blob/master/bannerlayout/src/main/java/com/bannerlayout/listener/BannerModelCallBack.kt)

具体可参考  [SimpleBannerModel](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/bannersimple/bean/SimpleBannerModel.kt)

如果让`BannerLayout`实现图片的加载记得依赖Glide

    implementation 'com.github.bumptech.glide:glide:4.8.0'
    
或者实现`ImageLoaderManager `

    class ImageLoaderSimpleManager : ImageLoaderManager<SimpleBannerModel> {
    
        override fun display(container: ViewGroup, model: SimpleBannerModel): ImageView {
            val imageView = ImageView(container.context)
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage(model.bannerUrl, imageView)
            return imageView
        }
    }
	
示例 :[ImageManagerSimple](https://github.com/7449/BannerLayout/tree/master/app/src/main/java/com/bannersimple/imagemanager)

## 简易实现:

    banner.resource(data).switchBanner(true/false);
                
## 垂直滚动

    banner.setVertical(true)

## PageChangeListener

    bannerLayout.onBannerChangeListener = object : OnBannerChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }
                        
## 点击事件

>如果不传递泛型，返回的model就是当前Bean类，强转即可，建议传递泛型

    bannerLayout.onBannerClickListener = object : OnBannerClickListener<SimpleBannerModel> {
        override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {
        }
    }

## 提示栏及小圆点、title位置的改变

	tipsSite 	 			提示栏在布局中的位置，top,bottom,center三种可选 
	dotsSite	  			小圆点在提示栏的位置，left,center,right三种可选 
	titleSite  			    title在提示栏的位置，left,center,right三种可选 

	xml:
		    <com.bannerlayout.widget.BannerLayout
		        ...
		        app:tips_site="center" />


## 切换动画以及速度

`viewpager`的垂直这里用的是动画，所以只要选择了垂直滚动，设置动画无效

动画内置的 [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，多谢作者
	
	banner.bannerTransformerType = BannerLayout.ANIMATION_CUBE_IN
	
	or
	
	banner.bannerTransformer = ZoomOutSlideTransformer()

## 动画集合：

    List<BannerTransformer> bannerTransformer = new ArrayList<>();
    
    etBannerSystemTransformerList(bannerTransformer);

#### java 方法

see: [MethodTestActivity](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/bannersimple/simple/MethodTestActivity.kt)

     bannerLayout
             .apply {
                 delayTime = 3000
                 errorImageView = R.mipmap.ic_launcher
                 placeImageView = R.mipmap.ic_launcher
                 bannerDuration = 3000
                 viewPagerTouchMode = true
                 isVertical = true
                 titleColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)
                 titleSize = 23F
                 tipsLayoutBackgroundColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
                 dotsSelector = R.drawable.banner
                 tipsWidth = BannerLayout.MATCH_PARENT
                 tipsHeight = 300
                 tipsSite = BannerLayout.TOP
                 ...
             }
             .initPageNumView()
             .initTips()
             .resource(SimpleData.initModel())
             .switchBanner(true)
                
#### xml 属性

> xml default parameter see:

[BannerDefaults.java](https://github.com/7449/BannerLayout/blob/master/bannerlayout/src/main/java/com/bannerlayout/widget/BannerDefaults.kt)

        app:banner_guide="true"
        app:banner_dots_visible="true"
        app:banner_page_num_radius="2"                              
        app:banner_page_num_paddingLeft="10"                        
        app:banner_page_num_paddingTop="10"                         
        app:banner_page_num_paddingRight="10"                       
        app:banner_page_num_paddingBottom="10"                      
        app:banner_page_num_marginTop="10"                          
        app:banner_page_num_marginLeft="10"                         
        app:banner_page_num_marginRight="10"                        
        app:banner_page_num_marginBottom="10"                       
        app:banner_page_num_textColor="@color/colorYellow"          
        app:banner_page_num_textSize="22sp"                         
        app:banner_page_num_BackgroundColor="@color/colorYellow"    
        app:banner_page_num_mark="@string/app_name"                 
        app:banner_pageNum_site="bottomRight"                       
        app:banner_tips_site="bottom"                               
        app:banner_dots_site="center"                               
        app:banner_title_site="center"                              
        app:banner_start_rotation="true"                            
        app:banner_title_visible="true"                             
        app:banner_title_size="10sp"                                
        app:banner_title_color="@color/colorYellow"                 
        app:banner_title_width="200"                                
        app:banner_title_height="60"                                
        app:banner_title_left_margin="60"                           
        app:banner_title_right_margin="60"                          
        app:banner_dots_left_margin="40"                            
        app:banner_dots_right_margin="40"                           
        app:banner_dots_width="30"                                  
        app:banner_dots_height="30"                                 
        app:banner_enabledRadius="2"                                
        app:banner_enabledColor="@color/colorAccent"                
        app:banner_normalRadius="2"                                 
        app:banner_normalColor="@color/colorBackground"             
        app:banner_glide_error_image="@mipmap/ic_launcher"          
        app:banner_glide_place_image="@mipmap/ic_launcher"          
        app:banner_is_tips_background="true"                        
        app:banner_tips_background="@color/colorAccent"             
        app:banner_tips_width="match_parent"                        
        app:banner_tips_height="300"                                
        app:banner_dots_selector="@drawable/banner"                 
        app:banner_start_rotation="true"                            
        app:banner_delay_time="300"                                 
        app:banner_duration="3000"                                  
        app:banner_view_pager_touch_mode="true"      


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
