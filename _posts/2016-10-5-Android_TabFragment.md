---
layout:     post
title:      "Android_TabFragment_用FragmeLayout切换不同的Fragment"
subtitle:   "viewpager+fragment也可以实现切换fragment，但是这次使用的是一个FrameLayout去切换不同的fragment"
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

[TabFragment](https://github.com/7449/AndroidDevelop/tree/master/tabFragment)

## 代码

>市面上很多`app`都是底部几个`Tab`然后点击切换页面，`ViewPager`和`Fragment`也可以实现，
但是这里使用的是一个`FrageLayou`切换不同的`Fragment`.
博客只给出关键的代码，其余的代码请在上面的链接里查看

	public class MainActivity extends AppCompatActivity {
	
	    private Fragment fragmentOne, fragmentTwo;
	
	    private static final String FRAGMENT_ONE = "one";
	    private static final String FRAGMENT_TWO = "two";
	
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        setTabSelect(0);
	        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_group);
	        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	            @Override
	            public void onCheckedChanged(RadioGroup radioGroup, int i) {
	                switch (i) {
	                    case R.id.rb_one:
	                        setTabSelect(0);
	                        break;
	                    default:
	                        setTabSelect(1);
	                        break;
	                }
	            }
	        });
	    }
	
	    private void setTabSelect(int i) {
	        FragmentManager manager = getSupportFragmentManager();
	        FragmentTransaction transaction = manager.beginTransaction();
	        switch (i) {
	            case 0:
	                fragmentOne = manager.findFragmentByTag(FRAGMENT_ONE);
	                hideTab(transaction);
	                if (null == fragmentOne) {
	                    fragmentOne = FragmentOne.startFragment();
	                    transaction.add(R.id.fragment, fragmentOne, FRAGMENT_ONE);
	                } else {
	                    transaction.show(fragmentOne);
	                }
	                break;
	            case 1:
	                fragmentTwo = manager.findFragmentByTag(FRAGMENT_TWO);
	                hideTab(transaction);
	                if (null == fragmentTwo) {
	                    fragmentTwo = FragmentTwo.startFragment();
	                    transaction.add(R.id.fragment, fragmentTwo, FRAGMENT_TWO);
	                } else {
	                    transaction.show(fragmentTwo);
	                }
	                break;
	        }
	
	        transaction.commit();
	    }
	
	
	    private void hideTab(FragmentTransaction transaction) {
	        if (null != fragmentOne) {
	            transaction.hide(fragmentOne);
	        }
	        if (null != fragmentTwo) {
	            transaction.hide(fragmentTwo);
	        }
	    }
	
	}

## 效果图
![_config.yml]({{ site.baseurl }}/img/TabFragment.gif)

