---
layout:     post
title:      "Android_首页Banner的封装"
subtitle:   "由于开发的app首页的好几个界面都有banner，结合相同的特点，把banner代码封装一下"
date:       2016-9-28
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - banner
    - android
---




## 代码地址：

[banner](https://github.com/7449/AndroidDevelop/tree/master/banner "Android Banner的封装")

## 改进版 bannerLayout（强烈建议试用此版本）：

[BannerLayout](https://github.com/7449/BannerLayout "Android Banner的封装")
	
## 设置一个接口得到想要的数据

	public interface BannerInterface {
	
	    ViewPager getViewPager();
	
	    LinearLayout getLinearLayout();
	
	    BasePagerAdapter getBannerAdapter();
	}

## 封装PagerAdapter

>这里对外提供两个方法，一个点击事件，一个获取数据的方法

	public abstract class BasePagerAdapter<T> extends PagerAdapter {
	
	    public List<T> mDatas = null;
	
	    public BasePagerAdapter(List<T> mDatas) {
	        this.mDatas = mDatas;
	    }
	
	    @Override
	    public int getCount() {
	        return Integer.MAX_VALUE;
	    }
	
	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	        return view == object;
	    }
	
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView((View) object);
	    }
	
	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	        position %= mDatas.size();
	        ImageView img = new ImageView(container.getContext());
	        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
	
	        //这里是本地数据，如果是网络数据请使用Glide或者其他的加载工具实现
	        // Glide.with(context).load(url).placeholder(R.drawable.bili_default_image)
	        // .error(R.drawable.bili_default_image).centerCrop().into(imageView);
	        //用Glide举例，一般url是String类型,把displayImage改成String即可
	
	        img.setBackgroundResource(displayImage(position));
	
	        final int finalPosition = position;
	        img.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                onImageClick(finalPosition, mDatas.get(finalPosition));
	            }
	        });
	        container.addView(img);
	        return img;
	    }
	
	    protected abstract int displayImage(int position);
	
	    protected abstract void onImageClick(int finalPosition, T mDatas);
	
	}

## 最重要的BannerHolder


>这里面实现了轮播以及小圆点，因为主要的控件以及数据已经通过接口取到了，所以这里只需要实现逻辑即可，这个类是通用的。

		public class BannerHolder implements ViewPagerHandlerUtils.ViewPagerCurrent {
	
	
	    private int preEnablePosition = 0;
	    private ViewPager mViewPager;
	    private ViewPagerHandlerUtils mHandlerUtil;
	    private LinearLayout mLinearLayout;
	
	    public <T> void setBanner(final List<T> banner, BannerInterface bannerInterface) {
	        initHolder(banner.size(), bannerInterface);
	
	        mViewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % banner.size()));
	
	        mHandlerUtil = new ViewPagerHandlerUtils(this, mViewPager.getCurrentItem());
	        //提供了setStart() 这个方法 如果想实现 在发送消息之前设置为false就行了
	
	        mHandlerUtil.sendEmptyMessage(ViewPagerHandlerUtils.MSG_START);
	
	        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	            @Override
	            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	
	            }
	
	            @Override
	            public void onPageSelected(int position) {
	                int newPosition = position % banner.size();
	                mLinearLayout.getChildAt(preEnablePosition).setEnabled(false);
	                mLinearLayout.getChildAt(newPosition).setEnabled(true);
	                preEnablePosition = newPosition;
	                mHandlerUtil.sendMessage(Message.obtain(mHandlerUtil, ViewPagerHandlerUtils.MSG_PAGE, mViewPager.getCurrentItem(), 0));
	            }
	
	            @Override
	            public void onPageScrollStateChanged(int state) {
	                switch (state) {
	                    case ViewPager.SCROLL_STATE_DRAGGING:
	                        mHandlerUtil.sendEmptyMessage(ViewPagerHandlerUtils.MSG_KEEP);
	                        break;
	                    case ViewPager.SCROLL_STATE_IDLE:
	                        mHandlerUtil.sendEmptyMessageDelayed(ViewPagerHandlerUtils.MSG_UPDATE, ViewPagerHandlerUtils.MSG_DELAY);
	                        break;
	                }
	            }
	        });
	    }
	
	    @Override
	    public void setCurrentItem(int page) {
	        mViewPager.setCurrentItem(page);
	    }
	
	    private void initHolder(int bannerCount, BannerInterface bannerInterface) {
	        mViewPager = bannerInterface.getViewPager();
	        mLinearLayout = bannerInterface.getLinearLayout();
	        mViewPager.setAdapter(bannerInterface.getBannerAdapter());
	        initRound(bannerCount);
	    }
	
	    private void initRound(int bannerCount) {
	        if (null != mLinearLayout) {
	            mLinearLayout.removeAllViews();
	            for (int i = 0; i < bannerCount; i++) {
	                View view = new View(mLinearLayout.getContext());
	                view.setBackgroundResource(R.drawable.point_background);
	                if (i == 0) {
	                    view.setEnabled(true);
	                } else {
	                    view.setEnabled(false);
	                }
	                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
	                view.setLayoutParams(params);
	                params.leftMargin = 10;
	                mLinearLayout.addView(view);
	            }
	        }
	    }
	
	}


