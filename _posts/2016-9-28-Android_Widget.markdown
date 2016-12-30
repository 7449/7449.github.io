---
layout:     post
title:      "AndroidWidget"
subtitle:   "Android自定义控件"
date:       2016-9-28
author:     "y"
header-mask: 0.3
header-img: "img/post-bg-2017.jpg"
catalog: true
tags:
    - Android
    - 开发
    - widget
---


## 高度自适应的imageview

	public class MImageView extends ImageView {  
	  
	    public MImageView(Context context) {  
	        super(context);  
	    }  
	  
	    public MImageView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	    }  
	  
	    public MImageView(Context context, AttributeSet attrs, int defStyleAttr) {  
	        super(context, attrs, defStyleAttr);  
	    }  
	  
	    @Override  
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	        Drawable drawable = getDrawable();  
	        if (drawable != null) {  
	            int width = MeasureSpec.getSize(widthMeasureSpec);  
	            int height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());  
	            setMeasuredDimension(width, height);  
	        } else {  
	            super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
	        }  
	    }  
	}  

## RV检测滑动底部

		public class MyRecyclerView extends RecyclerView {
	
	    public enum LAYOUT_MANAGER_TYPE {
	        LINEAR,
	        GRID,
	        STAGGERED_GRID
	    }
	
	    private LAYOUT_MANAGER_TYPE layoutManagerType;
	
	    /**
	     * 最后一个的位置
	     */
	    private int[] lastPositions;
	
	    /**
	     * 最后一个可见的item的位置
	     */
	    private int lastVisibleItemPosition;
	
	    private LoadingData loadingData;
	
	
	    public void setLoadingData(LoadingData loadingData) {
	        this.loadingData = loadingData;
	    }
	
	
	    public MyRecyclerView(Context context) {
	        super(context);
	    }
	
	    public MyRecyclerView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	
	    }
	
	    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }
	
	
	    @Override
	    public void onScrolled(int dx, int dy) {
	        super.onScrolled(dx, dy);
	        RecyclerView.LayoutManager layoutManager = getLayoutManager();
	        if (layoutManagerType == null) {
	            if (layoutManager instanceof GridLayoutManager) {
	                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
	            } else if (layoutManager instanceof LinearLayoutManager) {
	                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
	            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
	                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
	            } else {
	                throw new RuntimeException(
	                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
	            }
	        }
	
	        switch (layoutManagerType) {
	            case LINEAR:
	                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
	                break;
	            case GRID:
	                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
	                break;
	            case STAGGERED_GRID:
	                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
	                if (lastPositions == null) {
	                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
	                }
	                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
	                lastVisibleItemPosition = findMax(lastPositions);
	                break;
	        }
	    }
	
	
	    @Override
	    public void onScrollStateChanged(int state) {
	        super.onScrollStateChanged(state);
	        RecyclerView.LayoutManager layoutManager = getLayoutManager();
	        int visibleItemCount = layoutManager.getChildCount();
	        int totalItemCount = layoutManager.getItemCount();
	        if (visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && loadingData != null) {
	            loadingData.onLoadMore();
	        }
	    }
	
	    private int findMax(int[] lastPositions) {
	        int max = lastPositions[0];
	        for (int value : lastPositions) {
	            if (value > max) {
	                max = value;
	            }
	        }
	        return max;
	    }
	
	    public interface LoadingData {
	        void onLoadMore();
	    }
	}


## 60秒倒计时的Button

	public class TimeButton extends Button {
	
	    public TimeButton(Context context) {
	        super(context);
	    }
	
	    public TimeButton(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	
	    public TimeButton(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }
	
	    public void startButton() {
	        setEnabled(false);
	        new TimeCountUtil(60 * 1000, 1000).start();
	    }
	
	    public class TimeCountUtil extends CountDownTimer {
	
	        public TimeCountUtil(long millisInFuture, long countDownInterval) {
	            super(millisInFuture, countDownInterval);
	        }
	
	        @SuppressLint("NewApi")
	        public void onTick(long millisUntilFinished) {
	            setText("剩余 " + millisUntilFinished / 1000 + " 秒");
	        }
	
	        @SuppressLint("NewApi")
	        public void onFinish() {
	            setText("再次点击");
	            setEnabled(true);
	        }
	
	    }
	}

## 顶部有进度的webView

	public class ProgressWebView extends WebView {
	
	    private ProgressBar progressbar;
	
	    public ProgressWebView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
	        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
	        addView(progressbar);
	        setWebChromeClient(new WebChromeClient());
	    }
	
	    public class WebChromeClient extends android.webkit.WebChromeClient {
	        @Override
	        public void onProgressChanged(WebView view, int newProgress) {
	            if (newProgress == 100) {
	                progressbar.setVisibility(GONE);
	            } else {
	                if (progressbar.getVisibility() == GONE)
	                    progressbar.setVisibility(VISIBLE);
	                progressbar.setProgress(newProgress);
	            }
	            super.onProgressChanged(view, newProgress);
	        }
	
	    }
	
	    @Override
	    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
	        lp.x = l;
	        lp.y = t;
	        progressbar.setLayoutParams(lp);
	        super.onScrollChanged(l, t, oldl, oldt);
	    }
	}

## 可控制滑动的ViewPager

	public class ViewPagerCompat extends ViewPager {  
	  
	    //mViewTouchMode 表示 ViewPager 是否全权控制滑动事件，默认为 false，即不控制  
	    private boolean mViewTouchMode = false;  
	  
	    public ViewPagerCompat(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	    }  
	  
	    public void setViewTouchMode(boolean b) {  
	        if (b && !isFakeDragging()) {  
	            // 全权控制滑动事件  
	            beginFakeDrag();  
	        } else if (!b && isFakeDragging()) {  
	            // 终止控制滑动事件  
	            endFakeDrag();  
	        }  
	        mViewTouchMode = b;  
	    }  
	  
	    /** 
	     * 在 mViewTouchMode 为 true 的时候，ViewPager 不拦截点击事件，点击事件将由子 View 处理 
	     */  
	    @Override  
	    public boolean onInterceptTouchEvent(MotionEvent event) {  
	        if (mViewTouchMode) {  
	            return false;  
	        }  
	        return super.onInterceptTouchEvent(event);  
	    }  
	  
	    @Override  
	    public boolean onTouchEvent(MotionEvent ev) {  
	        try {  
	            return super.onTouchEvent(ev);  
	        } catch (Exception e) {  
	            return false;  
	        }  
	    }  
	  
	    /** 
	     * 在 mViewTouchMode 为 true 或者滑动方向不是左右的时候，ViewPager 将放弃控制点击事件， 
	     * 这样做有利于在 ViewPager 中加入 ListView 等可以滑动的控件，否则两者之间的滑动将会有冲突 
	     */  
	    @Override  
	    public boolean arrowScroll(int direction) {  
	        if (mViewTouchMode) return false;  
	        if (direction != FOCUS_LEFT && direction != FOCUS_RIGHT) return false;  
	        return super.arrowScroll(direction);  
	    }  
	  
	}  