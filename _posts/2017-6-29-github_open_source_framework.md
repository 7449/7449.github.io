---
layout:     post
title:      "GitHub开源项目收集"
subtitle:   "记录GitHub常用的开源项目"
date:       2017-6-29
author:     "y"
header-mask: 0.3
header-img: "img/github.png"
catalog: true
tags:
    - 工作记录
---


## Tips

* 收集GitHub开源框架

#### 请善用搜索

首先说明，不是针对某个伸手党...***

我经常在某些开发群里面看见这样的提问：
	
		* Android 视频播放用什么好啊？
		* 这个功能要怎么实现啊？ 然后放了一张UI设计图
		* Android 怎么实现直播？
		* 跳转Activity为什么报错啊？
		* 这样的错是为什么啊？我写的没问题（没问题怎么会报错，无奈.jpg）
		* ......

诸多问题，更有甚者就说他怎么怎么弄了但是为什么会报错？不给报错日志，也不贴代码，就算有人想好心的替你解答，难道你让他用意念帮你解决问题吗？

有时候顺手一搜就能找到答案的东西非要去群里面问，完全的舍近求远，`Android`从开始到现在，已经到了一个成熟期，所遇到的大部分错误，问题，网上都能找到解决办法！

群里的人不一定有空，也不一定每次都能帮你解决问题，难道你就在那浪费时间吗？ 

所以，如果你遇到开发困难，我建议是第一时间使用搜索引擎，至于使用什么搜索引擎看个人喜好，我个人比较偏向`google`,并且尽量使用纯英文搜索！

再借用上面一个问题： 
			
			*  Android 某某功能 用什么好啊？ or  某某功能 这个用什么控件能实现？

推荐的一个解决办法：