## Handler去实现轮播的效果

	public class ViewPagerHandlerUtils extends Handler {
	
	    public static final int MSG_START = 0; //开始轮播
	    public static final int MSG_UPDATE = 1; //更新
	    public static final int MSG_KEEP = 2; //暂停
	    public static final int MSG_BREAK = 3; // 恢复
	    public static final int MSG_PAGE = 4; //最新的page
	    public static final long MSG_DELAY = 2000; //time
	    private ViewPagerCurrent mCurrent;
	    private int page = 0;
	
	    public boolean isStart() {
	        return isStart;
	    }
	
	    public void setStart(boolean start) {
	        isStart = start;
	    }
	
	    private boolean isStart = true;
	
	    public ViewPagerHandlerUtils(ViewPagerCurrent viewPager, int currentItem) {
	        this.page = currentItem;
	        this.mCurrent = viewPager;
	    }
	
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        if (hasMessages(MSG_UPDATE)) {
	            removeMessages(MSG_UPDATE);
	        }
	        if (null == mCurrent) {
	            return;
	        }
	        if (!isStart) {
	            return;
	        }
	        switch (msg.what) {
	            case MSG_START:
	                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
	                break;
	            case MSG_UPDATE:
	                mCurrent.setCurrentItem(++page);
	                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
	                break;
	            case MSG_PAGE:
	                page = msg.arg1;
	                break;
	            case MSG_KEEP:
	                break;
	            case MSG_BREAK:
	                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
	                break;
	            default:
	                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
	                break;
	        }
	    }
	
	    public interface ViewPagerCurrent {
	        void setCurrentItem(int page);
	    }
	}

## 封装完成，在代码中实现

>adapter

	public class PagerAdapter extends BasePagerAdapter<PagerModel> {
	
	    public PagerAdapter(List<PagerModel> mDatas) {
	        super(mDatas);
	    }
	
	    @Override
	    protected int displayImage(int position) {
	        return mDatas.get(position).getImageId();
	    }
	
	    @Override
	    protected void onImageClick(int finalPosition, PagerModel mDatas) {
	
	    }
	}

>Activity

	public class MainActivity extends AppCompatActivity implements BannerInterface {
	    private List<PagerModel> mDatas = new ArrayList<>();
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        mDatas.add(new PagerModel(R.drawable.banner1));
	        mDatas.add(new PagerModel(R.drawable.banner2));
	        mDatas.add(new PagerModel(R.drawable.banner3));
	        mDatas.add(new PagerModel(R.drawable.banner4));
	        new BannerHolder().setBanner(mDatas, this);
	    }
	
	    @Override
	    public ViewPager getViewPager() {
	        return (ViewPager) findViewById(R.id.viewPager);
	    }
	
	    @Override
	    public LinearLayout getLinearLayout() {
	        return (LinearLayout) findViewById(R.id.ll_points);
	    }
	
	    @Override
	    public BasePagerAdapter getBannerAdapter() {
	        return new PagerAdapter(mDatas);
	    }
	
	}
	

至此封装Banner完成，而且在项目中没有出现问题。以后使用起来不管是RecyclerView里面还是activity里面，只要几行简单的代码就可以实现Banner的轮播以及小圆点。

最后看下实际效果图
![_config.yml]({{ site.baseurl }}/img/bannerImage.gif)
	
