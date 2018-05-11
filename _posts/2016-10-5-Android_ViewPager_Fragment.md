---
layout:     post
title:      "Android_ViewPager+Fragment滑动且带有光标"
subtitle:   "类似于TabLayout的功能，自己用代码写的一个Demo"
date:       2016-10-5
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - fragment
---


## 代码地址

[ViewPagerFragment](https://github.com/7449/AndroidDevelop/tree/develop/fragment-viewpager)

## 代码

	public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
	    private int ivWidth;
	    private ViewPager viewPager;
	    private ImageView imageView;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        viewPager = (ViewPager) findViewById(R.id.viewpager);
	        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_group);
	        imageView = (ImageView) findViewById(R.id.iv_line);
	
	
	        Display display = getWindow().getWindowManager().getDefaultDisplay();
	        DisplayMetrics dm = new DisplayMetrics();
	        display.getMetrics(dm);
	        ivWidth = (dm.widthPixels / 3);
	        viewPager.addOnPageChangeListener(this);
	        radioGroup.setOnCheckedChangeListener(this);
	        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
	
	            @Override
	            public Fragment getItem(int position) {
	                return ViewPagerFragment.startFragment(position);
	            }
	
	            @Override
	            public int getCount() {
	                return 3;
	            }
	        });
	    }
	
	    @Override
	    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
	        lp.leftMargin = position * ivWidth + (int) (positionOffset * ivWidth);
	        lp.width = ivWidth;
	        imageView.setLayoutParams(lp);
	    }
	
	    @Override
	    public void onPageSelected(int position) {
	
	    }
	
	    @Override
	    public void onPageScrollStateChanged(int state) {
	
	    }
	
	    @Override
	    public void onCheckedChanged(RadioGroup radioGroup, int i) {
	        switch (i) {
	            case R.id.rb_one:
	                viewPager.setCurrentItem(0);
	                break;
	            case R.id.rb_two:
	                viewPager.setCurrentItem(1);
	                break;
	            default:
	                viewPager.setCurrentItem(2);
	                break;
	        }
	    }
	}

>由一个`Fragment`构成的测试页面


	public class ViewPagerFragment extends Fragment {
	
	
	    private TextView textView;
	    private int index;
	
	    public static ViewPagerFragment startFragment(int position) {
	        ViewPagerFragment fragment = new ViewPagerFragment();
	        Bundle bundle = new Bundle();
	        bundle.putInt("index", position);
	        fragment.setArguments(bundle);
	        return fragment;
	    }
	
	    @Override
	    public void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Bundle arguments = getArguments();
	        if (null != arguments) {
	            index = arguments.getInt("index");
	        }
	    }
	
	    @Nullable
	    @Override
	    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	        View inflate = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_viewpager, null);
	        textView = (TextView) inflate.findViewById(R.id.tv);
	        return inflate;
	    }
	
	    @Override
	    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        textView.setText(String.valueOf(index));
	    }
	}

## 效果图

![_config.yml]({{ site.baseurl }}/img/viewpagerFragment.gif)