---
layout:     post
title:      "Android_Toolbar_Menu动态替换图片或者显示文字"
subtitle:   "Toolbar Menu动态替换图片或者显示文字"
date:       2017-4-19
tags:
    - toolbar
---

# 动态替换图片


如果做收藏功能的话,`Toolbar`的`Menu`是个不错的提示，如下图所示：

![_config.yml]({{ site.baseurl }}/assets/screenshot/17/toolbar_menu.gif)

在`ViewPager`切换的时候可以调用`invalidateOptionsMenu()`动态显示是否收藏过了

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                invalidateOptionsMenu(); //重新绘制ToolBar
            }
        });

如上图所示,如果没有收藏过,那么就显示空心,否则显示实心

最初是写的状态选择器,但是发现在 `Toolbar` 上 `MenuItem` 的状态选择器只有在`Enabled`状态下才会生效，但是这个明显不是最好的结果,因为`Toolbar`的`Item`是需要点击收藏功能的,
当`Enabled`为`false`的时候是不能触摸的

	<selector xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:drawable="@drawable/ic_favorite_border_24dp" android:state_enabled="false" />
	    <item android:drawable="@drawable/ic_favorite_24dp" android:state_enabled="true" />
	</selector>

状态选择器改为`Checked`时：

	<selector xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:drawable="@drawable/ic_favorite_border_24dp"
	        android:state_checked="false" />
	    <item android:drawable="@drawable/ic_favorite_24dp"
	        android:state_checked="true" />
	</selector>

则不会生效，没有任何反应,`MenuItem`的图标为初始化时的图标,无法改变

最后通过`google`，在[stackoverflow](http://stackoverflow.com/questions/6683186/menuitems-checked-state-is-not-shown-correctly-by-its-icon)上找到了答案：

android官网说明：[ui/menus](http://developer.android.com/guide/topics/ui/menus.html)

Note: Menu items in the Icon Menu (from the Options Menu) cannot display a checkbox or radio button. If you choose to make items in the Icon Menu checkable, you must manually indicate the checked state by swapping the icon and/or text each time the state changes.

大致意思就是如果要动态改变`MenuItem`的图标就必须手动在代码中去更换图标

其实这里用`onPrepareOptionsMenu`也可以,为了方便就没有改,直接用的`onCreateOptionsMenu`

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu, menu);
        MenuItem item = menu.findItem(R.id.collection);
        item.setIcon(true ? R.drawable.ic_favorite_border_24dp : R.drawable.ic_favorite_24dp);
        return super.onCreateOptionsMenu(menu);
    }

这样的效果就如上图所示,可以动态的修改`MenuItem`的图标，提供比较好的体验

# 显示文字

    <item
        android:id="@+id/menu_item"
        android:title=""
        app:actionLayout="@layout/toolbar_text"
        app:showAsAction="always" />

`actionLayout`指向自己的`Layout`

	<android.support.v7.widget.AppCompatTextView xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="wrap_content"
	    android:layout_height="wrap_content"
	    android:background="?android:selectableItemBackground"
	    android:drawableLeft="@mipmap/ic_launcher"
	    android:drawablePadding="10dp"
	    android:gravity="center"
	    android:padding="10dp"
	    android:text="@string/text"
	    tools:ignore="RtlHardcoded" />

点击事件的处理：

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	//          onOptionsItemSelected(item);
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


![_config.yml]({{ site.baseurl }}/assets/screenshot/17/toolbar_text.gif)

# 修改Toolbar上面的三个点

代码中设置： setOverflowIcon();

写一个`Style` 继承`@android:style/Widget.ActionButton.Overflow`

    <style name="toolbar_..." parent="@android:style/Widget.ActionButton.Overflow">
        <item name="android:src">@mipmap/ic_launcher</item>
    </style>

然后在自己的主题

加进去

	<item name="actionOverflowButtonStyle">@style/toolbar_...</item>


![_config.yml]({{ site.baseurl }}/assets/screenshot/17/toolbar_icon.gif)
