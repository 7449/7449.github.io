---
layout: post
title: Android_BannerLayout
---

一个支持图片无限轮播的控件，以最少的代码实现Android Banner的实现，仅需五行代码


一些自定义参数请看示例代码里面的说明，这里就不再多说

代码示例：[https://github.com/7449/BannerLayoutSimple](https://github.com/7449/BannerLayoutSimple)

- 支持网络本地加载数据

- 可自定义小圆点状态，左 中 右

- 可自定义title状态，左 中 右

- 可自定义提示栏状态，上 中 下

- 可自定义小圆点

- 可自定义是否自动轮播

- 支持List 、数组 两种数据格式

- 支持点击事件

- 支持设置轮播速度

- 支持是否显示小圆点，title,或者整个提示栏

- 支持加载时和加载失败时图片显示状态

- 支持选择暂停 恢复 轮播状态


使用效果

![_config.yml]({{ site.baseurl }}/images/bannerLayout.gif)



基础使用方法

1.数组方式

        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.bannerLayout);
        int[] mImage = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
        String[] mTitle = new String[]{"bannerl", "banner2", "banner3"};
        bannerLayout
                .initImageArrayResources(mImage, mTitle)
                .initAdapter()
                .initRound(true, true, true)
                .start(true);

2.List集合

        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.bannerLayout);
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了"));
        bannerLayout
                .initImageListResources(mDatas)
                .initAdapter()
                .initRound()
                .start(true);	

3.点击事件，也可以自己单独写

		bannerLayout
                .initImageArrayResources(mImage, mTitle)
                .initAdapter()
                .initRound()
                .start(true)
                .setOnBannerClickListener(new BannerLayout.OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position) {

                    }
                });

4.提示栏及小圆点、title位置的改变

想要改变位置在initRound()方法中实现几种不同的状态，不需要的可以直接传null 有默认的参数

代码中提供了三个枚举

BANNER_ROUND_CONTAINER_POSITION 	 提示栏在布局中的位置，TOP,BUTTOM,CENTERED三种可选

BANNER_ROUND_POSITION  	小圆点在提示栏的位置，LEFT,CENTERED,RIGHT三种可选 

BANNER_TITLE_POSITION  	title在提示栏的位置，LEFT,CENTERED,RIGHT三种可选   


最后调用start（）的时候可以决定是否开启自动轮播，不管在fragment还是activity里面，应该在合适的生命周期里选择暂停或者恢复轮播（如果开启了自动轮播），BannerLayout已经提供了方法，使用者直接调用就可以了，如果使用List数据，请使用BannerModel

