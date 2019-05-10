---
layout:     post
title:      "Android_编译Android源码并使用AS查看源码"
subtitle:   "编译Android源码并使用AS查看源码"
date:       2017-2-10
tags:
    - android
---

## 前言

之前在[ubuntu16.04](http://www.ubuntu.org.cn/index_kylin)系统下编译过Aosp，但是ubuntu是Mac开虚拟机弄的，最后还是选择在Mac继续编译Aosp;

在本章末尾会总结下如何在 `Ubuntu` 系统编译Aosp，但本篇文章主要的开发环境为MacOs,Win系统下也可以编译Aosp，但是不推荐，所以这里也不过多叙说<br>

## 准备工作

#### repo

AOSP项目由不同的子项目组成,为了方便进行管理,Google采用Git对AOSP项目进行多仓库管理
repo由一系列python脚本组成,通过调用Git命令实现对AOSP项目的管理

#### 必须安装的软件：<br>

[xcode](https://developer.apple.com/cn/xcode/)<br>
[macports](https://www.macports.org/install.php)<br>
[java](https://www.java.com/zh_CN/download/)<br>

#### 创建大小写敏感的磁盘

因为Mac默认磁盘不区分大小写，所以要创建一个大小写敏感的磁盘以避免不必要的麻烦<br>

打开 磁盘工具 - 文件 - 新建映像

然后创建AndroidAosp磁盘，建议大小在100GB左右，但是宁可多，不要少，格式则选择 Mac OS 扩展(区分大小写,日志式)，其他的默认<br>

![_config.yml]({{ site.baseurl }}/assets/screenshot/17/aosp_disk.png)

这里建议首先把隐藏的文件显示出来:

	显示：defaults write com.apple.finder AppleShowAllFiles -bool true; 
	     KillAll Finder
	隐藏：defaults write com.apple.finder AppleShowAllFiles -bool false;
	     KillAll Finder

#### 安装make,git,GPG

1.  首先安装[macports](https://www.macports.org/install.php)，可以选择安装包安装或者直接下载Macport，解压之后终端进入该目录，输入`./configure make sudo make install`进行安装

2.  打开 `.bash_profile` 文件，此文件在`你的用户名`文件夹下,例如我的用户名是`test`,那么这个文件就是`test`文件夹下，看里面是否有`export PATH="/opt/local/bin:/opt/local/sbin:$PATH"`,没有就加上,修改后在终端输入`source .bash_profile` 让其即时生效

3.  打开终端，输入`POSIXLY_CORRECT=1 sudo port install gmake libsdl Git gnupg`,等自动下载完成后如果提示`gmake not found`,就输入`sudo port -d sync` 同步一下，如果提示`port not found`请检查环境变量


## 下载源码

推荐在[清华大学开源镜像 Android 镜像](https://mirrors.tuna.tsinghua.edu.cn/help/AOSP/)下载<br>

1.  下载repo

		mkdir ~/bin  //创建bin目录
		PATH=~/bin:$PATH  //添加到环境变量
		curl https://storage.googleapis.com/git-repo-downloads/repo > ~/bin/repo  //安装repo
		chmod a+x ~/bin/repo  //将repo的权限改成可执行

	这个时候你要在`.bash_profile`里面看下`repo`的环境变量是否添加成功<br>


2.  下载Aosp初始化包：[aosp-latest]( https://mirrors.tuna.tsinghua.edu.cn/aosp-monthly/aosp-latest.tar)
	
	大小约为20G左右，下载完成后，解压至大小写敏感的磁盘，用终端进入该目录，在确保`repo`环境变量成功的情况下执行`repo sync`一遍即可得到完整的源码<br>
	
	同步过程中有可能会因为网络的问题中断，不确定的可以多执行`repo sync`几次以确保完整的同步了下来<br>
	
	以后每次只需要`repo sync`就可保持同步，建议每天同步，并且在凌晨<br>


## 编译源码


如果只是想通过IDE工具查阅Android代码,这步可以跳过去,使用我之前编译好的[idegen](https://github.com/7449/AndroidDevelop/blob/master/idegen.jar)，即可避免编译源码这个步骤

>提示：Android编译的Java版本要和aosp/build/core中的`main.mk`中的设置Java版本对应


JDK设置参考：

修改`.bash_profile`
	
		export JAVA_6_HOME= java6路径
		export JAVA_7_HOME= java7路径
		export JAVA_8_HOME= java8路径
		
		export JAVA_HOME=$JAVA_6_HOME
		alias jdk8='export JAVA_HOME=$JAVA_8_HOME'
		alias jdk7='export JAVA_HOME=$JAVA_7_HOME'
		alias jdk6='export JAVA_HOME=$JAVA_6_HOME’

这样以后终端输入jdk6、jdk7、jdk8 就可以切换jdk版本


>接下来操作皆在Aosp目录下完成

1.  终端输入 `source build/envsetup.sh`

2.  选择需要编译的目标，我这里选择 `aosp_arm-eng` ，执行命令 `lunch aosp_arm-eng`

3.  输入 `make` 或者 `make -j8` 开始编译,这里 `-j` 的参数一般是cpu核心*2


如果不出意外，等半个小时左右就可以编译完成<br>

## 运行模拟器

如果在编译完后立刻运行虚拟机直接执行`emulator`即可<br>

一般启动方式如下：

	source build/envsetup.sh
	lunch(你设置的目标版本,例如我上面的aosp_arm-eng是1)
	emulator

#### 编译模块

例如要编译Launcher2 App, 执行命令 `mmm packages/apps/Launcher2/` ,aosp的app源码都在`packages/apps`目录下

#### 重新打包镜像

如果想要将编译好的apk集成到系统镜像中,需要借助`make snod`指令重新打包系统镜像,模拟器重启生效

#### sdk编译

`make sdk`

## 导入Android Studio

1.  确保`aosp/out/host/darwin-x86/framework`目录中没有`idegen.jar`的情况下在终端中输入`mmm development/tools/idegen`即可看见生成了`idegen.jar`

2.  接着输入`development/tools/idegen/idegen.sh` 执行完之后Aosp目录下会生成`android.ipr` 和 `android.iml` 

使用AndroidStudio打开`android.ipr`即可导入源码工程


> idegen.jar

下载地址：[idegen](https://github.com/7449/AndroidDevelop/blob/develop/aosp/idegen.jar)

编译`AOSP`源码生成的jar，如果想把源码导入`AndroidStudio`或者`IDEA`，有了这个jar，就不必需要花费近乎一个小时的时间去重新编译源码，而可以直接导入IDE

请把这个jar包放在 `out` --> `host` --> `darwin-x86` --> `framework`,然后执行`development/tools/idegen/idegen.sh`生成`android.ipr` and `android.imi`,用于导入IDE

如果没有编译过源码是没有 `out` 目录的，请一层一层的新建文件夹，直到新建到`framework`，放进去就OK

`out` 目录与 `.repo` 平级

## Android Studio查看源码

我导入之后的列表：

![_config.yml]({{ site.baseurl }}/assets/screenshot/17/aosp_list.png)


第一次导入`android.ipr`会很慢，甚至会卡，因为同步的东西比较多，请静静等待即可，如何不想加载某项，可以在Modules中右键 Excluded 排除掉，路径为： File/Project Structrue/Modules，这样可以加快加载速度<br>

![_config.yml]({{ site.baseurl }}/assets/screenshot/17/aosp_modules.png)


建议在 setting - Build,Execution,Deployment - compiler 下 把 Build process heap size(Mbytes) 的默认值设置的大一些，避免编译时内存溢出<br>

![_config.yml]({{ site.baseurl }}/assets/screenshot/17/aosp_compiler.png)

一般查看的都在 `frameworks` 和 `packages` 目录下<br>

这个时候就可以看一些系统的源码了，例如直接搜索RecyclerView，跳转进去直接就可以跟着源码走了<br>

packages目录下是aosp的自带app，可以借鉴Google工程师是如何开发app的

## BUILD

编译目标的格式:BUILD-BUILDTYPE,比如上面的full-eng的BUILD是full,BUILDTYPE是eng.

BUILD指的是特定功能的组合的特定名称,即表示编译出的镜像可以运行在什么环境.<br>
其中,aosp(Android Open Source Project)代表Android开源项目;<br>
arm表示系统是运行在arm架构的处理器上,arm64则是指64位arm架构;处理器,x86则表示x86架构的处理器;<br>
此外,还有一些单词代表了特定的Nexus设备

|受型号	 	|设备代码	|编译目标
|---		|----		|---
|Nexus 6P	|angler		|aosp_angler-userdebug
|Nexus 5X	|bullhead	|aosp_bullhead-userdebug
|Nexus 6	|shamu		|aosp_shamu-userdebug
|Nexus 5	|hammerhead |aosp_hammerhead-userdebug

#### BUILDTYPE

BUILD TYPE则指的是编译类型,通常有三种:

-user:代表这是编译出的系统镜像是可以用来正式发布到市场的版本,其权限是被限制的(如,没有root权限,不能dedug等)

-userdebug:在user版本的基础上开放了root权限和debug权限.

-eng:代表engineer,也就是所谓的开发工程师的版本,拥有最大的权限(root等),此外还附带了许多debug工具


## 常见的错误

> You are attemping to build with the incorrect version
		
	安装openjdk 8

> Out of memory error

在编译命令之前,修改`prebuilts/sdk/tools/jack-admin`文件,找到文件中的这一行:
	
		JACK_SERVER_COMMAND="java -Djava.io.tmpdir=$TMPDIR $JACK_SERVER_VM_ARGUMENTS -cp $LAUNCHER_JAR $LAUNCHER_NAME"
	
	
然后在该行添加`-Xmx4096m`<br>
例如:
	
		JACK_SERVER_COMMAND="java -Djava.io.tmpdir=$TMPDIR $JACK_SERVER_VM_ARGUMENTS -Xmx4096m -cp $LAUNCHER_JAR $LAUNCHER_NAME"
	
	
然后再执行time make

>使用emulator时,虚拟机停在黑屏界面,点击无任何响应.此时,可能是kerner内核问题

	./out/host/linux-x86/bin/emulator -partition-size 1024 -kernel ./prebuilts/qemu-kernel/arm/kernel-qemu-armv7

如果还不能解决黑屏编译其他的试试

## Ubuntu下源码的编译

#### 安装git

		sudo apt-get install git 
		git config –global user.email “test@test.com” 
		git config –global user.name “test”

#### repo

repo安装方法和Mac一样

#### 下载源码

[aosp-latest]( https://mirrors.tuna.tsinghua.edu.cn/aosp-monthly/aosp-latest.tar)

`repo sync` 多同步几次源码确保完全拉下来了<br>

#### 构建编译环境

1.  硬件要求
 	
	64位的操作系统只能编译2.3.x以上的版本,如果你想要编译2.3.x以下的,那么需要32位的操作系统.
	如果想要在是在虚拟机运行linux,至少需要16GB的RAM/swap.

2.  软件要求

	Android版本						|编译的Ubuntu最低版本
	---    							|---   						
	Android 6.0至AOSP master   		|Ubuntu 14.04
	Android 2.3.x至Android 5.x		|Ubuntu 12.04
	Android 1.5至Android 2.2.x		|Ubuntu 10.04

3.  JDK要求

	Android版本						|编译的JDK版本
	---    							|---
	AOSP的Android主线   				|OpenJDK 8
	Android 5.x至android 6.0   		|OpenJDK 7
	Android 2.3.x至Android 4.4.x		|Oracle JDK 6
	Android 1.5至Android 2.2.x		|Oracle JDK 5

	我编译的环境为 Ubuntu 16.04 因此需要安装OpenJDK 8 `sudo apt-get install openjdk-8-jdk`
	
	如果是Ubuntu 14.04，编译主线代码也要安装OpenJDK 8，这个时候执行如下命令：
	
		sudo apt-get update
		sudo apt-get install openjdk-8-jdk
	
	如果要安装openjdk7，需要先设置ppa:
	
		sudo add-apt-repository ppa:openjdk-r/ppa 
		sudo apt-get update
	
	然后执行安装命令 
	
		sudo apt-get install openjdk-7-jdk
	
	关于Ubuntu切换不同的Java版本可用以下命令：
	
		sudo update-alternative --config java
		sudo update-alternative --config javac


4.  安装必要的依赖

	[官方构建编译指南](https://source.android.com/source/initializing.html)中只说明了Ubuntu14.04,Ubuntu 12.04,Ubuntu 10.04需要添加的依赖，但没有说明Ubuntu16.04的依赖<br>
	以下为Ubuntu16.04中需要的依赖(有几个重复的，不妨碍)

		sudo apt-get install libx11-dev:i386 libreadline6-dev:i386 libgl1-mesa-dev g++-multilib 
		sudo apt-get install -y git flex bison gperf build-essential libncurses5-dev:i386 
		sudo apt-get install tofrodos python-markdown libxml2-utils xsltproc zlib1g-dev:i386 
		sudo apt-get install dpkg-dev libsdl1.2-dev libesd0-dev
		sudo apt-get install git-core gnupg flex bison gperf build-essential  
		sudo apt-get install zip curl zlib1g-dev gcc-multilib g++-multilib 
		sudo apt-get install libc6-dev-i386 
		sudo apt-get install lib32ncurses5-dev x11proto-core-dev libx11-dev 
		sudo apt-get install libgl1-mesa-dev libxml2-utils xsltproc unzip m4
		sudo apt-get install lib32z-dev ccache

#### 编译源码

和Mac大同小异

`source build/envsetup.sh` <br>

`lunch aosp_arm-eng ` <br>

输入 `make` 或者 `make -j8` 开始编译 <br>





