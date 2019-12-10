---
layout:     post
title:      "Android开发中的小技巧"
subtitle:   "记下来避免忘记"
date:       2019-12-10
tags:
    - tips
---

[Old Version](https://7449.github.io/2016/09/28/android_development_skills.html)

## WebView 判断 Html 高低

    public class HtmlHeightInterface {
    
        public static final String URL = "javascript:( function () { var h = document.body.clientHeight; window.height.height(h); } ) ()";
    
        private CustomWebView webView;
        private Activity activity;
    
        public HtmlHeightInterface(CustomWebView webView, Activity activity) {
            this.webView = webView;
            this.activity = activity;
        }
    
        @JavascriptInterface
        public void height(int height) {
            ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, webView.getContext().getResources().getDisplayMetrics());
            activity.runOnUiThread(() -> webView.setLayoutParams(layoutParams));
        }
    
    }

## ViewPager 判断左右上下滑动

    public class CustomViewPager extends ViewPager {
        private boolean noScroll = true;
        private float downX;
        private float downY;
    
        private OnScrollOrientationListener onScrollOrientationListener;
    
        public CustomViewPager(@NonNull Context context) {
            super(context);
            init();
        }
    
        public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }
    
        private void init() {
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
                field.set(this, scroller);
            } catch (Exception ignored) {
            }
        }
    
        public void setOnScrollOrientationListener(OnScrollOrientationListener onScrollOrientationListener) {
            this.onScrollOrientationListener = onScrollOrientationListener;
        }
    
        public void setNoScroll(boolean noScroll) {
            if (noScroll && !isFakeDragging()) {
                beginFakeDrag();
            } else if (!noScroll && isFakeDragging()) {
                endFakeDrag();
            }
            this.noScroll = noScroll;
        }
    
        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = x;
                    downY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    float dx = x - downX;
                    float dy = y - downY;
                    if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                        int orientation = getOrientation(dx, dy);
                        switch (orientation) {
                            case 'r':
                                if (onScrollOrientationListener != null) {
                                    onScrollOrientationListener.onScrollRight();
                                }
                                break;
                            case 'l':
                                if (onScrollOrientationListener != null) {
                                    onScrollOrientationListener.onScrollLeft();
                                }
                                break;
                            case 't':
                                if (onScrollOrientationListener != null) {
                                    onScrollOrientationListener.onScrollTop();
                                }
                                break;
                            case 'b':
                                if (onScrollOrientationListener != null) {
                                    onScrollOrientationListener.onScrollBottom();
                                }
                                break;
                        }
                    }
                    break;
            }
            if (noScroll) {
                return false;
            }
            return super.onInterceptTouchEvent(event);
        }
    
        @Override
        public boolean arrowScroll(int direction) {
            if (noScroll) return false;
            if (direction != FOCUS_LEFT && direction != FOCUS_RIGHT) return false;
            return super.arrowScroll(direction);
        }
    
        private int getOrientation(float dx, float dy) {
            if (Math.abs(dx) > Math.abs(dy)) {
                return dx > 0 ? 'r' : 'l';
            } else {
                return dy > 0 ? 'b' : 't';
            }
        }
    
        public interface OnScrollOrientationListener {
            default void onScrollTop() {
            }
    
            default void onScrollBottom() {
            }
    
            default void onScrollLeft() {
            }
    
            default void onScrollRight() {
            }
        }
    
    
        private static class FixedSpeedScroller extends Scroller {
            private int mDuration = 1500;
    
            public FixedSpeedScroller(Context context) {
                super(context);
            }
    
            @Override
            public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                //为什么需要设置滚动时间
                super.startScroll(startX, startY, dx, dy, mDuration);
            }
    
            @Override
            public void startScroll(int startX, int startY, int dx, int dy) {
                super.startScroll(startX, startY, dx, dy, mDuration);
            }
        }
    }

