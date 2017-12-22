---
layout:     post
title:      "ObjectBox,目前Android上速度最快的数据库操作库"
subtitle:   "从greenDao转objectBox"
date:       2017-9-28
author:     "y"
header-mask: 0.3
header-img: "img/android.png"
catalog: true
tags:
    - android
    - sql
---


## update

objectBox 生成的`objectbox-models/default.json`，要一直随着代码更新，但不能删除重新更近，否则会导致`UID`不一致而崩溃

跟随git commit记录一起保存起来最好

## 前言

 今晚得知	`Android`著名的数据库`ORM`操作框架`greenDao`的作者推出了一个全新的面向对象数据库

 由于之前一直在使用`greenDao`，所以心里比较激动，二话不说在github上面找到主页，大概看了下操作流程以及`sample`，决定自己动手写个`Demo`尝试下

 但是在使用代理的情况下，`gradle`死活都拉不下来相关依赖，你能想象隔壁网页油管播放着`1080P`的视频，但是这边却拉不下极小的依赖的时候绝望的心情吗？

 心里把`GFW`操了无数遍，折磨了好一会，激动的心情都慢慢的被磨灭了，着实让人恼火

 最后在不使用代理的情况下，虽然很慢，但是慢慢的还是把相关依赖拉了下来，实在是让人摸不着头脑，还有这种操作？