* 打开[github](https://github.com/)
* 搜索关键词 例如视频就搜`Android video`,ViewPager轮播就搜`Android Banner` or `Android 轮播`

这样很容易就能找到你想要的解决方案

拒绝伸手党，从个人做起！

## Android-JAVA


#### rx

[RxJava](https://github.com/ReactiveX/RxJava):响应式编程~<br>
[RxAndroid](https://github.com/ReactiveX/RxAndroid):RxJava bindings for Android<br>
[RxKotlin](https://github.com/ReactiveX/RxKotlin):RxJava bindings for Kotlin<br>
[RxBinding](https://github.com/JakeWharton/RxBinding):RxJava binding APIs for Android's UI widgets.<br>
[RxVolley](https://github.com/kymjs/RxVolley):RxVolley = Volley + RxJava(RxJava2.0) + OkHttp(OkHttp3)<br>
[RxPermissions](https://github.com/tbruyelle/RxPermissions):使用RxJava请求Android权限<br>
[RxLifecycle](https://github.com/trello/RxLifecycle):防止RxJava泄露<br>
[RxDownload](https://github.com/ssseasonnn/RxDownload):下载<br>
[RxCache](https://github.com/VictorAlbertos/RxCache):缓存<br>
[RxAndroidBle](https://github.com/Polidea/RxAndroidBle):蓝牙<br>
[RxBus](https://github.com/AndroidKnife/RxBus):类似于`EventBus`<br>
[RxRetroJsoup](https://github.com/florent37/RxRetroJsoup):Jsoup网络请求<br>
[RxGalleryFinal](https://github.com/7449/RxGalleryFinal):图片视频选择框架<br>
[RxNetWork](https://github.com/7449/RxNetWork):个人简单封装的一个网络请求框架，里面也实现了RxBus<br>
[Awesome-RxJava](https://github.com/lzyzsd/Awesome-RxJava):RxJava 一些资料收集<br>
[rex-weather](https://github.com/vyshane/rex-weather):`RexWeather`是一个`RxJava`示例的项目，展示了使用`Retrofit`和`RxJava`与Web服务进行交互<br>
[RxJavaSamples](https://github.com/THEONE10211024/RxJavaSamples):收集了RxJava常见的使用场景，例子简洁、经典、易懂...<br>
[RxJava-Android-Samples](https://github.com/kaushikgopal/RxJava-Android-Samples):RxJava在Android上的示例<br>
[RxBlur](https://github.com/SmartDengg/RxBlur):用RxJava处理和操作高斯模糊效果的简单用例<br>


#### 图片加载

[glide](https://github.com/bumptech/glide):google员工出品，太好用了！<br>
[picasso](https://github.com/square/picasso):square开源的一个图片加载框架<br>
[glide-transformations](https://github.com/wasabeef/glide-transformations):为Glide提供各种图像转换<br>
[fresco](https://github.com/facebook/fresco):facebook的图片加载框架<br>
[Fresco-Source-Analysis](https://github.com/desmond1121/Fresco-Source-Analysis):中文的Fresco源码解读<br>

#### 数据库

[greenDAO](https://github.com/greenrobot/greenDAO): android 数据库操作<br>
[wcdb](https://github.com/Tencent/wcdb):微信数据库<br>
[realm-java](https://github.com/realm/realm-java):REALM 数据库<br>
[objectbox-java](https://github.com/objectbox/objectbox-java):ObjectBox<br>


#### widget

[material-dialogs](https://github.com/afollestad/material-dialogs):Material Design 风格的Dialog，推荐<br>
[MaterialDialog](https://github.com/drakeet/MaterialDialog):Material Design 风格的Dialog，兼容 2.2 - L<br>
[android-adDialog](https://github.com/yipianfengye/android-adDialog):一个简单，强大的广告活动弹窗控件<br>
[SwipeRecyclerView](https://github.com/yanzhenjie/SwipeRecyclerView):RecyclerView侧滑菜单，Item拖拽，滑动删除Item，自动加载更多<br>
[ijkplayer](https://github.com/Bilibili/ijkplayer):BiliBili开源的一款视频播放器<br>
[DanmakuFlameMaster](https://github.com/Bilibili/DanmakuFlameMaster):B站弹幕引擎<br>
[CircleImageView](https://github.com/hdodenhof/CircleImageView):扩展ImageView，圆形头像...<br>
[PhotoView](https://github.com/chrisbanes/PhotoView):轻松放大 ImageView，类似的还有 TouchImageView,有兴趣的可以自行搜索下，这里就不贴了<br>

[AndroidVideoPlayer](https://github.com/xiongwei-git/AndroidVideoPlayer):视频播放器<br>
[JieCaoVideoPlayer](https://github.com/lipangit/JieCaoVideoPlayer):节操视频播放器，使用比较简单<br>
[AndroidYouTubePlayer](https://github.com/PierfrancescoSoffritti/AndroidYouTubePlayer):基于WebView的YouTube播放器<br>

[materialish-progress](https://github.com/pnikosis/materialish-progress):Material Design 风格的Progress ，兼容到2.3<br>
[TSnackBar](https://github.com/AndreiD/TSnackBar):扩展的SnackBar，可从任意位置吐出<br>
[android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh):下拉刷新控件，兼容任何View<br>
[SwitchButton](https://github.com/kyleduo/SwitchButton):很强大的选择控件<br>
[ExpandableTextView](https://github.com/Manabu-GT/ExpandableTextView):点击加载全部的TextView<br>
[XCL-Charts](https://github.com/xcltapestry/XCL-Charts):Android图表库，支持较多<br>
[BubbleSeekBar](https://github.com/woxingxiao/BubbleSeekBar):自定义SeekBar，进度变化由可视化气泡样式呈现<br>
[RichText](https://github.com/zzhoujay/RichText):Android平台下的富文本解析器，支持Html和Markdown<br>
[ExoPlayer](https://github.com/google/ExoPlayer):google开源的Android视频播放器<br>
[MagicCamera](https://github.com/wuhaoyu1990/MagicCamera):包含美颜等40余种实时滤镜相机，可拍照、录像、图片修改<br>
[Timeline-View](https://github.com/vipulasri/Timeline-View):时间线展示View<br>
[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper):RecyclerViewAdapter封装<br>
[recyclerview-animators](https://github.com/wasabeef/recyclerview-animators):recyclerview item 动画<br>
[ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms):ViewPager的动画<br>
[AndroidTagGroup](https://github.com/2dxgujun/AndroidTagGroup):Android  Tag <br>
[SlidingLayout](https://github.com/HomHomLin/SlidingLayout):实现类似微信WebView的上拉下拉弹跳效果和iOS的ListView的果冻效果<br>
[material-ripple](https://github.com/balysv/material-ripple):为 View 添加水波纹<br>
[vlayout](https://github.com/alibaba/vlayout):VirtualLayout是一个针对RecyclerView的LayoutManager扩展<br>
[Material-Animations](https://github.com/lgvalle/Material-Animations):顾名思义，Material动画<br>
[StyleableToast](https://github.com/Muddz/StyleableToast):自定义Toast<br>
[Toasty](https://github.com/GrenderG/Toasty):加强版 Toast<br>
[PanoramaImageView](https://github.com/gjiazhe/PanoramaImageView):跟随设备滚动的ImageView<br>
[ItemTouchHelperDemo](https://github.com/YoKeyword/ItemTouchHelperDemo):使用ItemTouchHelper实现今日头条 网易新闻 的频道排序、频道移动<br>
[NineGridView](https://github.com/jeasonlzy/NineGridView):类似QQ空间，微信朋友圈，微博主页等，展示图片的九宫格控件<br>
[AppIntro](https://github.com/apl-devs/):App启动页封装<br>
[RecyclerViewSnap](https://github.com/rubensousa/RecyclerViewSnap):RecyclerView snapping example with SnapHelper<br>
[SmartTabLayout](https://github.com/ogaclejapan/SmartTabLayout):类似于 TabLayout，但是功能更加丰富<br>
[ExpandingPager](https://github.com/qs-lll/ExpandingPager):画廊 ViewPager<br>
[PullZoomView](https://github.com/jeasonlzy/PullZoomView):类似QQ空间，新浪微博个人主页下拉头部放大的布局效果<br>
[RMSwitch](https://github.com/RiccardoMoro/RMSwitch): SwitchButton<br>
[Leonids](https://github.com/plattysoft/Leonids): Android 粒子爆炸库<br>
[android-floating-action-button](https://github.com/futuresimple/android-floating-action-button): 扩展的 FAB 控件<br>
[LuseenBottomNavigation](https://github.com/armcha/LuseenBottomNavigation): 底部 tab<br>
[RippleEffect](https://github.com/traex/RippleEffect): 为View添加水波纹动画<br>
[AndroidViewAnimations](https://github.com/daimajia/AndroidViewAnimations): 为 View添加动画<br>
[Android-AlertView](https://github.com/saiwu-bigkoo/Android-AlertView):仿IOSdialog<br>
[WaveSideBar](https://github.com/Solartisan/WaveSideBar):一个快速跳跃分组的侧边栏控件<br>
[android-card-slide-panel](https://github.com/xmuSistone/android-card-slide-panel):模仿探探首页的卡片滑动效果<br>
[MaterialDrawer](https://github.com/mikepenz/MaterialDrawer): 最简单实现 Android 抽屉效果<br>
[FlycoTabLayout](https://github.com/H07000223/FlycoTabLayout): TabLayout 比官方的效果更加丰富<br>
[CircularReveal](https://github.com/ozodrukh/CircularReveal): 为 view 添加动画<br>
[cameraview](https://github.com/google/cameraview): google 的 camerView<br>
[Phoenix](https://github.com/Yalantis/Phoenix): 下拉刷新控件<br>
[shortbread](https://github.com/MatthiasRobbers/shortbread): 快速实现 shortcuts<br>
[lottie-android](https://github.com/airbnb/lottie-android):为 Android 快速实现各种动画效果<br>
[flexbox-layout](https://github.com/google/flexbox-layout):在 Android 上面实现 CSS 效果<br>
[NiftyNotification](https://github.com/sd6352051/NiftyNotification): 快速实现Android通知栏通知<br>
[circular-progress-button](https://github.com/dmytrodanylyk/circular-progress-button):自定义 Button，不同状态不同样式<br>
[android-flowlayout](https://github.com/ApmeM/android-flowlayout): FlowLayout ，推荐使用 google 的 flexbox-layout<br>
[AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView): 各种 动画<br>
[InfiniteCycleViewPager](https://github.com/Devlight/InfiniteCycleViewPager): 无限循环的ViewPager 具有双向交互和动画的效果<br>
[Android-DirectionalViewPager](https://github.com/JakeWharton/Android-DirectionalViewPager):竖直滚动的ViewPager<br>
[ShineButton](https://github.com/ChadCSong/ShineButton): 点赞 爆炸效果<br>
[ExplosionField](https://github.com/tyrantgit/ExplosionField): 粒子爆炸动画<br>
[AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer):解析PDF<br>
[MaterialDesignLibrary](https://github.com/navasmdc/MaterialDesignLibrary): MaterialDesign 控件，兼容到2.2<br>
[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart):Android图表视图/图表视图库<br>
[material-design-icons](https://github.com/google/material-design-icons): google  material design icon<br>
[CameraFilter](https://github.com/nekocode/CameraFilter):相机<br>
[MaterialProgressBar](https://github.com/DreaminginCodeZH/MaterialProgressBar):Material Design ProgressBar ,兼容到4.+<br>
[MaterialDesign](https://github.com/Templarian/MaterialDesign): MaterialDesign 图标库<br>
[SwipeBack](https://github.com/liuguangqiang/SwipeBack):侧滑退出Activity<br>
[SlideActivity](https://github.com/chenjishi/SlideActivity):侧滑退出Activity<br>
[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout):侧滑退出Activity<br>
[BGASwipeBackLayout-Android](https://github.com/bingoogolapple/BGASwipeBackLayout-Android):Android Activity 滑动返回<br>

#### 选择器

[MaterialDateTimePicker](https://github.com/wdullaer/MaterialDateTimePicker):Material Design 风格的时间选择控件<br>
[Android-PickerView](https://github.com/Bigkoo/Android-PickerView):时间选择器、省市区三级联动<br>
[ImagePicker](https://github.com/jeasonlzy/ImagePicker):完全仿微信的图片选择<br>
[android-crop](https://github.com/jdamcd/android-crop):一款图片裁剪框架<br>
[PhotoPicker](https://github.com/donglua/PhotoPicker):图片选择框架<br>
[uCrop](https://github.com/Yalantis/uCrop):图片裁剪，推荐<br>
[AndroidPicker](https://github.com/gzu-liyujiang/AndroidPicker):安卓选择器类库，包括日期选择器、时间选择器、单项选择器、双项选择器、城市地址选择器、车牌号选择器、数字选择器、星座选择器、生肖选择器、颜色选择器、文件选择器、目录选择器等<br>
[PictureSelector](https://github.com/LuckSiege/PictureSelector):Android 图片选择器<br>
[Album](https://github.com/yanzhenjie/Album): MaterialDesign-style Android图片选择器<br>
[ImageSelector](https://github.com/smuyyh/ImageSelector):Android 图片选择器。充分自由定制<br>
[ImagePicker](https://github.com/martin90s/ImagePicker):Android 图片选择器<br>
[boxing](https://github.com/Bilibili/boxing):B站开源图片选择器<br>
[Matisse](https://github.com/zhihu/Matisse):知乎推出的图片选择器<br>
[Album](https://github.com/7449/Album):图片选择器，自由定制，参数几乎满足所有的需求<br>
[cropiwa](https://github.com/steelkiwi/cropiwa):图片裁剪<br>
[TimePickerDialog](https://github.com/JZXiang/TimePickerDialog):Android时间选择器，支持年月日时分，年月日，年月，月日时分，时分格式<br>
[AndroidFileExplorer](https://github.com/dibakarece/AndroidFileExplorer):Android 文件选择管理器<br>
[BannerLayout](https://github.com/7449/BannerLayout):快速实现Banner轮播<br>

#### json 

[gson](https://github.com/google/gson):虽然不是最快的，但是它好用啊<br>
[jackson](https://github.com/FasterXML/jackson):解析json<br>
[ig-json-parser](https://github.com/Instagram/ig-json-parser):json解析<br>
[fastjson](https://github.com/alibaba/fastjson):号称JAVA最快的json解析框架<br>


#### 扫描

[android-zxingLibrary](https://github.com/yipianfengye/android-zxingLibrary):封装的ZXing扫描<br>
[zxing](https://github.com/zxing/zxing):二维码扫描，类似的还有`ZBar`<br>
[AwesomeQRCode](https://github.com/SumiMakito/AwesomeQRCode):二维码扫描工具<br>


#### 压缩，theme，编译，混淆，hotfix

[MultipleTheme](https://github.com/dersoncheng/MultipleTheme):Android换肤／夜间模式的Android框架<br>
[PluginTheme](https://github.com/jiangyinbin/PluginTheme):在线切换主题(换肤)，支持直接替换整个布局<br>
[MagicaSakura](https://github.com/Bilibili/MagicaSakura):Android 多主题框架<br>
[Android-skin-support](https://github.com/ximsfei/Android-skin-support):Android 换肤框架, 极低的学习成本, 极好的用户体验. 只需要两行代码, 就可以实现换肤<br>
[SkinSprite](https://github.com/geminiwen/SkinSprite): 换肤库<br>
[Android-Skin-Loader](https://github.com/fengjundev/Android-Skin-Loader):一个通过动态加载本地皮肤包进行换肤的皮肤框架<br>

[RePlugin](https://github.com/Qihoo360/RePlugin):360插件化开发<br>
[VirtualAPK](https://github.com/didi/VirtualAPK):滴滴插件化开发<br>

[tinker](https://github.com/Tencent/tinker):微信热修复<br>
[Robust](https://github.com/Meituan-Dianping/Robust):美团热修复<br>
[Nuwa](https://github.com/jasonross/Nuwa):女娲热修复<br>

[Luban](https://github.com/Curzibn/Luban):可能是最接近微信朋友圈的图片压缩算法<br>
[Compressor](https://github.com/zetbaitsu/Compressor): 图片压缩库<br>

[fastdex](https://github.com/typ0520/fastdex):加快 apk 的编译速度<br>
[LayoutCast](https://github.com/mmin18/LayoutCast):加快编译速度<br>
[freeline](https://github.com/alibaba/freeline):Freeline 是 Android 平台上的秒级编译方案<br>
[packer-ng-plugin](https://github.com/mcxiaoke/packer-ng-plugin):下一代Android打包工具，100个渠道包只需要10秒钟<br>
[AndroidMultiChannelBuildTool](https://github.com/GavinCT/AndroidMultiChannelBuildTool):安卓多渠道打包工具<br>

[AndResGuard](https://github.com/shwenzhang/AndResGuard):Android资源混淆工具<br>
[ThinRPlugin](https://github.com/meili/ThinRPlugin):去除android中的R.class<br>

#### 权限

[PermissionsDispatcher](https://github.com/hotchemi/PermissionsDispatcher): 简化 6.0 权限处理<br>
[Grant](https://github.com/anthonycr/Grant):简化 6.0 权限处理<br>
[easypermissions](https://github.com/googlesamples/easypermissions): google 推出的 简化 6.0 权限请求框架<br>


#### 解压缩

[zip4j](http://www.lingala.net/zip4j/)

[7z](https://github.com/hzy3774/AndroidUn7zip)

#### ...

[MVVMLight](https://github.com/Kelin-Hong/MVVMLight):MVVM示例<br>
[okhttp](https://github.com/square/okhttp):现在网络请求几乎都是用这个<br>
[retrofit](https://github.com/square/retrofit):结合`RxJava`使用效果更佳，底层封装的就是`OKHTTP`<br>
[kotlin](https://github.com/JetBrains/kotlin): kotlin <br>
[butterknife](https://github.com/JakeWharton/butterknife):极大的简化一些繁琐的操作，例如 finviewById <br>
[jsoup](https://github.com/jhy/jsoup): jsoup <br>
[jsoup-annotations](https://github.com/fcannizzaro/jsoup-annotations):注解使用 JSOUP<br>
[dagger2](https://github.com/google/dagger):dagger2<br>
[litho](https://github.com/facebook/litho):Litho是一个高效构建Android UI的声名式框架（declarative framework for building efficient UIs on Android）<br>
[FreeBuilder](https://github.com/google/FreeBuilder): 快速实现 Builder 模式<br>
[AndroidChromium](https://github.com/JackyAndroid/AndroidChromium):谷歌浏览器安卓版源码项目<br>
[leakcanary](https://github.com/square/leakcanary): 检查 Android 内存泄露<br>

[Apktool](https://github.com/iBotPeaches/Apktool) [jd-gui](https://github.com/java-decompiler/jd-gui) [dex2jar](https://github.com/pxb1988/dex2jar) [jadx](https://github.com/skylot/jadx)

[AndroidEventBus](https://github.com/hehonghui/AndroidEventBus):已停止开发，推荐EventBus，3.x之后，使用更方便<br>
[EventBus](https://github.com/greenrobot/EventBus):使用观察者模式，简化各个组件之间的通信<br>

[logger](https://github.com/orhanobut/logger):老牌的Log框架，个人比较喜欢KLOG<br>
[KLog](https://github.com/ZhaoKaiQiang/KLog):打印Log，个人比较喜欢用<br>

[AndroidEmoji](https://github.com/w446108264/AndroidEmoji): AndroidEmoji <br>
[XhsEmoticonsKeyboard](https://github.com/w446108264/XhsEmoticonsKeyboard):最良心的开源表情键盘解决方案<br>
[SuperNova-Emoji](https://github.com/hani-momanii/SuperNova-Emoji): Android Emoji <br>

[ImmersionBar](https://github.com/gyf-dev/ImmersionBar):android 4.4以上沉浸式状态栏和沉浸式导航栏管理<br>
[StatusBarUtil](https://github.com/laobie/StatusBarUtil):处理兼容Android低版本状态栏颜色问题，兼容到4.4<br>

[ZeusPlugin](https://github.com/iReaderAndroid/ZeusPlugin):掌阅推出的插件补丁框架<br>
[atlas](https://github.com/alibaba/atlas):Atlas是伴随着手机淘宝的不断发展而衍生出来的一个运行于Android系统上的一个容器化框架，也叫动态组件化框架<br>
[mars](https://github.com/Tencent/mars): 微信组件化<br>

[FileDownloader](https://github.com/lingochamp/FileDownloader):老牌的文件下载框架了<br>
[ActivityRouter](https://github.com/mzule/ActivityRouter):支持给Activity定义 URL，这样可以通过 URL 跳转到Activity，支持在浏览器以及 app 中跳入<br>
[LogReport](https://github.com/wenmingvs/LogReport):崩溃日志上传框架<br>
[WeChatLuckyMoney](https://github.com/geeeeeeeeek/WeChatLuckyMoney):微信抢红包插件<br>
[AndroidMP3Recorder](https://github.com/GavinCT/AndroidMP3Recorder):为Android提供MP3录音功能<br>
[gradle-retrolambda](https://github.com/evant/gradle-retrolambda):为java低版本提供 retrolambda<br>
[PLDroidPlayer](https://github.com/pili-engineering/PLDroidPlayer):Pili 直播 SDK 的安卓播放器<br>
[AndroidHttpCapture](https://github.com/JZ-Darkal/AndroidHttpCapture):Android手机抓包软件<br>
[Android-Pay](https://github.com/mayubao/Android-Pay):两行代码实现微信支付， 三行代码实现支付宝支付<br>
[condom](https://github.com/oasisfeng/condom):一个超轻超薄的Android工具库，阻止三方SDK中常见的有害行为<br>
[AppMethodOrder](https://github.com/zjw-swun/AppMethodOrder):一个能让你了解所有函数调用顺序以及函数耗时的Android库（无需侵入式代码）<br>
[android-architecture-components](https://github.com/googlesamples/android-architecture-components): 26 sample<br>
[Calligraphy](https://github.com/chrisjenx/Calligraphy):加载字体<br>
[Android-BluetoothSPPLibrary](https://github.com/akexorcist/Android-BluetoothSPPLibrary):Android蓝牙封装<br>
[ASimpleCache](https://github.com/yangfuhai/ASimpleCache):ASimpleCache 是一个为android制定的 轻量级的 开源缓存框架<br>
[MasteringAndroidDataBinding](https://github.com/LyndonChin/MasteringAndroidDataBinding):精通 Android Data Binding，介绍了遇到的坑以及解决办法<br>
[agera](https://github.com/google/agera): 类似于 RxJava 的 异步和响应式程序的框架<br>
[androidannotations](https://github.com/androidannotations/androidannotations):AndroidAnnotations是一个开源框架，可以加速Android开发,封装一套开发工具<br>
[T-MVP](https://github.com/north2016/T-MVP):Android MVP架构<br>
[LessCode](https://github.com/openproject/LessCode):Android工具库<br>
[mosby](https://github.com/sockeqwe/mosby): Android 的 MVP or  MVI 库(快速开发)<br>
[Moxy](https://github.com/Arello-Mobile/Moxy): MVP 快速开发框架<br>
[Store](https://github.com/NYTimes/Store):用于异步数据加载和缓存的Android库<br>
[android-training-course-in-chinese](https://github.com/kesenhoo/android-training-course-in-chinese):Android官方培训课程中文版<br>
[Android-Session-Slides](https://github.com/MDCC2016/Android-Session-Slides):MDCC 2016  资料<br>
[AndroidDecompiler](https://github.com/dirkvranckaert/AndroidDecompiler):反编译<br>
[AndroidLibs](https://github.com/XXApple/AndroidLibs):Android 开源代码大全<br>
[RapidView](https://github.com/Tencent/RapidView):用于开发Android客户端界面、逻辑以及功能的开发组件<br>

#### sample

[android-architecture-components](https://github.com/googlesamples/android-architecture-components):google架构示例<br>
[android-architecture-components](https://github.com/googlesamples/android-architecture-components):google 官方推出的架构示例Demo<br>
[android-Camera2Video](https://github.com/googlesamples/android-Camera2Video):google sample<br>
[android-Camera2Basic](https://github.com/googlesamples/android-Camera2Basic):google sample<br>
[android-PictureInPicture](https://github.com/googlesamples/android-PictureInPicture):google sample<br>
[android-UniversalMusicPlayer](https://github.com/googlesamples/android-UniversalMusicPlayer):google sample<br>
[android-instant-apps](https://github.com/googlesamples/android-instant-apps):google sample android-instant-apps<br>
[android-ndk](https://github.com/googlesamples/android-ndk):google sample<br>
[android-architecture](https://github.com/googlesamples/android-architecture):讨论和展示Android应用程序的不同架构工具和模式<br>
[UltimateAndroidReference](https://github.com/aritraroy/UltimateAndroidReference):App 开发参考<br>
[u2020](https://github.com/JakeWharton/u2020):示例Android应用程序，其中展示了Dagger与其他开源库之间的高级用法<br>
[f8app](https://github.com/fbsamples/f8app):2016年官方F8应用程序的源代码<br>
[Gitskarios](https://github.com/gitskarios/Gitskarios):第三方GitHub客户端<br>
[PocketHub](https://github.com/pockethub/PocketHub):PocketHub 客户端<br>
[Android-CleanArchitecture](https://github.com/android10/Android-CleanArchitecture):构建Android应用程序。<br>
[android-common](https://github.com/Trinea/android-common):主要包括：缓存、公共View及Android常用工具类<br>
[plaid](https://github.com/nickbutcher/plaid):一个新闻的Android应用程序<br>
[iosched](https://github.com/google/iosched):io 大会app<br>
[FileExplorer](https://github.com/MiCode/FileExplorer):MIUI文件管理器社区开源版<br>
[Notes](https://github.com/MiCode/Notes):小米便签社区开源版<br>
[BookReader](https://github.com/JustWayward/BookReader):"任阅" 网络小说阅读器<br>
[codeKK-Android](https://github.com/7449/codeKK-Android): code kk  第三方android客户端<br>
[ZLSimple](https://github.com/7449/ZLSimple):知乎专栏，包括一个简单的小程序版和Kotlin版，都在分支<br>
[Lawnchair](https://github.com/Deletescape-Media/Lawnchair):launcher<br>
[JsoupSample](https://github.com/7449/JsoupSample):jsoupSample<br>
[AndroidTVLauncher](https://github.com/JackyAndroid/AndroidTVLauncher):TV桌面<br>

#### cordova 

[CordovaCn](https://github.com/CordovaCn/CordovaCn):cordova 中文

#### react-native

[react-native](https://github.com/facebook/react-native):源码地址<br>
[react-native-docs-cn](https://github.com/reactnativecn/react-native-docs-cn):React Native中文文档<br>

#### kotlin

[kotlin-in-chinese](https://github.com/huanglizhuo/kotlin-in-chinese):kotlin 官方文档翻译<br>
[KotlinDoc-cn](https://github.com/kymjs/KotlinDoc-cn):Kotlin语言文档翻译项目<br>

#### 社区，文档，笔记

[android-open-project](https://github.com/Trinea/android-open-project):Android 开源项目分类汇总<br>
[AndroidNote](https://github.com/venshine/AndroidNote):Android 进阶笔记，包含常用的技术框架、博客社区、书籍等。<br>
[AndroidNote](https://github.com/GcsSloop/AndroidNote):安卓学习笔记<br>
[android-tech-frontier](https://github.com/hehonghui/android-tech-frontier):【停止维护】一个定期翻译国外Android优质的技术、开源库、软件架构设计、测试等文章的开源项目<br>
[android-open-project-analysis](https://github.com/android-cn/android-open-project-analysis):Android 开源项目源码解析<br>
[AndroidSdkSourceAnalysis](https://github.com/LittleFriendsGroup/AndroidSdkSourceAnalysis):android sdk 源码解析<br>
[android-discuss](https://github.com/android-cn/android-discuss):Android 问题交流讨论坛<br>
[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode):包含很多Util<br>
[free-programming-books-zh_CN](https://github.com/justjavac/free-programming-books-zh_CN):免费的计算机编程类中文书籍<br>
[TimLiu-Android](https://github.com/Tim9Liu9/TimLiu-Android):总结的Android开源项目及库<br>
[android-dev-bookmarks](https://github.com/zhengxiaopeng/android-dev-bookmarks):Android 开发者的浏览器书签 <br>
[android-dev-cn](https://github.com/android-cn/android-dev-cn):一些国内 Android 开发者信息<br>
[android-dev-com](https://github.com/android-cn/android-dev-com):一些国外 Android 开发者信息<br>
## 小程序

[wxParse](https://github.com/icindy/wxParse):小程序富文本解析,支持HTML及markdown<br>
[awesome-wechat-weapp](https://github.com/justjavac/awesome-wechat-weapp):微信小程序开发资源汇总<br>


## APPLE

[swift](https://github.com/apple/swift): swift <br>
[sampleCode](https://developer.apple.com/library/content/navigation/#section=Resource%20Types&topic=Sample%20Code):apple官网示例代码<br>
[iOS-Swift-Demos](https://github.com/Lax/iOS-Swift-Demos):分类整理的Swift开发学习资源<br>
[the-swift-programming-language-in-chinese](https://github.com/numbbbbb/the-swift-programming-language-in-chinese):中文版 Apple 官方 Swift 教程《The Swift Programming Language》<br>
[MacOSX-SDKs](https://github.com/phracker/MacOSX-SDKs):XCode SDK<br>

[cartool](https://github.com/steventroughtonsmith/cartool):Export images from OS X / iOS .car CoreUI archives<br>
[Assets.carTool](https://github.com/yuedong56/Assets.carTool):Mac上解压Assets.car文件的小工具<br>
[AssetsExtractor](https://github.com/pcjbird/AssetsExtractor):Assets提取工具(推荐)<br>


#### touchbar

[TouchBarDemoApp](https://github.com/bikkelbroeders/TouchBarDemoApp):包含了集中touchbar<br>
[zsh-iterm-touchbar](https://github.com/iam4x/zsh-iterm-touchbar):zsh的touchbar加强版<br>


## brew

[brew](https://github.com/Homebrew/brew):<br>
[homebrew-cask](https://github.com/caskroom/homebrew-cask):app安装<br>
[homebrew-cask-upgrade](https://github.com/buo/homebrew-cask-upgrade):brew cask 升级<br>
