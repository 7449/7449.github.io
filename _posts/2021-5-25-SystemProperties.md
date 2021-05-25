---
layout:     post
title:      "普通Android程序获取SystemProperties"
subtitle:   "SystemProperties"
date:       2021-5-25
tags:
    - SystemProperties
---

* 在开发时有可能会获取`system/build.prop`里面的属性，一般都是通过`su`或者反射获取属性值，
  这里介绍一种欺骗系统的方法来获取属性值而不需要进行反射

在`java`目录下创建`android/os`目录并创建`SystemProperties.java`

其内容如下
    
    public class SystemProperties {
    
        public static String get(final String key) {
            throw new RuntimeException("Stub!");
        }
    
        public static String get(final String key, final String def) {
            throw new RuntimeException("Stub!");
        }
    
        public static int getInt(final String key, final int def) {
            throw new RuntimeException("Stub!");
        }
    
        public static long getLong(final String key, final long def) {
            throw new RuntimeException("Stub!");
        }
    
        public static boolean getBoolean(final String key, final boolean def) {
            throw new RuntimeException("Stub!");
        }
    
        public static void set(final String key, final String val) {
            throw new RuntimeException("Stub!");
        }
    
        public static void addChangeCallback(final Runnable callback) {
            throw new RuntimeException("Stub!");
        }
    }
    
这样之后就可以直接调用来获取系统属性