## 针对 Java 的一种防Null

    public class Safe {
        private Safe() {
        }
    
        public interface Function1<A> {
            void action(A it);
        }
    
        public interface Function2<A, B> {
            void action(A a, B b);
        }
    
        public interface Function3<A, B, C> {
            void action(A a, B b, C c);
        }
    
        public static <A> void notNull(A o, Function1<A> function1) {
            if (UIUtils.checkNotNull(o)) {
                function1.action(o);
            }
        }
    
        public static <A, B> void notNull(A a, B b, Function2<A, B> function2) {
            if (UIUtils.checkNotNull(a) && UIUtils.checkNotNull(b)) {
                function2.action(a, b);
            }
        }
    
        public static <A, B, C> void notNull(A a, B b, C c, Function3<A, B, C> function3) {
            if (UIUtils.checkNotNull(a) && UIUtils.checkNotNull(b) && UIUtils.checkNotNull(c)) {
                function3.action(a, b, c);
            }
        }
    
    }

## maven

    apply plugin: 'maven'
    
    def group = 'com.google.android'
    
    ext {
        PUBLISH_VERSION = version
        PUBLISH_ARTIFACT_ID = artifactId
        PUBLISH_GROUP_ID = group
    
        PUBLISH_URL = "file:///$rootDir/android"
    }
    
    uploadArchives {
        repositories {
            mavenDeployer {
                repository(url: PUBLISH_URL)
                pom.project {
                    groupId PUBLISH_GROUP_ID
                    artifactId PUBLISH_ARTIFACT_ID
                    version PUBLISH_VERSION
                }
            }
        }
    }
    
    uploadArchives {
        doFirst {
            if (new File("$rootDir/android/" + PUBLISH_ARTIFACT_ID).exists()) {
                delete "$rootDir/android/" + PUBLISH_ARTIFACT_ID
            }
        }
    }

## 检查版本

    new Version(version).compareTo(new Version(version))

    public class Version implements Comparable<Version> {
    
        private String version;
    
        private String get() {
            return this.version;
        }
    
        public Version(String version) {
            if (version == null)
                throw new IllegalArgumentException("Version can not be null");
            if (!version.matches("[0-9]+(\\.[0-9]+)*"))
                throw new IllegalArgumentException("Invalid version format");
            this.version = version;
        }
    
        @Override
        public int compareTo(@NonNull Version that) {
            String[] thisParts = this.get().split("\\.");
            String[] thatParts = that.get().split("\\.");
            int length = Math.max(thisParts.length, thatParts.length);
            for (int i = 0; i < length; i++) {
                int thisPart = i < thisParts.length ?
                        Integer.parseInt(thisParts[i]) : 0;
                int thatPart = i < thatParts.length ?
                        Integer.parseInt(thatParts[i]) : 0;
                if (thisPart < thatPart)
                    return -1;
                if (thisPart > thatPart)
                    return 1;
            }
            return 0;
        }
    
        @Override
        public boolean equals(Object that) {
            return this == that || that != null && this.getClass() == that.getClass() && this.compareTo((Version) that) == 0;
        }
    
    }

