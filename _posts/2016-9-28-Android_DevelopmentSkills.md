---
layout:     post
title:      "Android_DevelopmentSkills"
subtitle:   "android开发技巧集合"
date:       2016-9-28
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - develop
    - android
    - skills
---

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
