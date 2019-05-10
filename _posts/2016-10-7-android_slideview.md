---
layout:     post
title:      "Android_SlideView"
subtitle:   "通讯录快速索引"
date:       2016-10-7
tags:
    - 自定义View
    - android
---


## 代码地址：

[SlideView](https://github.com/7449/SlideView)

>代码很简单，就不再多余的解释。github上面有完整的Demo示例。

	public class SlideView extends View {
	
	    private static final String TAG = "SlideView";
	
	    private String[] mark;
	
	    private OnTouchListener listener;
	
	    private Paint mPaint;
	
	
	    private TextView promptBox;
	
	
	    private boolean isTouch = false;
	
	
	    public SlideView(Context context) {
	        super(context);
	        init();
	    }
	
	    public SlideView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        init();
	    }
	
	    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	        init();
	    }
	
	    public void setPromptBox(TextView promptBox) {
	        this.promptBox = promptBox;
	    }
	
	    private void init() {
	//        initPromptBox();
	        mPaint = new Paint();
	        mPaint.setAntiAlias(true);
	        mPaint.setColor(Color.GRAY);
	        mPaint.setTextSize(getResources().getDimension(R.dimen.slideview_text_size));
	        mPaint.setTextAlign(Paint.Align.CENTER);
	        mark = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
	    }
	
	    public void setOnTouchListener(OnTouchListener onTouchListener) {
	        this.listener = onTouchListener;
	    }
	
	//    private void initPromptBox() {
	//        promptBox = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.index_box_layout, null);
	//        promptBox.setTextSize(50);
	//        promptBox.setTextColor(Color.WHITE);
	//        promptBox.setGravity(Gravity.CENTER);
	//        WindowManager.LayoutParams layoutParams =
	//                new WindowManager.LayoutParams(200, 200,
	//                        WindowManager.LayoutParams.TYPE_APPLICATION,
	//                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
	//                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
	//                        PixelFormat.TRANSLUCENT);
	//        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
	//        windowManager.addView(promptBox, layoutParams);
	//    }
	
	
	    @Override
	    protected void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	
	        int textHeight = getHeight() / mark.length;
	
	        for (int i = 0; i < mark.length; i++) {
	            canvas.drawText(mark[i], getWidth() / 2, textHeight + (i * textHeight), mPaint);
	        }
	
	        if (isTouch) {
	            canvas.drawColor(getContext().getResources().getColor(R.color.colorBackgound));
	        }
	
	        Log.i(TAG, "onDraw  -->");
	    }
	
	
	    @Override
	    public boolean dispatchTouchEvent(MotionEvent event) {
	
	        float y = event.getY();
	        int letter = (int) (y / getHeight() * mark.length);
	
	        if (letter < 0 || letter > mark.length - 1) {
	            isTouch = false;
	            invalidate();
	            if (promptBox != null) {
	                promptBox.setVisibility(GONE);
	            }
	            return false;
	        }
	
	        switch (event.getAction()) {
	
	            case MotionEvent.ACTION_MOVE:
	            case MotionEvent.ACTION_DOWN:
	                isTouch = true;
	                touchIndex(letter);
	                Log.i(TAG, "dispatchTouchEvent -- >   " + mark[letter]);
	                break;
	
	            case MotionEvent.ACTION_UP:
	                isTouch = false;
	                if (promptBox != null) {
	                    promptBox.setVisibility(GONE);
	                }
	                break;
	        }
	
	        invalidate();
	        return true;
	    }
	
	    private void touchIndex(int letter) {
	
	        if (letter >= 0 && letter < mark.length) {
	            listener.onTouch(mark[letter]);
	            if (promptBox != null) {
	                promptBox.setVisibility(VISIBLE);
	                promptBox.setText(mark[letter]);
	            }
	        }
	
	    }
	
	
	    public String[] getMark() {
	        return mark;
	    }
	
	    public void setMark(String[] mark) {
	        this.mark = mark;
	    }
	
	    public interface OnTouchListener {
	
	        void onTouch(String letter);
	
	    }
	}


![_config.yml]({{ site.baseurl }}/assets/screenshot/16/slideview.gif)
