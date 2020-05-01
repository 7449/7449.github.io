---
layout:     post
title:      "过时的OnActivityResult替代品-registerForActivityResult源码解析"
subtitle:   ""
date:       2020-5-1
tags:
    - android
    - activity
    - fragment
    - result
---

# 序

时隔多年`google`终于开始对`onActivityResult`下手了,在`FragmentV1.3.0-alpha03`版本中新加入了`prepareCall`方法,

该方法是`startActivityForResult`的替代品

作者使用的版本是`1.3.0-alpha04`,`google`已经在该版本中把`startActivityForResult()` `onActivityResult()` `requestPermissions()` `onRequestPermissionsResult()`

标记为过时,并把`prepareCall`重命名为`registerForActivityResult`

该方法目前在`alpha`版本中,所以`api`可能会随着版本变化而改变,本文以`1.3.0-alpha04`为基础

本文是针对新版本的`registerForActivityResult`,所以不会再讲之前的`onActivityResult`的使用方法,并且代码以`kotlin`为主

## 功能

该功能新加的类基本都在`androidx.activity:activity:version`中，其中有一个名为`ComponentActivity`的`Activity`用于分发事件,

如果你查看`androidx.fragment:fragment:version`就会发现`FragmentActivity`已经依赖了`androidx.activity.ComponentActivity`

而不是`androidx.core.app.ComponentActivity`,该`Activity`是实现新回调的一个基类,不论是`activity`或者`fragment`调用`registerForActivityResult`的时候

最终都是在这个类中去处理的，

![_config.yml]({{ site.baseurl }}/assets/screenshot/20/new_activity_result_class.png)

具体新加的与回调相关的类如上图所示,其中有几个暂时不相关的类,但不影响观看

#### ActivityResultContract 

   该类为基类,需要两个泛型,一个为出参，一个为入参，在使用过程中一般只需要关注实现`createIntent`和`parseResult`这两个方法即可,顾名思义
   
   `createIntent`提供跳转需要的`intent`,而`parseResult`提供返回的数据

#### ActivityResultContracts 

   该类为`ActivityResultContract`的实现类,其中有几个已经实现好了的`ActivityResultContract`,这里挑几个介绍一下
   
   `StartActivityForResult`         : 用于跳转`Activity`,其中涉及到了`ActivityResult`,代码比较简单,有兴趣的可以看看
   
   `StartIntentSenderForResult`     : `google`支付，这里不多做解释

   `RequestMultiplePermissions`     : 多个权限请求

   `RequestPermission`              : 单个权限请求

   `TakePicturePreview`             : 拍照预览

   `TakePicture`                    : 拍照

   `TakeVideo`                      : 摄像
   
   `PickContact`                    : 选择联系人
   
   `GetContent`                     : 获取各种文件的Uri
   
   `GetMultipleContents`            : 获取多个各种文件的Uri

   `OpenDocument`                   : 打开文件
   
   `OpenMultipleDocuments`          : 打开多个文件
   
   `OpenDocumentTree`               : 打开文件夹
   
   `CreateDocument`                 : 创建文件

#### ActivityResult 

   `StartActivityForResult`需要的一个扩展类,提供了`resultCode`和返回的`Intent`

#### ActivityResultCallback 

   接口,替代`onActivityResult`

#### ActivityResultCaller 

   接口,用于注册`ActivityResult`,返回一个`ActivityResultLauncher`

#### ActivityResultLauncher 

   用于启动相应的`Intent`

#### ActivityResultRegistry 

   事件分发的提供者，具体可看`ComponentActivity`中的`mActivityResultRegistry`

#### ActivityResultRegistryOwner 

   接口，获取一个`ActivityResultRegistry`

#### ComponentActivity 

   `Activity`,实现了`ActivityResultRegistry`

## Activity中使用
    
    class MainActivity : AppCompatActivity(R.layout.activity_main) {
    
        private val startActivityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    //
                } else if (it.resultCode == Activity.RESULT_CANCELED) {
                    //
                }
            }
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            startActivityLauncher.launch(Intent(this, TestActivity::class.java))
        }
    }

## Fragment中使用
    
    class Fragment : Fragment() {
        private val startActivityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    //
                } else if (it.resultCode == Activity.RESULT_CANCELED) {
                    //
                }
            }
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            startActivityLauncher.launch(Intent(requireContext(), TestActivity::class.java))
        }
    }

## 源码解析

先来看一下在`Activity`中启动`launcher`之后是怎么实现这种功能的

调用`ComponentActivity`中的`registerForActivityResult`，该方法返回一个`ActivityResultLauncher`用于启动`Intent`

    public final <I, O> ActivityResultLauncher<I> registerForActivityResult(
            @NonNull final ActivityResultContract<I, O> contract,
            @NonNull final ActivityResultRegistry registry,
            @NonNull final ActivityResultCallback<O> callback) {
        return registry.register(
                "activity_rq#" + mNextLocalRequestCode.getAndIncrement(), this, contract, callback);
    }

由于没有自定义`ActivityResultRegistry`,所以这里使用的是`ComponentActivity`中的`mActivityResultRegistry`,调用了`ActivityResultRegistry`中的`register`

