---
layout:     post
title:      "Android_DevelopmentSkills"
subtitle:   "android开发技巧集合"
date:       2017-2-9
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
---

## Layout属性

android:clipToPadding：配合paddingTop可简单实现View距离顶部一定距离

android:clipChildren：是否限制子View在其范围内

## 调用显示触摸位置功能

	android.provider.Settings.System.putInt(getContentResolver(), "show_touches", 1);

## 代码切换全屏

        //切换到全屏 
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //切换到非全屏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

## Activity透明

    <style name="TransparentActivity" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:colorBackgroundCacheHint">@null</item>
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowContentOverlay">@null</item>
    </style>

## 展开、收起状态栏

		 public static final void collapseStatusBar(Context ctx) {
		        Object sbservice = ctx.getSystemService("statusbar");
		        try {
		            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
		            Method collapse;
		            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
		                collapse = statusBarManager.getMethod("collapsePanels");
		            } else {
		                collapse = statusBarManager.getMethod("collapse");
		            }
		            collapse.invoke(sbservice);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
	
	    public static final void expandStatusBar(Context ctx) {
	        Object sbservice = ctx.getSystemService("statusbar");
	        try {
	            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
	            Method expand;
	            if (Build.VERSION.SDK_INT >= 17) {
	                expand = statusBarManager.getMethod("expandNotificationsPanel");
	            } else {
	                expand = statusBarManager.getMethod("expand");
	            }
	            expand.invoke(sbservice);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

## 获取网络类型名称

	 public static String getNetworkTypeName(Context context) {
	        if (context != null) {
	            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            if (connectMgr != null) {
	                NetworkInfo info = connectMgr.getActiveNetworkInfo();
	                if (info != null) {
	                    switch (info.getType()) {
	                        case ConnectivityManager.TYPE_WIFI:
	                            return "WIFI";
	                        case ConnectivityManager.TYPE_MOBILE:
	                            return getNetworkTypeName(info.getSubtype());
	                    }
	                }
	            }
	        }
	        return getNetworkTypeName(TelephonyManager.NETWORK_TYPE_UNKNOWN);
	    }
	
	    public static String getNetworkTypeName(int type) {
	        switch (type) {
	            case TelephonyManager.NETWORK_TYPE_GPRS:
	                return "GPRS";
	            case TelephonyManager.NETWORK_TYPE_EDGE:
	                return "EDGE";
	            case TelephonyManager.NETWORK_TYPE_UMTS:
	                return "UMTS";
	            case TelephonyManager.NETWORK_TYPE_HSDPA:
	                return "HSDPA";
	            case TelephonyManager.NETWORK_TYPE_HSUPA:
	                return "HSUPA";
	            case TelephonyManager.NETWORK_TYPE_HSPA:
	                return "HSPA";
	            case TelephonyManager.NETWORK_TYPE_CDMA:
	                return "CDMA";
	            case TelephonyManager.NETWORK_TYPE_EVDO_0:
	                return "CDMA - EvDo rev. 0";
	            case TelephonyManager.NETWORK_TYPE_EVDO_A:
	                return "CDMA - EvDo rev. A";
	            case TelephonyManager.NETWORK_TYPE_EVDO_B:
	                return "CDMA - EvDo rev. B";
	            case TelephonyManager.NETWORK_TYPE_1xRTT:
	                return "CDMA - 1xRTT";
	            case TelephonyManager.NETWORK_TYPE_LTE:
	                return "LTE";
	            case TelephonyManager.NETWORK_TYPE_EHRPD:
	                return "CDMA - eHRPD";
	            case TelephonyManager.NETWORK_TYPE_IDEN:
	                return "iDEN";
	            case TelephonyManager.NETWORK_TYPE_HSPAP:
	                return "HSPA+";
	            default:
	                return "UNKNOWN";
	        }
	    }



## 扫描指定的文件

	sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

## 有没有应用程序处理你发出的intent

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

## TransitionDrawable实现渐变效果

    private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(R.color.colorAccent), new BitmapDrawable(imageView.getContext().getResources(), bitmap)});
        imageView.setBackgroundDrawable(imageView.getDrawable());
        imageView.setImageDrawable(td);
        td.startTransition(200);
    }

