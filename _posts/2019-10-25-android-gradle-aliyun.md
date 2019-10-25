---
layout:     post
title:      "使用国内镜像加速gradle下载依赖"
subtitle:   "使用国内镜像加速gradle下载依赖"
date:       2019-10-25
tags:
    - gradle
---

[公共代理库](https://help.aliyun.com/document_detail/102512.html?spm=a2c40.aliyun_maven_repo.0.0.36183054ixVFam)

## 简介

[maven.aliyun.com](maven.aliyun.com)代理了很多公共的maven仓库。

使用[maven.aliyun.com](maven.aliyun.com)中的仓库地址作为下载源，速度更快更稳定。

## 使用

* `public`仓库是`central`仓和`jcenter`仓的聚合仓库，因此没必要再添加`jcenter`了
    
    
        buildscript {
            repositories {
                maven { url 'https://maven.aliyun.com/repository/google' }
                maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
                maven { url 'https://maven.aliyun.com/repository/public/' }
            }
            dependencies {
                classpath 'com.android.tools.build:gradle:version'
            }
        }
        allprojects {
            repositories {
                maven { url 'https://maven.aliyun.com/repository/google' }
                maven { url 'https://maven.aliyun.com/repository/public/' }
            }
        }



