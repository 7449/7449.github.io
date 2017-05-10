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

![](http://i.imgur.com/WnrNvI4.gif)

## 基础使用方法

>项目中引用 

		compile 'com.ydevelop:bannerlayout:1.1'



使用者如果用自带的加载框架，请自行依赖 Glide

因为框架中

    provided 'com.github.bumptech.glide:glide:3.7.0'

>如果是网络加载图片 记得添加

	<uses-permission android:name="android.permission.INTERNET" />

>简单使用方式

Bean类请实现 [BannerModelCallBack](https://github.com/7449/BannerLayout/blob/master/bannerlayout/src/main/java/com/bannerlayout/Interface/BannerModelCallBack.java)

具体可参考  [SimpleBannerModel](https://github.com/7449/BannerLayout/blob/master/app/src/main/java/com/bannersimple/bean/SimpleBannerModel.java)

            bannerLayout
                    .initListResources(initImageModel())//初始化数据
                    .initTips(true, true, true)//设置tips
                    .start(true, 2000)//轮播 轮播时间

>调用start()的时候可以决定是否开启自动轮播，如果开启了自动轮播应该在合适的生命周期里选择暂停或者恢复轮播

	startBanner(true/false);

0.页码展示：

           bannerLayout
                    .initPageNumView();


1.List

        List<BannerModel> mDatas = new ArrayList<>();
		...
        bannerLayout
                .initImageListResources(mDatas)
                .initTips()
                .start(true);	

2.点击事件

>如果不传递泛型，返回的model就是当前Bean类，强转即可，建议传递泛型

            bannerLayout
             .initListResources(initImageModel())
             .setOnBannerClickListener(new OnBannerClickListener<ImageModel>() {
                 @Override
                 public void onBannerClick(View view, int position, ImageModel model) {
                   Toast.makeText(holder.getContext(), model.getTestText(), Toast.LENGTH_SHORT).show();
                        }
                    });

3.提示栏及小圆点、title位置的改变

	setTipsSite() 	 			提示栏在布局中的位置，top,bottom,center三种可选 
	setDotsSite()	  			小圆点在提示栏的位置，left,center,right三种可选 
	setTitleSite()  			title在提示栏的位置，left,center,right三种可选 

	xml:
		    <com.bannerlayout.widget.BannerLayout
		        ...
		        app:tips_site="center" />

4.使用自定义加载图片框架
	  
	默认使用Glide加载图片，如果不喜欢的继承 ImageLoaderManager 然后在代码中 setImageLoaderManager.

	 bannerLayout
                .initImageListResources(mBanner)
                .setImageLoaderManage(new ImageLoader()) //自己定义加载图片的方式
                .initTips(true, true, false)
                .start(true);

	public class ImageManager implements ImageLoaderManager<BannerBean> {
	
	    @Override
	    public void display(ImageView imageView, BannerBean model) {
	        Picasso.with(imageView.getContext())
	                .load(model.getImageUrl())
	                .placeholder(R.mipmap.ic_launcher)
	                .error(R.mipmap.ic_launcher)
	                .into(imageView);
	    }
	}

5.切换动画以及速度

>垂直滚动的动画

viewpager的垂直这里用的是动画，所以只要选择了垂直滚动，设置动画无效

动画内置的 [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)，多谢作者

	
	如果想自定义动画请继承 ABaseTransformer 或者 BannerTransformer 即可;
	
	        bannerLayout
	                .initImageListResources(list) //自定义model类
	                .initTips()
	                .setBannerTransformer(new FlipVerticalTransformer())  //切换动画效果
	                .setBannerTransformerList(transformers) //开启随机动画,这里设置，那就没必要设置切换动画效果了，需要一个list动画集合
	                .setDuration(3000) //切换速度
	                .start();
	
	如果只想使用内置的动画可以用 BannerAnimation 进行选择
	
	例：
	
		   bannerLayout
	                .setBannerTransformer(BannerAnimation.CUBE_IN);

6.动画集合：


>自定义动画集合

        List<BannerTransformer> transformers = new ArrayList<>();
       
		bannerLayout.setBannerTransformerList(transformers);

>系统动画集合

		 List<BannerAnimation> enumTransformer = new ArrayList<>();

		bannerLayout.setBannerSystemTransformerList(enumTransformer);

## 自定义参数详解

属性名								|说明  						|属性值
---    								|---   						|---
delay_time   						|轮播时间					|默认2s
start_rotation   					|是否开启自动轮播				|true 开启，默认不开启
view_pager_touch_mode   			|viewpager是否可以手动滑动	|true禁止滑动,false可以滑动，默认可以滑动
banner_duration						|viewPager切换速度			|默认800，越大越慢
banner_isVertical					|viewPager垂直滚动			|默认不是垂直滚动，true开启
dots_visible		  				|是否显示小圆点				|默认显示
dots_selector   					|小圆点状态选择器				|可参考自带的
dots_left_margin	   				|小圆点的marginLeft			|默认10	
dots_right_margin   				|小圆点的marginRight			|默认10	
dots_width   						|小圆点width					|默认15
dots_height   						|小圆点height				|默认15
is_tips_background					|是否显示提示控件的背景		|true 显示，默认不显示
tips_background				   		|BannerTips背景色			|默认半透明色
tips_width				   			|BannerTips宽度				|填充屏幕
tips_height			 				|BannerTips高度				|默认50
glide_error_image  					|glide加载错误占位符			|默认android自带图标
glide_place_image  					|glide加载中占位符			|默认android自带图标
title_visible		  				|是否显示title				|默认不显示
title_size			   				|字体大小					|默认12
title_color		 					|字体颜色					|默认黄色
title_width		 					|字体width					|默认自适应
title_height		 				|字体height					|默认自适应
title_left_margin   				|title marginLeft			|默认10	
title_right_margin   				|title marginRight			|默认10	
enabledRadius						|未选中小圆点Radius  			|默认20f
normalRadius						|选中小圆点Radius  			|默认20f
enabledColor						|未选中小圆点颜色				|默认蓝色
normalColor							|选中小圆点颜色				|默认白色
tips_site							|tips在布局中位置    			|默认底部，可选上中下
dots_site							|小圆点在布局中位置    		|默认底部，可选左中右
title_site							|title在布局中位置    		|默认底部，可选左中右
page_num_view_radius				|pageNumView shape radius   |默认25f
page_num_view_paddingTop			|pageNumView padding Top	|默认5
page_num_view_paddingLeft			|pageNumView padding Left	|默认20
page_num_view_paddingRight			|pageNumView padding Right	|默认20
page_num_view_paddingBottom			|pageNumView padding Bottom	|默认5
page_num_view_marginTop				|pageNumView margin 	 	|默认0
page_num_view_marginLeft			|pageNumView margin		  	|默认0
page_num_view_marginRight			|pageNumView margin  		|默认0
page_num_view_marginBottom			|pageNumView margin  		|默认0
page_num_view_textColor				|pageNumView textColor	 	|默认白色
page_num_view_BackgroundColor		|pageNumView BackgroundColor|默认半透明
page_num_view_textSize				|pageNumView textSize	  	|默认10
pageNumView_site					|pageNumView 位置			|默认初始化之后在左上角
page_num_view_mark				|pageNumView 符号 |默认 /

        <attr name="pageNumView_site">
            <enum name="topLeft" value="0" />
            <enum name="topRight" value="1" />
            <enum name="bottomLeft" value="2" />
            <enum name="bottomRight" value="3" />
            <enum name="centeredLeft" value="4" />
            <enum name="centeredRight" value="5" />
            <enum name="topCenter" value="6" />
            <enum name="bottomCenter" value="7" />
        </attr>



License
--
    Copyright (C) 2016 yuezhaoyang7449@163.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