## catch 全局异常

    public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    
        private static final String ERROR_MESSAGE = "出现了未知错误,即将退出";
        @SuppressLint("StaticFieldLeak")
        private static ExceptionHandler instance = new ExceptionHandler();
        private Context context;
        private Thread.UncaughtExceptionHandler defaultHandler;
        private Map<String, String> devInfo = new HashMap<>();
        private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    
        public static ExceptionHandler getInstance() {
            return instance;
        }
    
        @SuppressLint("SimpleDateFormat")
        private static String getCurrentTime() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
    
        public void setCustomCrashHandler(Context ctx) {
            context = ctx;
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            boolean isDone = doException(ex);
            if (!isDone && defaultHandler != null) {
                defaultHandler.uncaughtException(thread, ex);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {
    
                }
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    
        private boolean doException(Throwable ex) {
            if (ex == null) {
                return true;
            }
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(context, ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            collectDeviceInfo(context);
            saveExceptionToFile(ex);
            return true;
        }
    
        private void collectDeviceInfo(Context ctx) {
            try {
                PackageManager pm = ctx.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (pi != null) {
                    devInfo.put("versionName", pi.versionName);
                    devInfo.put("versionCode", "" + pi.versionCode);
                    devInfo.put("MODEL", "" + Build.MODEL);
                    devInfo.put("SDK_INT", "" + Build.VERSION.SDK_INT);
                    devInfo.put("PRODUCT", "" + Build.PRODUCT);
                    devInfo.put("TIME", "" + getCurrentTime());
                }
            } catch (Exception ignored) {
            }
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    devInfo.put(field.getName(), field.get(null).toString());
                } catch (Exception ignored) {
                }
            }
        }
    
        private void saveExceptionToFile(Throwable ex) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : devInfo.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key).append("=").append(value).append("\n");
            }
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            sb.append(result);
            try {
                String time = df.format(new Date());
                String fileName = time + ".error_log";
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    FileOutputStream fos = new FileOutputStream(FileUtils.getDiskFileDir(context, "error").getPath() + "/" + fileName);
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }
            } catch (Exception ignored) {
    
            }
        }
    }

## BitmapUtils

    public class BitmapUtils {
    
        /**
         * 获取屏幕的宽高
         *
         * @param path 文件路径
         * @return [0] 宽 [1]高
         */
        public static int[] getImageWidthHeight(String path) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            return new int[]{options.outWidth, options.outHeight};
        }
    
        /**
         * 图片转 Base64 带头部
         *
         * @param path 路径
         */
        public static String bitmapToBase64HeaderPng(String path) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return String.valueOf(TextUtils.concat("data:image/jpeg;base64,", Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)));
        }
    
        /**
         * 图片转 Base64
         *
         * @param path 路径
         */
        public static String bitmapToBase64(String path) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
        }
    
        /**
         * 图片转 Base64 带头部
         *
         * @param bitmap {@link Bitmap}
         */
        public static String bitmapToBase64HeaderPng(Bitmap bitmap) {
            if (bitmap == null) return "";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return String.valueOf(TextUtils.concat("data:image/png;base64,", Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)));
        }
    
        /**
         * 图片转 Base64 带头部
         *
         * @param bitmap {@link Bitmap}
         */
        public static String bitmapToBase64(Bitmap bitmap) {
            if (bitmap == null) return "";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
        }
    }

## 禁止滑动的LayoutManager

    public class NoScrollGridLayoutManager extends GridLayoutManager {
        private boolean isScrollEnabled = false;
    
        public NoScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
    
        public NoScrollGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }
    
        public NoScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }
    
        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }
    
        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }
    

## 键盘弹出或隐藏

    public class KeyboardStatusDetector implements ViewTreeObserver.OnGlobalLayoutListener {
    
        private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 100;
        private boolean keyboardVisible = false;
        private KeyboardListener keyboardListener;
    
        public KeyboardStatusDetector(@NonNull KeyboardListener keyboardListener) {
            this.keyboardListener = keyboardListener;
        }
    
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            keyboardListener.getKeyboardView().getWindowVisibleDisplayFrame(r);
            int heightDiff = keyboardListener.getKeyboardView().getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) {
                if (!keyboardVisible) {
                    keyboardVisible = true;
                    keyboardListener.onVisibilityChanged(true);
                }
            } else {
                if (keyboardVisible) {
                    keyboardVisible = false;
                    keyboardListener.onVisibilityChanged(false);
                }
            }
        }
    
    
        public interface KeyboardListener {
            View getKeyboardView();
    
            void onVisibilityChanged(boolean flag);
        }
    }

## EditText NoEmoji

    public class EditTextHelper {
    
        private static Pattern PATTERN = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");
    
        public static void noemoji(EditText... editText) {
            for (EditText text : editText) {
                text.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
                    Matcher matcher = PATTERN.matcher(source);
                    if (!matcher.find()) {
                        return null;
                    } else {
                        return "";
                    }
                }});
            }
        }
    }