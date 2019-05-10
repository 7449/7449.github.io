---
layout:     post
title:      "转载:禁止RecyclerView滑动"
subtitle:   "禁止RecyclerView滑动"
date:       2018-7-22
tags:
    - android
---


## 转载地址

[https://stackoverflow.com/questions/30531091/how-to-disable-recyclerview-scrolling](https://stackoverflow.com/questions/30531091/how-to-disable-recyclerview-scrolling)


#### 自定义LayoutManager

    public class CustomGridLayoutManager extends LinearLayoutManager {
     private boolean isScrollEnabled = true;
    
     public CustomGridLayoutManager(Context context) {
      super(context);
     }
    
     public void setScrollEnabled(boolean flag) {
      this.isScrollEnabled = flag;
     }
    
     @Override
     public boolean canScrollVertically() {
      //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
      return isScrollEnabled && super.canScrollVertically();
     }
    }


     linearLayoutManager = new LinearLayoutManager(context) {
     @Override
     public boolean canScrollVertically() {
      return false;
     }
    };

### 另一种

>具体还是看原文吧，比较推荐自定义LayoutManager

    recyclerView.setNestedScrollingEnabled(false);
