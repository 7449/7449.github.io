---
layout: post
title: Android_首页banner封装
---
由于开发的app首页的好几个界面都有banner，结合相同的特点，把banner代码封装一下。以最少的代码实现banner的轮播以及小圆点

本来代码是用于RecyclerView的ViewHolder的，这里我稍微改了下。让其适用任何地方。

代码地址：[https://github.com/7449/AndroidDevelop/tree/master/Banner](https://github.com/7449/AndroidDevelop/tree/master/Banner "Android Banner的封装")
	
首先设置一个接口得到想要的数据

	public interface BannerInterface<T> {
	
	    Context getContext();
	
	    ViewPager getViewPager();
	
	    LinearLayout getLinearLayout();
	
	    BasePagerAdapter getBannerAdapter(List<T> banner);
	}

封装PagerAdapter，这里对外提供两个方法，一个点击事件，一个获取数据的方法

	public abstract class BasePagerAdapter<T> extends PagerAdapter {
	
	    public List<T> mDatas = null;
	
	    public BasePagerAdapter(List<T> mDatas) {
	        this.mDatas = mDatas;
	    }
	
	    @Override
	    public int getCount() {
	        //数字无限大，如果用户真的能滑到最后一张 面对这样的人  认怂，怼不过
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

接着封装最重要的BannerHolder,这里面实现了轮播以及小圆点，因为主要的控件以及数据已经通过接口取到了，所以这里只需要实现逻辑即可，这个类是通用的。

	public class BannerHolder implements ViewPagerHandlerUtils.ViewPagerCurrent {

	    private int preEnablePosition = 0;
	    private ViewPager mViewPager;
	    private ViewPagerHandlerUtils mHandlerUtil;
	    private LinearLayout mLinearLayout;
	
	    public <T> void setBanner(final List<T> banner, BannerInterface<T> bannerInterface) {
	        //初始化viewpager 小圆点 adapter
	        initHolder(banner, bannerInterface);
	
	        //这里给一个无限大的正确的数字 这样viewpager刚开始就可以向右滑动
	        mViewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % banner.size()));
	
	        //初始化轮播HandlerUtils
	        mHandlerUtil = new ViewPagerHandlerUtils(this, mViewPager.getCurrentItem());

	        //刚开始发送一个开始轮播的消息 这里可以判断图片的数量 如果是一个 就不轮播
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
	
	    private <T> void initHolder(List<T> banner, BannerInterface<T> bannerInterface) {
	        mViewPager = bannerInterface.getViewPager();
	        mLinearLayout = bannerInterface.getLinearLayout();
	        BasePagerAdapter adapter = bannerInterface.getBannerAdapter(banner);
	        initRound(bannerInterface.getContext(), banner.size());
	        mViewPager.setAdapter(adapter);
	    }
	
	    private void initRound(Context context, int bannerCount) {
	        if (null != mLinearLayout) {
	            mLinearLayout.removeAllViews();
	            for (int i = 0; i < bannerCount; i++) {
	                View view = new View(context);
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

还有一个Handler去实现轮播的效果

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

封装已经完成，再来看看在代码中如何去实现
先看Adapter,只需要两个方法了。

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

再接着看Activity里面是如何实现的

	public class MainActivity extends AppCompatActivity implements BannerInterface<PagerModel> {
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
	    public Context getContext() {
	        return getApplicationContext();
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
	    public BasePagerAdapter getBannerAdapter(List<PagerModel> banner) {
	        return new PagerAdapter(banner);
	    }
	}

至此封装Banner完成，而且在项目中没有出现问题。以后使用起来不管是RecyclerView里面还是activity里面，只要几行简单的代码就可以实现Banner的轮播以及小圆点。

最后看下实际效果图
![_config.yml]({{ site.baseurl }}/images/bannerImage.gif)
	
