---
layout: post
title: Fragment懒加载
---

本篇文章解决ViewPager和Fragment一起使用时ViewPager的预加载问题

代码地址：[https://github.com/7449/AndroidDevelop/tree/master/LazyFragment](https://github.com/7449/AndroidDevelop/tree/master/LazyFragment)

在项目中使用Fragment和viewPager是很常见的事情,但是细心的人就会发现如果fragment有多个页面的话,当你还没切换到第二个页面的时候viewpager就已经把第二个页面在后台加载了,这个是viewpager的特性。

举个简单的例子：你在页面加个progressBar,让他显示一秒再消失,但是你打开App,然后等两秒,再切换到第二个页面,这个时候就会发现progressbar早已经消失了。

阅读viewpager的源码就会发现有这个方法setOf'fscreenPageLimit();

这个就是控制viewpager一次加载几个页面的方法,看原源码就会发现就算你传0,viewpager也会默认为1,通过修改viewpager可以实现不预加载,但是以后SDK更新的话你就用不到新东西,这个方法明显是不理想的.

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

再来看看Fragment的API,setUserVisibleHint这个方法告诉我们Fragment的UI是否是可见的。

很明显从这里下手可以得到想要的结果。

创建一个LazyFragment,只要不想预加载的Fragment只要继承这个LazyFragment就可以了

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

继承的Fragment

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


然后基于这个写个ViewPager和Fragment的Demo测试下，最后发现是没有任何问题的。
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

Fragment:
	
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

![_config.yml]({{ site.baseurl }}/images/lazyFragment.gif)
	