## 介绍

 github:[objectbox](https://github.com/objectbox)

 [主页](http://objectbox.io/)

 [blog](http://objectbox.io/blog/)

 主页上有这么一张图，展示了`Sqlite`以及`objectbox`的速度，当然了，在存在`greenDao`这么优秀的框架的情况下，还会推出`objectbox`

 我也应该相信`objectBox`的效率！！

 ![_config.yml]({{ site.baseurl }}/img/objectbox-sqlite.png)


 截止今天(17.9.28),`objectBox`仅支持`JAVA` `Android`，现在刚发布`1.x`版本，以后会慢慢完善其他平台

 在其主页上写着

	ObjectBox is designed for mobile. 
	It is an object-oriented embedded database and a full alternative for SQLite. 
	ObjectBox is incidentally also well-suited for IoT.


面向对象的嵌入式数据库和SQLite的一个完整的替代方案，由此可见`ObjectBox` 是`SQLite`的另一个可替换的更换品，并且比`sqlite`效率更高！

为什么说又呢？ 因为目前已经存在了类似的框架：[realm](https://github.com/realm/)


之前也写过`greenDao`相关的Blog，有兴趣的可以看看：[Android_greenDaoCRUD](https://7449.github.io/2016/10/08/Android_greenDaoCRUD/)

里面涉及了，3.x 2.x 的代码，以及多表关联，操作外部数据库


## 代码地址

[https://github.com/7449/AndroidDevelop/tree/master/objectBox](https://github.com/7449/AndroidDevelop/tree/master/objectBox)

[https://github.com/7449/AndroidDevelop/tree/master/objectBoxMultiTable](https://github.com/7449/AndroidDevelop/tree/master/objectBoxMultiTable)

## 准备工作

开始着手第一个`ObjectBox`Demo，激动...


新建一个项目在其根目录的`project`的`build.gradle`添加如下所示：


	buildscript {
	    ext.objectboxVersion = '1.0.1'
	    repositories {
	        maven { url "http://objectbox.net/beta-repo/" }
	    }
	    dependencies {
	        classpath "io.objectbox:objectbox-gradle-plugin:$objectboxVersion"
	    }
	}

	allprojects {
	    repositories {
	        maven { url "http://objectbox.net/beta-repo/" }
	    }
	}


然后在`Module`的`build.gradle`也就是`app`
目录下的`build.gradle`里面添加`plugin`

	apply plugin: 'io.objectbox'

同步一下，等待同步完成之后项目就已经成功的依赖了`objectBox`

如果需要`Rx`的支持，则需要添加

    compile "io.objectbox:objectbox-daocompat:1.0.1"

`kotlin`则需要

    compile "io.objectbox:objectbox-kotlin:1.0.1"


## Entity

通过阅读源码可以发现`ObjectBox`和`greenDao3.x`的代码在使用上十分相似，都是通过注解的方式去自动生成相关文件

这里新建一个`Entity`

	@Entity
	@NameInDb("SampleObjectBoxEntity")
	public class ObjectBoxEntity {

	    @Id
	    private long id;
	    @NonNull
	    private String name;
	    private int age;

	    public ObjectBoxEntity(long id, @NonNull String name, int age) {
	        this.id = id;
	        this.name = name;
	        this.age = age;
	    }

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    @NonNull
	    public String getName() {
	        return name;
	    }

	    public void setName(@NonNull String name) {
	        this.name = name;
	    }

	    public int getAge() {
	        return age;
	    }

	    public void setAge(int age) {
	        this.age = age;
	    }
	}

建好之后重新`build`一下项目，就可以看见在`app/buil/generated/source/apt/debug/com.objectbox`目录下生成了相关文件

这里需要注意的是`greenDao3.x`是自动生成`get` `set`以及构造方法的，但是作者在当前版本使用中发现，相关方法并没有自动生成，需要使用者手动写

或者将所有字段设置为`public`即可，`objectbox`就可以识别了

由于`Entity`过于简单。因此生成的文件也非常简单，容易阅读，这里建议初次使用的过程中可以通过阅读自动生成的代码来增加熟悉感

主要操作就在自动生成的`MyObjectBox`类

## 增删改查

作者这里并没有通过在`Application`里面获取`BoxStore`对象，而是抽取了出来

其实效果是相同的，都是单例

	public class ObjectBoxUtils {

	    private ObjectBoxUtils() {
	    }


	    public static BoxStore get() {
	        return ObjectBoxHolder.BOX_STORE_BUILDER;
	    }

	    private static class ObjectBoxHolder {
	        private static final BoxStore BOX_STORE_BUILDER = MyObjectBox.builder().androidContext(App.getContext()).build();
	    }

	}

首先要获取`Box`对象


> 主要的操作都在`Box`类里面，而且代码看起来非常清晰，建议通过阅读来了解如何增删改查

	BoxStore boxStore = ObjectBoxUtils.getBoxStore();
	objectBoxEntityBox = boxStore.boxFor(ObjectBoxEntity.class);

* 增：

	objectBoxEntityBox.put();

* 删：

	objectBoxEntityBox.removeAll();
	objectBoxEntityBox.remove();

* 改：

	objectBoxEntityBox.put();

* 查：

> 可以直接返回全部数据，也可以根据条件插件，分页

	objectBoxEntityBox.getAll();
	objectBoxEntityBox.get();

	QueryBuilder<ObjectBoxEntity> query = objectBoxEntityBox.query();
    Query<ObjectBoxEntity> build = query.order(ObjectBoxEntity_.name).build();
    build.find();

## Dao

在 `build.gradle` 添加

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = ['objectbox.daoCompat': 'true']
            }
        }
    }

并且要依赖

    compile "io.objectbox:objectbox-daocompat:1.0.1"

    或者

    compile "org.greenrobot:objectbox-daocompat:1.1.0"



截止`1.0.1`版本。如果要生成`Dao`，`io.objectbox:objectbox-daocompat` 使用什么版本，`objectBox`也必须要使用什么版本，否则会因为包名的问题找不到`AbstractDao`

因为在`1.1.0`版本的时候作者发现`objectbox`生成的`dao`找的是`org.greenrobot.daocompat.AbstractDao`而不是`io.objectbox.daocompat.AbstractDao`,但是`io.objectbox`并没有更新到`1.1.0`


重新`build`一下项目就会发现生成了熟悉的`Dao`类，这里要注意的是

目前为止，如果使用`Dao`操作数据表，则需要生成`get` `set` 方法，生成的类里面需要使用


## 多表

> 此问题已得到修复

~~多表目前作者发现操作时不能生成`Dao`，否则会报错~~

~~Error:[ObjectBox] Code generation failed: The following has evaluated to null or missing:~~

~~作者提了一个[Issues](https://github.com/objectbox/objectbox-java/issues/197)~~

~~开发者的回答是会在`1.1.1`版本修复此问题~~

多表和`greenDao`，操作还是有些变化，不通过注解，而是泛型去生成

例如：

	@Entity
	public class SchoolEntity {

	    @Id
	    long id;

	    ToMany<StudentEntity> student;
	}


	@Entity
	public class StudentEntity {

	    @Id
	    long id;
	    String name;
	}

如果相互关联可以使用`Backlink`注解

相关代码也已提交到`GitHub`上

## Rx 

~~太晚了，等早上有空了再总结一下，否则要迟到了...~~

        Query<ObjectBoxEntity> build = objectBoxEntityBox.query().build();
        DataSubscription observer = 
        build.subscribe()
                .on(AndroidScheduler.mainThread())
                .onError(new ErrorObserver() {
                    @Override
                    public void onError(@NonNull Throwable th) {

                    }
                })
                .observer(new DataObserver<List<ObjectBoxEntity>>() {
                    @Override
                    public void onData(@NonNull List<ObjectBoxEntity> data) {
                    }
                });


        if (observer != null && !observer.isCanceled()) {
            observer.cancel();
        }


## 总结

写个Demo,感受就是像，操作数据库的动作和`greenDao`很像，几乎都一样，相信如果熟悉`greenDao`的人会很容易上手`ObjectBox`

目前第一个正式版已经出来了，如果想正式应用的话，看下github的`lssues`，如果没有涉及到自己的相关问题，可以改为`ObjectBox`，反正我决定就他了！！