`register`则使用了`lifecycleOwner`实现了自动解绑功能，然后返回了一个`ActivityResultLauncher`,并调用了`invoke`方法

    public final <I, O> ActivityResultLauncher<I> register() {
            // 注册一个requestCode并保存回调,这里的回调就是在onActivityResult拦截的时候取的
            final int requestCode = registerKey(key);
            mKeyToCallback.put(key, new CallbackAndContract<>(callback, contract));
            // 这里最终返回的是我们需要的ActivityResultLauncher，并调用了ComponentActivity中mActivityResultRegistry的invoke
            return new ActivityResultLauncher<I>() {
                @Override
                public void launch(I input, @Nullable ActivityOptionsCompat options) {
                    invoke(requestCode, contract, input, options);
                }
    
                @Override
                public void unregister() {
                    ActivityResultRegistry.this.unregister(key);
                }
            };
    }

这个时候回到`ComponentActivity`的`mActivityResultRegistry`看下源码就知道是怎么一回事了，该方法先判断了`action`是否为权限或者支付，最后调用的`startActivityForResult`

     private ActivityResultRegistry mActivityResultRegistry = new ActivityResultRegistry() {
            @Override
            public <I, O> void invoke() {
                ComponentActivity activity = ComponentActivity.this;
                Intent intent = contract.createIntent(activity, input);
                if (ACTION_REQUEST_PERMISSIONS.equals(intent.getAction())) {
                    // requestPermissions path  权限处理
                } else if (ACTION_INTENT_SENDER_REQUEST.equals(intent.getAction())) {
                    //google支付
                } else {
                    // 启动activity最终调用的是这里
                    ActivityCompat.startActivityForResult(activity, intent, requestCode, options != null ? options.toBundle() : null);
                }
            }
     };

具体流程大概是这个样子，再来看下是如何拦截`onActivityResult`的

`ActivityResultRegistry`拦截了`onActivityResult`,`onRequestPermissionsResult`同理，`dispatchResult`方法判断了是否需要拦截,根据在调用`register`的时候保存的`registerkey`来判断

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //处理onActivityResult如果不是launcher启动的则还是走onActivityResult
        //不过这里已经标记为过时
        if (!mActivityResultRegistry.dispatchResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

然后拿出`mKeyToCallback`中保存对应`requestCode`的回调`ActivityResultCallback`，这个时候调用`ActivityResultCallback`的`onActivityResult`，这样就能拿到对应的数据和状态

    public final boolean dispatchResult(int requestCode, int resultCode, @Nullable Intent data) {
        String key = mRcToKey.get(requestCode);
        if (key == null) {
            //使用的startActivityForResult
            return false;
        }
        doDispatch(key, resultCode, data, mKeyToCallback.get(key));
        return true;
    }
    
    //这里就比较简单了,获取回调之后返回parseResult生成的类数据
    private <O> void doDispatch(String key, int resultCode, @Nullable Intent data, @Nullable CallbackAndContract<O> callbackAndContract) {
        if (callbackAndContract != null && callbackAndContract.mCallback != null) {
            ActivityResultCallback<O> callback = callbackAndContract.mCallback;
            ActivityResultContract<?, O> contract = callbackAndContract.mContract;
            //parseResult返回的什么这里就获取的什么
            callback.onActivityResult(contract.parseResult(resultCode, data));
        } else {
            mPendingResults.putParcelable(key, new ActivityResult(resultCode, data));
        }
    }

`fragment`也是先拿到`mActivityResultRegistry`然后还是走的`Activity`流程

    public final <I, O> ActivityResultLauncher<I> registerForActivityResult(
            @NonNull final ActivityResultContract<I, O> contract,
            @NonNull final ActivityResultCallback<O> callback) {
        return prepareCallInternal(contract, new Function<Void, ActivityResultRegistry>() {
            @Override
            public ActivityResultRegistry apply(Void input) {
                //返回了ComponentActivity的ActivityResultRegistry
                if (mHost instanceof ActivityResultRegistryOwner) {
                    return ((ActivityResultRegistryOwner) mHost).getActivityResultRegistry();
                }
                return requireActivity().getActivityResultRegistry();
            }
        }, callback);
    }

## 自定义ActivityResultContract

原因:在[Album](https://www.github.com/7449/Album)框架替换`api`的过程时，拍照需要处理，自带的不能满足功能，所以需要自定义一个

源码如下:

    class CameraUri(val type: ScanType, val uri: Uri)
    
    class CameraResultContract : ActivityResultContract<CameraUri, Int>() {
        override fun createIntent(context: Context, input: CameraUri): Intent =
                Intent(if (input.type == ScanType.VIDEO) MediaStore.ACTION_VIDEO_CAPTURE else MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT, input.uri)
    
        override fun parseResult(resultCode: Int, intent: Intent?): Int = resultCode
    }
    
自定义兼容了视频还是图片,因为`uri`是需要特别处理所以这里只返回了`resultCode`，至于`uri`更新则由其他类自行管理

## 总结

大体流程走下来感觉还是很不错的，各种回调也比较好处理，`Activity`之间的回调处理起来也比较容易点

`jetpack`更新比较快,各种新功能也层出不穷,类似于`ViewPager2`,`MergeAdapter`这种方便的功能越来越多,针对`Kotlin`也支持了各种`xxx-ktx`,

再加上`miui12`推动的隐私和权限处理,如果能推广开来,则`android`和`ios`的交集则越来越多，整个`android`生态环境也会逐渐好起来，

作者使用`android`的时候最烦的是各种`app`在根目录乱拉的毛病,而`android11`也强制开启了文件沙盒,这表明了欠的债迟早是要还的~~