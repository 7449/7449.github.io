---
layout:     post
title:      "Android_DownloadProgressBar"
subtitle:   "自定义View实现下载时进度的显示"
date:       2016-10-7
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - 自定义View
    - android
---


## 代码地址：

[ProgressView](https://github.com/7449/ProgressView)

这次带来的是通过自定义`view`去实现下载时圆环进度以及进度数的一个`progressView`.
<br>

## 自定义控件简单说明：

自定义控件的实现方式大概有三种，组合控件，继承控件和自绘控件

组合控件：使用时只用系统原生的各个控件就行了,例如实际开发中经常会自己定义一个`titlebar`，需要一个返回键和`title`，这样的就是组合控件

继承控件：继承系统的原生控件，在他的基础上再添加一些新功能，例如登陆时的验证码60秒倒计时，我们就可以去自定义一个`button`去实现这个功能

自绘控件：页面上展示的东西都是通过代码自己去绘制出来的




## 开始

其余的倒不用管，想要绘制圆圈必须在`View`的`onDraw()`;方法中。

先去继承一个`View`，然后实现它的构造方法

	public class ProgressBarView extends View {  
	  
	    public ProgressBarView(Context context) {  
	        super(context);  
	    }  
	  
	    public ProgressBarView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        init(context, attrs);  
	    }  
	  
	    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {  
	        super(context, attrs, defStyleAttr);  
	        init(context, attrs);  
	    }  
	}

然后再创建一个`attrs.xml`文件，用来初始化一些变量的,例如进度初始化的颜色,字体颜色,字体大小之类的,而且可以直接在`xml`中引用

	<declare-styleable name="CirclesProgressBar">  
	  
	       <attr name="circlesWidth" format="dimension" />  
	       <attr name="circlesColor" format="color" />  
	       <attr name="textCrude" format="dimension" />  
	       <attr name="textColor" format="color" />  
	       <attr name="textSize" format="dimension" />  
	       <attr name="currentProgressColor" format="color" />  
	       <attr name="currentProgress" format="integer" />  
	       <attr name="isPercent" format="boolean" />  
	       <attr name="style" format="integer" />  
	       <attr name="currentScheduleWidth" format="dimension" />  
	</declare-styleable>  

	private void init(Context context, AttributeSet attrs) {  

	    mPaint = new Paint();  
	    mPaint.setAntiAlias(true);  
	    TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclesProgressBar);  

	    //初始化圆环变量  
	    circlesWidth = mTypedArray.getDimension(R.styleable.CirclesProgressBar_circlesWidth, ProgressDefaults.CIRCLES_WIDTH);  
	    circlesColor = mTypedArray.getColor(R.styleable.CirclesProgressBar_circlesColor, ProgressDefaults.CIRCLES_COLOR);  
	    textCrude = mTypedArray.getDimension(R.styleable.CirclesProgressBar_textCrude, ProgressDefaults.TEXT_CRUDE);  
	    textColor = mTypedArray.getColor(R.styleable.CirclesProgressBar_textColor, ProgressDefaults.TEXT_COLOR);  
	    textSize = mTypedArray.getDimension(R.styleable.CirclesProgressBar_textSize, ProgressDefaults.TEXT_SIZE);  
	    currentProgress = mTypedArray.getInt(R.styleable.CirclesProgressBar_currentProgress, ProgressDefaults.CURRENT_PROGRESS);  
	    currentProgressColor = mTypedArray.getColor(R.styleable.CirclesProgressBar_currentProgressColor, ProgressDefaults.CURRENT_PROGRESS_COLOR);  
	    isPercent = mTypedArray.getBoolean(R.styleable.CirclesProgressBar_isPercent, ProgressDefaults.IS_PERCENT);  
	    style = mTypedArray.getInt(R.styleable.CirclesProgressBar_style, ProgressDefaults.STYLE);  
	    currentScheduleWidth = mTypedArray.getDimension(R.styleable.CirclesProgressBar_currentScheduleWidth, ProgressDefaults.CURRENT_SCHEDULE_WIDTH);  
	  
	    mTypedArray.recycle();  
	}  

然后重写的`onDraw()`;方法去绘制我们需要的圆环以及进度数据
	
		@Override  
	    protected void onDraw(Canvas canvas) {  
	        super.onDraw(canvas);  
	        int anInt = getWidth() / 2;  
	        int circlesRadius = (int) (anInt - circlesWidth / 2);//半径  
	  
	  
	        //圆环  
	        mPaint.setColor(circlesColor);  
	        mPaint.setStyle(Paint.Style.STROKE);  
	        mPaint.setStrokeWidth(circlesWidth);  
	        canvas.drawCircle(anInt, anInt, circlesRadius, mPaint);  
	  
	        //百分比  
	        if (isPercent && style == STROKE) {  
	            mPaint.setStrokeWidth(textCrude);  
	            mPaint.setColor(textColor);  
	            mPaint.setTextSize(textSize);  
	            mPaint.setTypeface(mTypeface);  
	            int percent = (int) (((float) currentProgress / (float) ProgressDefaults.PROGRESS_BAR_MAX) * 100);  
	            float textWidth = mPaint.measureText(percent + "%");  
	            canvas.drawText(percent + "%", anInt - textWidth / 2, anInt + textSize / 2, mPaint);  
	        }  
	  
	  
	        //进度的圆环  
	        mPaint.setColor(currentProgressColor);  
	        mPaint.setStrokeWidth(currentScheduleWidth);  
	        RectF rectF = new RectF(anInt - circlesRadius, anInt - circlesRadius, anInt + circlesRadius, anInt + circlesRadius);  
	  
	        //选择风格  
	        switch (style) {  
	  
	            case STROKE:  
	                if (currentProgress != 0) {  
	                    mPaint.setStyle(Paint.Style.STROKE);  
	                    canvas.drawArc(rectF, 0, 360 * currentProgress / ProgressDefaults.PROGRESS_BAR_MAX, false, mPaint);  
	                }  
	                break;  
	            case FILL:  
	                if (currentProgress != 0) {  
	                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);  
	                    canvas.drawArc(rectF, 0, 360 * currentProgress / ProgressDefaults.PROGRESS_BAR_MAX, true, mPaint);  
	                }  
	                break;  
	        }  
	        Log.i("onDraw---->", "onDraw current");  
	    }  

然后为了能在代码中方便的改变一些数据还需要一些set，get方法。这里就不再叙述了。

![_config.yml]({{ site.baseurl }}/img/downloadProgress01.gif)
![_config.yml]({{ site.baseurl }}/img/downloadProgress02.gif)
![_config.yml]({{ site.baseurl }}/img/downloadProgress03.gif)