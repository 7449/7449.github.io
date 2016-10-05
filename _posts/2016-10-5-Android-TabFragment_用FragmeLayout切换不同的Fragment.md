---
layout: post
title: Hello Github
---

viewpager+fragment也可以实现切换fragment，但是这次使用的是一个FrameLayout去切换不同的fragment

代码地址：[https://github.com/7449/AndroidDevelop/tree/master/TabFragment](https://github.com/7449/AndroidDevelop/tree/master/TabFragment)

市面上很多app都是底部几个Tab然后点击切换页面，ViewPager和Fragment也可以实现，但是这里使用的是一个FrageLayou切换不同的Fragment.
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

横竖屏切换的时候还是会出现页面重叠的问题，解决办法是AndroidManifest.xml文件下的对应的activity添加android:configChanges = "orientation|screenSize"这个属性，并在在fragment里面重写onConfigurationChanged这个方法，里面什么都不操作。就可以避免横竖屏切换时的界面重叠问题

![_config.yml]({{ site.baseurl }}/images/TabFragment.gif)

