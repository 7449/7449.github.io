---
layout:     post
title:      "Android多Module本地AAR"
subtitle:   "解决多Module之间本地AAR依赖问题"
date:       2020-3-11
tags:
    - maven
    - aar
    - android
---

## `Module`之间无依赖

* 在多`Module`项目中如果一开始各个`Module`都是以`ARouter`或者`EventBus`之类的联系,这样本地打`aar`是没有任何问题,因为`Module`
之间没有任何依赖,都是一个单独的模块,如果这样为了减少编译时间`Module`可以直接打成`aar`,具体如下:


    apply plugin: 'maven'
    
    uploadArchives {
        configuration = configurations.archives
        repositories {
            mavenDeployer {
                repository(url: "file:///$rootDir/../../maven")
                pom.project {
                    version VERSION
                    groupId GROUP_ID
                    artifactId ARTIFACT_ID
                    packaging PACKAGING"
                    description DESCRIPTION
                }
            }
        }
    }

这样直接在本地运行`uploadArchives`指令即可

## `Module`之间有依赖

* 如果多个`Module`之间相互依赖的话,打成`aar`使用的使用会遇到以下错误

    
    Error:Failed to resolve:LIBRARY_NAME:unspecified
    
提示找不到该依赖,具体有兴趣可以查看`aar`的`pom`文件,打`aar`的时候没有把代码打进去,而是直接依赖的`Module`名字,这样肯定会找不到需要的代码

解决问题的思路很简单,既然多出来了,那就在`pom`把它删掉就行了,手动也可以,但比较笨拙,这里提供一个简单的方法,代码如下所示:

    apply plugin: 'maven'
    
    uploadArchives {
        configuration = configurations.archives
        repositories {
            mavenDeployer {
                pom.whenConfigured { pomp ->
                    pomp.dependencies
                        .findAll { dep -> dep.groupId == rootProject.name }
                        .collect { dep -> pomp.dependencies.remove(dep) }
                }
                repository(url: "file:///$rootDir/../../maven")
                pom.project {
                    version VERSION
                    groupId GROUP_ID
                    artifactId ARTIFACT_ID
                    packaging PACKAGING"
                    description DESCRIPTION
                }
            }
        }
    }

通过打印可以知道

    android
    Dependency {groupId=android, artifactId=LIBRARY_NAME, version=unspecified, type=null}
    android
    Dependency {groupId=androidx.databinding, artifactId=databinding-common, version=3.5.3, type=null}

这样的话解决起来就很简单了,在`pom`里面把`groupId`等于`android`的通通删掉就行了,如果因为`android`名字害怕出现遗漏或者其他问题,也可以对比`artifactId`

总之只要在生成的时候把不需要的依赖删掉就行了.