## 字符串中只能包含：中文、数字、下划线(_)、横线(-)

    public static boolean checkNickname(String sequence) {
        final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w-_]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        return !matcher.find();
    }

## 字符串中是否包含汉字

    public static boolean checkChinese(String sequence) {
        final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        boolean result = false;
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        result = matcher.find();
        return result;
    }

## 获取应用程序下所有Activity

    public static ArrayList<String> getActivities(Context ctx) {
        ArrayList<String> result = new ArrayList<String>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setPackage(ctx.getPackageName());
        for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(intent, 0)) {
            result.add(info.activityInfo.name);
        }
        return result;
    }

## 计算字宽

注意如果设置了textStyle，还需要进一步设置TextPaint

    public static float GetTextWidth(String text, float Size) {
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(Size);
        return FontPaint.measureText(text);
    }
	

## 判断是否是平板

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

## 获取屏幕尺寸

    public static double getScreenPhysicalSize(Activity ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }

## 代码设置TextView的样式

	new TextView(new ContextThemeWrapper(this, R.style.text_style))

## TextView 显示文字阴影效果

1. 
	TextView.setShadowLayer(10F, 11F,5F, Color.YELLOW); 
	第一个参数为模糊半径，越大越模糊。 
	第二个参数是阴影离开文字的 x 横向距离。 	
	第三个参数是阴影离开文字的 Y 横向距离。 
	第四个参数是阴影颜色。

2. 
	将 TextView 控件的 style 单独写成一个. xml 文件进行添加
	
     <style name="AudioFileInfoOverlayText">
         <item name="android:shadowColor">#4d4d4d</item>
         <item name="android:shadowDx">0</item>
         <item name="android:shadowDy">-3</item>
         <item name="android:shadowRadius">3</item>
     </style>

## CRUD语句

insert: `insert into tab_name (field,field,field) values(?,?,?)`<br>
update: `update tab_name set field = value where field = value`<br>
select: `select * from tab_name`<br>
delete: `delete from tab_name where field = value`<br>

简单的四个语句，复杂的需要自己添加，例如select时候可以添加 `where` 实现过滤功能<br>

## AlertDialog改变Theme

 `AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));`

## 截取当前屏幕截图

    public static Bitmap captureContent(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }


## textView显示不同颜色

    public static SpannableStringBuilder getHomeTitlePageType(String text, String suffix) {
        SpannableStringBuilder mText = new SpannableStringBuilder("#" + text + "#" + suffix);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), R.color.colorPrimary));
        mText.setSpan(foregroundColorSpan, 0, mText.length() - suffix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mText;
    }
	

## 关闭软键盘

	
    public static void offKeyboard(EditText editText) {
        if (detectKeyboard(editText)) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void openKeyboard(EditText editText) {
        if (!detectKeyboard(editText)) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static boolean detectKeyboard(EditText editText) {
        //true 弹出状态
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


## 代码创建selector

	public class SelectorUtils {
	
	
	    public static StateListDrawable getDrawableSelector(float enabledRadius, int enabledColor, float normalRadius, int normalColor) {
	        StateListDrawable drawable = new StateListDrawable();
	        drawable.addState(new int[]{android.R.attr.state_enabled}, getShape(enabledRadius, enabledColor));
	        drawable.addState(new int[]{-android.R.attr.state_enabled}, getShape(normalRadius, normalColor));
	        return drawable;
	    }
	
	
	    private static GradientDrawable getShape(float radius, int color) {
	        GradientDrawable gd = new GradientDrawable();
	        gd.setShape(GradientDrawable.OVAL);
	        gd.setCornerRadius(radius);
	        gd.setColor(color);
	        return gd;
	    }
	}
	
	
## 双击退出程序

		private long exitTime = 0;
	
	    @Override
	    public void onBackPressed() {
	        if ((System.currentTimeMillis() - exitTime) > 2000) {
	            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_LONG).show();
	            exitTime = System.currentTimeMillis();
	        } else {
	            super.onBackPressed();
	        }
	    }

## 检测用户是否某个应用

	 public static boolean isApk(Context context, String packageName) {  
	        final PackageManager packageManager = context.getPackageManager();  
	        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);  
	        if (pinfo != null) {  
	            for (int i = 0; i < pinfo.size(); i++) {  
	                String pn = pinfo.get(i).packageName;  
	                if (pn.equals(packageName)) {  
	                    return true;  
	                }  
	            }  
	        }  
	        return false;  
	    }  

## 在代码中改变TextView的drawable的图片资源
	
	Drawable drawable = getResources().getDrawable(R.mipmap.ic_pay_koukuan);  
	drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
	tv_text.setCompoundDrawables(drawable, null, null, null);  

## 点击按钮实现手机震动
	
	<uses-permission android:name="android.permission.VIBRATE"/>

	  public static void vibrator(Context context) {  
	      Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);  
	      //控制振动时间  
	      vibrator.vibrate(2000);  
	  } 

