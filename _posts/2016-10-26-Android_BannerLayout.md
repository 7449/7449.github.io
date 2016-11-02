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

- 支持自定义提示栏


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

3.点击事件，也可以自己单独写，如果是list集合 返回的是当前model对象，如果是数组，返回的object就是当前数组对象,自定义的model如果获取的json图片命名不是image，请自行实现	ImageLoaderManage，返回的object里面获取网络url


		bannerLayout
                .initImageListResources(list) 
                .initAdapter()
                .initRound()
                .start(true)
                .setOnBannerClickListener(new BannerLayout.OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position, Object model) {
                        ImageModel imageModel = (ImageModel) model;
                        Toast.makeText(getApplicationContext(), imageModel.getTestText(), Toast.LENGTH_SHORT).show();
                        //如果是array  返回的object是传入的数组对象
	//                        int[] image = (int[]) model;
	//                        Toast.makeText(getApplicationContext(), image[position], Toast.LENGTH_SHORT).show();

                    }
                })
                });

4.提示栏及小圆点、title位置的改变

	想要改变位置在initRound()方法中实现几种不同的状态，不需要的可以直接传null 有默认的参数
	
	代码中提供了三个枚举
	
	- BANNER_ROUND_CONTAINER_POSITION 	 提示栏在布局中的位置，TOP,BUTTOM,CENTERED三种可选 
	- BANNER_ROUND_POSITION  	小圆点在提示栏的位置，LEFT,CENTERED,RIGHT三种可选 
	- BANNER_TITLE_POSITION  	title在提示栏的位置，LEFT,CENTERED,RIGHT三种可选 




5.使用自定义加载图片框架
	  
	默认使用Glide加载图片，如果不喜欢的继承 ImageLoaderManage 然后在代码中 setImageLoaderManage.

	 bannerLayout
                .initImageListResources(mBanner)
                .setImageLoaderManage(new ImageLoader()) //自己定义加载图片的方式
                .initAdapter()
                .initRound(true, true, false)
                .start(true);

	Glide默认就算是本地的资源文件也可以加载，但是Picasso加载时不行，如果使用Picasso加载图片请把url强转成int类型，其他的没有试过。

	

	    public class ImageLoader implements ImageLoaderManage {
	
	        @Override
	        public void display(Context context, ImageView imageView, Object url, Object model) {
				//如果是list集合 返回的model就是当前Model对象，如果是数组，返回的model就是当前数组对象
	            Picasso.with(context).load((int) url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
	        }
	    }


6.自定义提示栏

>自定义提示栏不建议使用，为了简便才封装，如果使用自定义提示栏就违背初衷，所以没有什么能快速设置的功能请尽量提[lssues](https://github.com/7449/BannerLayoutSimple/issues),除非是非常奇葩的需求，再使用自定义提示栏吧

        bannerLayout
                .initImageListResources(mDatas)
                .setTitleSetting(ContextCompat.getColor(this, R.color.colorPrimary), -1)
                .addOnBannerPageChangeListener(new BannerOnPage())
				.initPromptBar(new PromptBarView(getBaseContext())) //自定义提示栏view initAdapter之前调用生效
                .initAdapter()
                .start(true);

     /**
     * 接管viewpager的onPage方法
     */
    public class BannerOnPage implements BannerLayout.OnBannerPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.i(getClass().getSimpleName(), position + "");
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
	


>最后调用start（）的时候可以决定是否开启自动轮播，不管在fragment还是activity里面，应该在合适的生命周期里选择暂停或者恢复轮播（如果开启了自动轮播），BannerLayout已经提供了方法，使用者直接调用就可以了，如果使用List数据，请使用BannerModel