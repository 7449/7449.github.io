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

[https://github.com/7449/BannerLayout(https://github.com/7449/BannerLayout)](https://github.com/7449/BannerLayout)

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

    compile 'com.ydevelop:bannerlayout:1.1.1'
    
>maven

	<dependency>
	  <groupId>com.ydevelop</groupId>
	  <artifactId>bannerlayout</artifactId>
	  <version>1.1.1</version>
	  <type>pom</type>
	</dependency>


建议先看一下示例 ： [SimpleActivity](https://github.com/7449/BannerLayout/tree/master/app/src/main/java/com/bannersimple/simple)


>如果是网络加载图片 记得添加

	<uses-permission android:name="android.permission.INTERNET" />

>简单使用方式

Bean类请实现 [BannerModelCallBack](https://github.com/7449/BannerLayout/blob/master/bannerlayout/src/main/java/com/bannerlayout/Interface/BannerModelCallBack.java)

具体可参考  [SimpleBannerModel](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/bannersimple/bean/SimpleBannerModel.java)

如果让`BannerLayout`实现图片的加载记得依赖Glide

    compile 'com.github.bumptech.glide:glide:3.7.0'
    
或者实现`ImageLoaderManager `

	public class SimpleManager implements ImageLoaderManager<SimpleBannerModel> {
	
	    @NonNull
	    @Override
	    public ImageView display(@NonNull ViewGroup container, SimpleBannerModel model) {
	        ImageView imageView = new ImageView(container.getContext());
	        // picasso or fresco or universalimageloader
	        return imageView;
	    }
	}
	
示例 :[ImageManagerSimple](https://github.com/7449/BannerLayout/tree/master/app/src/main/java/com/bannersimple/imagemanager)

## 简易实现:

        banner
                .initListResources(data)
               	//if you use glide this step is not necessary
                .setImageLoaderManager(new SimpleImageManager()) 
                .switchBanner(true/false);
                
## 垂直滚动

        banner
                .setVertical(true)

## PageChangeListener

        banner
                .addOnPageChangeListener(
                        new OnBannerChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                        
## 点击事件

>如果不传递泛型，返回的model就是当前Bean类，强转即可，建议传递泛型

        banner
                .setOnClickListener(
                        new OnBannerClickListener<SimpleBannerModel>() {
                            @Override
                            public void onBannerClick(View view, int position, SimpleBannerModel model) {

                            }
                        });

## 提示栏及小圆点、title位置的改变

	setTipsSite() 	 			提示栏在布局中的位置，top,bottom,center三种可选 
	setDotsSite()	  			小圆点在提示栏的位置，left,center,right三种可选 
	setTitleSite()  			title在提示栏的位置，left,center,right三种可选 

	xml:
		    <com.bannerlayout.widget.BannerLayout
		        ...
		        app:tips_site="center" />


## 切换动画以及速度

`viewpager`的垂直这里用的是动画，所以只要选择了垂直滚动，设置动画无效

动画内置的 [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，多谢作者

	
	如果想自定义动画请继承 ABaseTransformer 或者 BannerTransformer 即可;
	
	setBannerTransformer(BannerLayout.ANIMATION_CUBE_IN)
	
	或者
	
	setBannerTransformer(new ZoomOutSlideTransformer())

## 动画集合：


>自定义动画集合

			 Integer: 0 ~ 17
	         List<Integer> integerList = new ArrayList<>();
	       
			 setBannerSystemTransformerList(transformers);

>系统动画集合

			List<BannerTransformer> bannerTransformer = new ArrayList<>();
			
			etBannerSystemTransformerList(bannerTransformer);

#### java 方法

see: [MethodTestActivity](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/bannersimple/simple/MethodTestActivity.java)

        newBannerLayout
                .setDelayTime(3000)
                .setErrorImageView(R.mipmap.ic_launcher)
                .setPlaceImageView(R.mipmap.ic_launcher)
                .setDuration(3000)
                .setViewPagerTouchMode(true)
                .setVertical(true)
                .setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark))
                .setTitleTextSize(23)
                .setTipsBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                .setTipsDotsSelector(R.drawable.banner)
                .setTipsWidthAndHeight(BannerLayout.MATCH_PARENT, 300)
                .setTipsSite(BannerLayout.TOP)
                .setTitleMargin(60, 20)
                .setTitleSite(BannerLayout.LEFT)
                .setDotsWidthAndHeight(30, 30)
                .setDotsSite(BannerLayout.RIGHT)
                .setNormalRadius(2)
                .setNormalColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYellow))
                .setEnabledRadius(2)
                .setEnabledColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYellow))
                .setDotsMargin(60, 60)
                .setBannerTransformer(BannerLayout.ANIMATION_ZOOMOUT)
                .setBannerTransformer(new ZoomOutSlideTransformer())
                .setPageNumViewRadius(1)
                .setPageNumViewMargin(10, 10, 10, 10)
                .setPageNumViewPadding(10, 10, 10, 10)
                .setPageNumViewMargin(10)
                .setPageNumViewPadding(10)
                .setPageNumViewTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYellow))
                .setPageNumViewBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                .setPageNumViewSite(BannerLayout.PAGE_NUM_VIEW_BOTTOM_CENTER)
                .setPageNumViewTextSize(22)
                .setPageNumViewMark(" & ")
                .initPageNumView()
                .initTips(true, true, true)
                .setOnBannerClickListener(this)
                .addOnPageChangeListener(this)
                .initListResources(SimpleData.initModel())
                .switchBanner(true);
                
#### xml 属性

> xml default parameter see:

[BannerDefaults.java](https://github.com/7449/BannerLayout/blob/master/bannerlayout/src/main/java/com/bannerlayout/widget/BannerDefaults.java)

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
