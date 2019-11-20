---
layout:     post
title:      "记重装MAC的流程以及需要安装的软件"
subtitle:   "记下来避免忘记"
date:       2019-11-20
tags:
    - mac
---

* 一台可联网的MAC
* 一块硬盘（可无，备份用）
* 系统盘：还是建议做个备份盘，在线恢复是连接美国的服务器，比较慢。

#### 做系统盘

catalina

    sudo /Applications/Install\ macOS\ Catalina.app/Contents/Resources/createinstallmedia --volume /Volumes/MyVolume 

* [创建可引导的 macOS 安装器](https://support.apple.com/zh-cn/HT201372)

#### 启动到恢复界面：

关机状态下按下电源按钮，开始重新启动之后，立即在键盘上按住` option ` ,直到硬盘标志出来选择制作好的安装器盘符

* [关于 macOS 恢复功能](https://support.apple.com/zh-cn/HT201314)

然后按照指示，重装的时候选择系统盘重装，至于格盘，看个人选择

这里建议重装系统完成之后首先把隐藏的文件显示出来:

	显示：defaults write com.apple.finder AppleShowAllFiles -bool true; 
	     KillAll Finder
	隐藏：defaults write com.apple.finder AppleShowAllFiles -bool false;
	     KillAll Finder

## xcode

> 如果不安装的话，安装`brew`的时候会自动安装命令行工具

命令行工具：`xcode-select --install`

## [shadowsocksx-ng-r](https://github.com/qinyuhang/ShadowsocksX-NG-R)

[download](https://github.com/qinyuhang/ShadowsocksX-NG-R/releases/download/1.4.4-r8/ShadowsocksX-NG-R8.dmg)

## 基础安装

#### [brew](https://github.com/Homebrew/brew)

安装[brew.sh](https://brew.sh/index_zh-cn.html)

    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

`brew --prefix`:确定brew安装位置

`brew --cacche`:确定缓存位置

`brew doctor`:为了确认brew运行时，对系统中各个目录时候有权限等问题，可执行命令诊断

以 wget 为例：

	查找软件包
	brew search wget
	
	安装软件包
	brew install wget
	
	列出已安装的软件包
	brew list
	
	删除软件包
	brew remove wget
	
	卸载软件
	brew uninstall wget
	
	搜索
	brew search git  
	
	更新 brew
	brew update 
	
	查看软件包信息
	brew info wget
	
	列出软件包的依赖关系
	brew deps wget
	
	更新brew
	brew update
	
	列出过时的软件包（已安装但不是最新版本）
	brew outdated
	
	更新过时的软件包（全部或指定）
	brew upgrade 或 brew upgrade wget
	
	清除下载缓存
	brew cleanup	
	
#### [homebrew-cask](https://github.com/caskroom/homebrew-cask)

搜索直接使用 `brew search` 即可

    brew cask install # 下载安装软件
    brew cask uninstall # 卸载软件
    brew cask info # 显示这个软件的详细信息，如果已经用cask安装了，也会显示其安装目录信息等
    brew cask list 列出本机按照过的软件列表

####  [brew cask upgrade](https://github.com/buo/homebrew-cask-upgrade)

    brew tap buo/cask-upgrade
    
    brew cu
    
    brew cu [CASK]

#### zsh

可选另外一种：[fish-shell](https://github.com/fish-shell/fish-shell)

catalina默认是zsh,无须再次配置

[oh-my-zsh](https://github.com/robbyrussell/oh-my-zsh)

    sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"

    or

    sh -c "$(wget https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh -O -)"

比较好的字体：[FiraCode](https://github.com/tonsky/FiraCode)

#### [iTerm2](http://www.iterm2.com/downloads.html)

	brew cask install iTerm2

[iTerm2-Color-Schemes](https://github.com/mbadolato/iTerm2-Color-Schemes)

#### 终端开启代理

mac终端默认不走代理，例如git clone 的时候就算开vpn 也会很慢，开启代理方式如下

以shadowsocksx为例，添加在 `~/.zshrc`：

	alias openvpn="export ALL_PROXY=socks5://127.0.0.1:1080" 
	alias offvpn="unset ALL_PROXY"

以后想打开代理  直接输入 `openvpen`,关闭输入 `offvpn` 即可

或者使用 `brew install proxychains-ng` ,安装好之后在 `/usr/local/etc/proxychains.conf` 下，在 `ProxyList` 下面加入代理类型，代理地址和端口

	socks5 127.0.0.1 1080
	
test:

	proxychains4 curl ip.cn
	or
	openvpn
	curl ip.cn

#### [the fuck](https://github.com/nvbn/thefuck)

	brew install thefuck
	
	eval $(thefuck --alias)
	eval $(thefuck --alias name) // 别名

#### [go2Shell](http://zipzapmac.com/Go2Shell)

	brew cask install go2Shell
	
可以直接添加在 Finder 上，点击直接在 shell 打开选中的文件夹目录。

按住`command`,然后拖动图标到`Finder`的状态栏或者打开自行安装

#### [vim](https://www.vim.org/)

系统自带，但是版本较低

终端：	
	
	brew install vim --with-lua --with-override-system-vi

GUI: 	

	brew install macvim --with-lua --with-override-system-vim

#### repo

	brew install repo
	
#### git

	git config --global user.name "user.name"
	git config --global user.email "user.email"

cd ~ 

ssh-keygen：

用`shadowsocks`加速 `git clone`

    git config --global http.proxy 'socks5://127.0.0.1:1080' 
    git config --global https.proxy 'socks5://127.0.0.1:1080'

#### [sublimetext](http://www.sublimetext.com/3)

	brew cask install sublime-text
	
#### [alfred](https://www.macupdate.com/app/mac/34344/alfred)

	brew cask install alfred
	
#### [python](https://www.python.org/)

	brew install python
	
#### [adoptopenjdk](https://github.com/AdoptOpenJDK)

	brew cask install adoptopenjdk8

#### [Flashlight](https://github.com/nate-parrott/Flashlight)

扩展系统自带搜索功能

#### [jekyll](https://github.com/jekyll/jekyll)

首先 git 必须安装

安装[brew](https://brew.sh/index_zh-cn.html)用来安装`ruby`

安装ruby(macos已经默认自带了ruby,但是建议使用自己安装的)

	brew install ruby

安装Jekyll

	sudo gem install jekyll
	
启动

	jekyll server

#### adb  gradle

* 路径如果害怕自己填写错误，可以直接拖动到 `.zshrc`

进入根目录的`.zshrc`,如果没有就自己创建

> sdk

	export PATH=${PATH}: sdk file
	
> gradle,这里配置AS里面的gradle,要有`\`对名称中的空格进行转意

gradle可以直接使用`brew`安装

	export PATH=${PATH}:/Applications/Android\ Studio.app/Contents/gradle/gradle-3.2/bin
	
#### vps

直接打开 `vps`，如果`git`配置了的话根目录会出现`.ssh`目录，如果没有就自行建立，
新建`config`，配置如下：

    Host                  name
    HostName              ip
    Port                  port
    User                  userName

以后链接`VPS`直接在命令行输入`ssh name`，就会提醒你输入密码链接

#### OS X  NTFS 移动硬盘中文件呈灰白色且无法读取

进入`Terminal`，在命令行输入 `xattr -d com.apple.FinderInfo  文件路径` 

#### OS X 读写 NTFS

> 其实可以借助软件，这里再介绍一种可行的方法

在`etc`目录下新建一个名为`fstab`的文件

	LABEL=DISK_NAME none ntfs rw,auto,nobrowse

	or

	UUID=DISK_UUID  none ntfs rw,auto,nobrowse

然后重新拔插，在`Volumes`可以找到该硬盘

#### brew 安装

    brew install aria2 cocoapods jenkins cmake curl-openssl ffmpeg go gradle mysql node p7zip php python repo ruby sbt thefuck tomcat unrar unzip watchman wget yarn you-get youtube-dl
    
#### brew cask 安装(不用VPN)

    brew cask install android-file-transfer android-studio google-chrome iina qq sogouinput tencent-lemon xiami youdaodict

#### brew cask 安装(需要VPN)

    brew cask install adoptopenjdk alfred charles cyberduck dozer go2shell intellij-idea istat-menus iterm2 jd-gui keka licecap macdown phpstorm react-native-debugger steam sublime-text touchswitcher vmware-fusion webstorm;
