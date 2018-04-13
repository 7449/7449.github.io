---
layout:     post
title:      "Android_Fragment懒加载"
subtitle:   "本篇文章解决ViewPager和Fragment一起使用时ViewPager的预加载问题"
date:       2016-10-15
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - fragment
---



## 代码地址

[LazyFragment](https://github.com/7449/AndroidDevelop/tree/master/lazyFragment)


## ViewPager预加载问题

在项目中使用`Fragment`和`viewPager`是很常见的事情,但是细心的人就会发现如果`fragment`有多个页面的话,当你还没切换到第二个页面的时候`viewpager`就已经把第二个页面在后台加载了,
这个是`viewpager`的特性。

>举个简单的例子


你在页面加个`progressBar`,让他显示一秒再消失,但是你打开`App`,然后等两秒,再切换到第二个页面,这个时候就会发现`progressbar`早已经消失了。

阅读`viewpager`的源码就会发现有这个方法``setOffscreenPageLimit();``

这个就是控制`viewpager`一次加载几个页面的方法<br>
看原源码就会发现就算你传0,`viewpager`也会默认为1<br>
通过修改`viewpager`可以实现不预加载,但是以后SDK更新的话用不到新特性,这个方法明显是不理想的.

	public void setOffscreenPageLimit(int limit) {  
	    if (limit < DEFAULT_OFFSCREEN_PAGES) {  
	        Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to " +  
	                DEFAULT_OFFSCREEN_PAGES);  
	        limit = DEFAULT_OFFSCREEN_PAGES;  
	    }  
	    if (limit != mOffscreenPageLimit) {  
	        mOffscreenPageLimit = limit;  
	        populate();  
	    }  
	}  

## fragment与viewpager的碰撞

再来看看`Fragment`的`API`,`setUserVisibleHint`这个方法告诉我们`Fragment`的`UI`是否是可见的。<br>
这个方法只有在`Fragment`碰到了`ViewPager`才会触发

很明显从这里下手可以得到想要的结果。

## 解决预加载问题

创建一个`LazyFragment`,只要不想预加载的`Fragment`只要继承这个`LazyFragment`就可以了

	public abstract class LazyFragment extends Fragment {
	
	    boolean isPrepared;
	    boolean isLoad;
	    boolean isVisible;
	    View view;
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        if (null == view) {
	            view = initView(savedInstanceState);
	            isPrepared = true;
	        }
	        initById();
	        return view;
	    }
	
	    @Override
	    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        initData();
	    }
	
	
	    @Override
	    public void setUserVisibleHint(boolean isVisibleToUser) {
	        super.setUserVisibleHint(isVisibleToUser);
	        if (getUserVisibleHint()) {
	            isVisible = true;
	            onVisible();
	        } else {
	            isVisible = false;
	            onInvisible();
	        }
	    }
	
	    private void onVisible() {
	        initData();
	    }
	
	
	    private void onInvisible() {
	    }
	
	    protected abstract View initView(Bundle savedInstanceState);
	
	    protected abstract void initById();
	
	    protected abstract void initData();
	
	}

>继承的Fragment

	public class FragmentTest extends LazyFragment {
	    @Override
	    protected View initView(Bundle savedInstanceState) {
	        return null;
	    }
	
	    @Override
	    protected void initById() {
	
	    }
	
	    @Override
	    protected void initData() {
	        if (!isPrepared || !isVisible || isLoad) {
	            return;
	        }
	
	        //这里加载数据
	
	        isLoad = true;
	    }
	}


## 测试 

>然后基于这个写个`ViewPager`和`Fragment`的`Demo`测试下，最后发现是没有任何问题的。

Activity:

	public class MainActivity extends AppCompatActivity {
	
	    private List<String> mData;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
	        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	        mData = new ArrayList<>();
	        mData.add("一");
	        mData.add("二");
	        mData.add("三");
	        mData.add("四");
	        mData.add("五");
	        mData.add("六");
	        mData.add("七");
	        TabNameAdapter tabNameAdapter = new TabNameAdapter(getSupportFragmentManager());
	        viewPager.setAdapter(tabNameAdapter);
	        tabLayout.setupWithViewPager(viewPager);
	    }
	
	    public class TabNameAdapter extends FragmentPagerAdapter {
	
	
	        public TabNameAdapter(FragmentManager fm) {
	            super(fm);
	        }
	
	        @Override
	        public Fragment getItem(int position) {
	            return TestFragment.newInstance(position);
	        }
	
	        @Override
	        public CharSequence getPageTitle(int position) {
	            return mData.get(position);
	        }
	
	        @Override
	        public int getCount() {
	            return mData.size();
	        }
	    }
	}

>Fragment:
	
	public class TestFragment extends LazyFragment {
	
	    private static final String FRAGMENT_INDEX = "fragment_index";
	    private TextView tv;
	    private ProgressBar progressBar;
	    private int index = 0;
	
	    public static TestFragment newInstance(int position) {
	        Bundle bundle = new Bundle();
	        TestFragment fragment = new TestFragment();
	        bundle.putInt(FRAGMENT_INDEX, position);
	        fragment.setArguments(bundle);
	        return fragment;
	    }
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Bundle bundle = getArguments();
	        if (bundle != null) {
	            index = bundle.getInt(FRAGMENT_INDEX);
	        }
	    }
	
	    @Override
	    protected View initView(Bundle savedInstanceState) {
	        return getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_test, null);
	    }
	
	    @Override
	    protected void initById() {
	        tv = (TextView) view.findViewById(R.id.tv);
	        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
	    }
	
	    @Override
	    protected void initData() {
	        if (!isPrepared || !isVisible || isLoad) {
	            return;
	        }
	
	        //这里加载数据
	        progressBar();
	
	        isLoad = true;
	    }
	
	    private void progressBar() {
	        progressBar.setVisibility(View.VISIBLE);
	        new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                progressBar.setVisibility(View.GONE);
	                switchId(index);
	            }
	        }, 1000);
	    }
	
	
	    private void switchId(int id) {
	        switch (id) {
	            case 0:
	                tv.setText("界面一");
	                break;
	            case 1:
	                tv.setText("界面二");
	                break;
	            case 2:
	                tv.setText("界面三");
	                break;
	            case 3:
	                tv.setText("界面四");
	                break;
	            case 4:
	                tv.setText("界面五");
	                break;
	            case 5:
	                tv.setText("界面六");
	                break;
	            case 6:
	                tv.setText("界面七");
	                break;
	        }
	
	    }
	}

![_config.yml]({{ site.baseurl }}/img/lazyFragment.gif)
	