---
layout:     post
title:      "Android面试总结--android部分"
subtitle:   "Android面试总结--android部分"
date:       2018-10-11
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
---

## 内存泄漏是怎么产生的,如何避免？

1. 资源对象没关闭。

    如`Cursor`、`File`等资源，他们会在`finalize`中关闭，但这样效率太低，容易造成内存泄露,`SQLiteCursor`当数据量大的时候容易泄露

2. 使用`Adapter`时，没有使用`ViewHolder`
 
3. 即时调用`recycle()`释放不再使用的`Bitmap`,适当降低`Bitmap`的采样率，如：

        BitmapFactory.Options options = newBitmapFactory.Options();    
        options.inSampleSize = 2; 
        Bitmap bitmap =BitmapFactory.decodeStream(cr.openInputStream(uri), null, options); 
        preview.setImageBitmap(bitmap);

4. 使用`application`的`context`来替代`activity`相关的`context`

5. 注册没取消造成内存泄露

6. 集合中的对象没清理造成的内存泄露我们通常把一些对象的引用加入到了集合中，
当我们不需要该对象时，并没有把它的引用从集合中清理掉，这样这个集合就会越来越大，如果这个集合是`static`的话，那情况就更严重了。

7. `Handler`应该申明为静态对象， 并在其内部类中保存一个对外部类的弱引用。 

#### Bitmap有没有必要使用`recycle()`

在`Android 2.3.3`以前，像素数据与对象本身分开存储，像素数据存储在`native`层,对象存储在`java`层;
显然，像素数据才是内存占用的大头,并且，像素数据什么时候回收是没有保证的,这时也是最容易触发 OOM 的时候
但是，在`Android 3.0`以后，像素数据与`Bitmap`对象数据一起关联存储在`Dalvik`堆中，所以，这个时候，就可以考虑用GC来自动回收`Bitmap`的内存了

如果调用也可以,但需要注意的是,这个方法是不可逆的,调用之后不能再操作该`Bitmap`

## OOM

> OOM`不可以`try`,因为是ERROR

`Bitmap`的`Options`有个属性`inJustDecoedBounds`，设置`true`的话，只会返回图片的信息而不会加载在内存中,这点可以避免`OOM`

