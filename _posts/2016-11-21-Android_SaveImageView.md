---
layout:     post
title:      "Android_保存ImageView至本地"
subtitle:   "图片保存在本地"
date:       2016-11-21
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: false
tags:
    - ImageView
    - RxJava
    - android
---



## 示例代码：

[https://github.com/7449/AndroidDevelop/tree/master/SaveImage](https://github.com/7449/AndroidDevelop/tree/master/SaveImage)

>本身没有什么难点，关键是在国内某些机型上面直接获取bitmap会为null,这个时候就要处理一下兼容某些机型了


	public class MainActivity extends AppCompatActivity {
	
	    private ImageView imageView;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        imageView = (ImageView) findViewById(R.id.imageview);
	        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                saveImageView(getViewBitmap(imageView));
	            }
	        });
	    }
	
	    private class SaveObservable implements Observable.OnSubscribe<String> {
	
	        private Bitmap drawingCache = null;
	
	        public SaveObservable(Bitmap drawingCache) {
	            this.drawingCache = drawingCache;
	        }
	
	        @Override
	        public void call(Subscriber<? super String> subscriber) {
	            if (drawingCache == null) {
	                subscriber.onError(new NullPointerException("imageview的bitmap获取为null,请确认imageview显示图片了"));
	            } else {
	                try {
	                    File imageFile = new File(Environment.getExternalStorageDirectory(), "saveImageview.jpg");
	                    FileOutputStream outStream;
	                    outStream = new FileOutputStream(imageFile);
	                    drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
	                    subscriber.onNext(Environment.getExternalStorageDirectory().getPath());
	                    subscriber.onCompleted();
	                    outStream.flush();
	                    outStream.close();
	                } catch (IOException e) {
	                    subscriber.onError(e);
	                }
	            }
	        }
	    }
	
	    private class SaveSubscriber extends Subscriber<String> {
	
	        @Override
	        public void onCompleted() {
	            Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
	        }
	
	        @Override
	        public void onError(Throwable e) {
	            Log.i(getClass().getSimpleName(), e.toString());
	            Toast.makeText(getApplicationContext(), "保存失败——> " + e.toString(), Toast.LENGTH_SHORT).show();
	        }
	
	        @Override
	        public void onNext(String s) {
	            Toast.makeText(getApplicationContext(), "保存路径为：-->  " + s, Toast.LENGTH_SHORT).show();
	        }
	    }
	
	
	    private void saveImageView(Bitmap drawingCache) {
	        Observable.create(new SaveObservable(drawingCache))
	                .subscribeOn(Schedulers.io())
	                .observeOn(AndroidSchedulers.mainThread())
	                .subscribe(new SaveSubscriber());
	    }
	
	    /**
	     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
	     */
	    private Bitmap getViewBitmap(View view) {
	        if (view == null) {
	            return null;
	        }
	        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(bitmap);
	        view.draw(canvas);
	        return bitmap;
	    }
	}