## EditText输入限制小数点后两位

	public class EditTextUtils {  
	
	    public static void editWatcher(final EditText editText, final EditTextChanged editTextChanged) {  
	  
	        editText.addTextChangedListener(new TextWatcher() {  
	            @Override  
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
	                editTextChanged.beforeTextChanged();  
	            }  
	  
	            @Override  
	            public void onTextChanged(CharSequence s, int start, int before, int count) {  
	                if (s.toString().contains(".")) {  
	                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {  
	                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);  
	                        editText.setText(s);  
	                        editText.setSelection(s.length());  
	                    }  
	                }  
	            }  
	  
	            @Override  
	            public void afterTextChanged(Editable s) {   }  
	        });  
	    }  
	  
	    public interface EditTextChanged {  
	        void beforeTextChanged(); 
	    }  
	  
	}   

## TextView后加字体并改变颜色点击跳转

	private void hint_onClick() {  
	    SpannableString span = new SpannableString(hint);//后加内容  
	    ClickableSpan click = new MClickableSpan(this);  
	    span.setSpan(click, 0, hint.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);  
	    tvHint.append(span);  
	    tvHint.setMovementMethod(LinkMovementMethod.getInstance());  
	}  

	public class MClickableSpan extends ClickableSpan { 
 
	 	@Override  
	    public void updateDrawState(TextPaint ds) {  
	        ds.setColor(Color.BLUE);  
	    }  
	  
	    @Override  
	    public void onClick(View widget) {  
	      
	    }  

	}  

## apk下载完成跳转到安装界面

    public static void installApp(Context context, String filePath) {
        File _file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(_file),"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

## 添加删除桌面快捷方式

    <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
    <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />

    /**
     * 创建桌面快捷方式
     */
    private void addShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); //不允许重复创建
        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
        ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        sendBroadcast(shortcut);
    }

    /**
     * 删除程序的快捷方式
     */
    private void delShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        //注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
        String appClass = this.getPackageName() + "." + this.getLocalClassName();
        ComponentName comp = new ComponentName(this.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
        sendBroadcast(shortcut);
    }


## 几个时间方法

		获取本地时间  
		
			public static String getCurrentDate() {  
			    Date d = new Date();  
			    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    return format.format(d);  
			}  
			  
		时间戳转换成字符串  
		
			public static String getDateToString(long time) {  
			    Date d = new Date(time);  
			    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    return format.format(d);  
			}  
		  
		字符串转换成时间戳  
		
			public static long getStringToDate(String time) {  
			    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    Date date = new Date();  
			    try {  
			        date = format.parse(time);  
			    } catch (ParseException e) {  
			        e.printStackTrace();  
			    }  
			    return date.getTime();  
			} 

	
	  		@SuppressLint("SimpleDateFormat")
		    public static String getData() {
		        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		    }
		
		    @SuppressLint("SimpleDateFormat")
		    public static String getTime() {
		        return new SimpleDateFormat("HH:mm").format(new Date());
		    }
		
		
		    public static String getWeek() {
		        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(new Date());
		        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		        if (week_index < 0) {
		            week_index = 0;
		        }
		        return weeks[week_index];
		    }

