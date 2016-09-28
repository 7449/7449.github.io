---
layout: post
title: Android_Widget
---

包含了用过的几个自定义的Widget

宽度固定，高度自适应的imageview

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

RecyclerView检测是否滑动到底部

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
	            if (layoutManager instanceof LinearLayoutManager) {  
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

60秒倒计时的Button

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

顶部有进度的webView

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