---
layout:     post
title:      "Android_Java注解"
subtitle:   "简单的butterknife"
date:       2016-12-21
author:     "y"
header-mask: 0.3
header-img: "img/java.jpg"
catalog: false
tags:
    - android
    - 注解
---

通过注解@ViewId()获取Android xml布局文件中的控件，类似于butterknife



  在Activity或者	Dialog，PopupWindow里面使用直接在布局之后 Injector.inject(this);<br>
  fragment里面则需要当前view， Injector.inject(this, view);

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	public @interface ViewId {
	    int value();
	}
	
	

	public class Injector {
	
	    public static void inject(Object object) {
	        try {
	            if (object instanceof Activity) {
	                injectView(object, ((Activity) object).getWindow().getDecorView());
	            }
	            if (object instanceof Dialog) {
	                injectView(object, ((Dialog) object).getWindow().getDecorView());
	            }
	            if (object instanceof PopupWindow) {
	                injectView(object, ((PopupWindow) object).getContentView());
	            }
	        } catch (Exception e) {
	            throw new NullPointerException(e.toString());
	        }
	    }
	
	    public static void inject(Object object, View view) {
	        try {
	            injectView(object, view);
	        } catch (Exception e) {
	            throw new NullPointerException(e.toString());
	        }
	
	    }
	
	    private static void injectView(Object paramObject, View viewById) throws Exception {
	        for (Field field : getFields(paramObject.getClass())) {
	            ViewId viewId = field.getAnnotation(ViewId.class);
	            if (viewId != null) {
	                View view = viewById.findViewById(viewId.value());
	                if (view != null) {
	                    field.setAccessible(true);
	                    field.set(paramObject, view);
	                }
	            }
	        }
	    }
	
	    private static List<Field> getFields(Class<?> paramClass) {
	        ArrayList<Field> localArrayList = new ArrayList<>();
	        Field[] arrayOfField = paramClass.getDeclaredFields();
	        localArrayList.addAll(Arrays.asList(arrayOfField).subList(0, arrayOfField.length));
	        if ((paramClass != Object.class) && (paramClass.getSuperclass() != null)) {
	            localArrayList.addAll(getFields(paramClass.getSuperclass()));
	        }
	        return localArrayList;
	    }
	}
