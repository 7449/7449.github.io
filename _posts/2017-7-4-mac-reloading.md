---
layout:     post
title:      "记重装MAC的流程以及需要安装的软件"
subtitle:   "记下来避免忘记"
date:       2017-7-4
author:     "y"
header-mask: 0.3
header-img: "img/about-bg.jpg"
catalog: false
tags:
    - mac
---


## 准备：

* 一台可联网的MAC
* 一块硬盘（可无，备份用）
* 系统盘：还是建议做个备份盘，在线恢复是连接美国的服务器，比较慢。

tips: 硬盘可分出来 10G 左右，做成系统盘。

#### 做系统盘

App Store 搜索 ` macOS Sierra ` 下载完成之后退出，插上U盘


以下是命令的基本语法。

将 volumepath 替换为USB闪存驱动器或其他宗卷的相应路径，并将 installerpath 替换为“安装 OS X”应用的相应路径。

示例：

		createinstallmedia --volume volumepath --applicationpath installerpath

simple:

		sudo /Applications/Install\ macOS\ Sierra.app/Contents/Resources/createinstallmedia --volume /Volumes/diskName --applicationpath /Applications/Install\ macOS\ Sierra.app


* [创建可引导的 macOS 安装器](https://support.apple.com/zh-cn/HT201372)

#### 启动到恢复界面：

关机状态下按下电源按钮，开始重新启动之后，立即在键盘上按住` Command-R `

看到 Apple 标志或旋转的地球松开，看到“实用工具”窗口时，启动工程！

* [关于 macOS 恢复功能](https://support.apple.com/zh-cn/HT201314)

然后按照指示，重装的时候选择系统盘重装，至于格盘，看个人选择


## 重装完成

重装完成之后，开机什么都不要干，立马用硬盘做一次 `Time Machine`,留一个干净的系统备份，往后只要系统出问题，直接用备份恢复

这里建议首先把隐藏的文件显示出来:

	显示：defaults write com.apple.finder AppleShowAllFiles -bool true; 
	     KillAll Finder
	隐藏：defaults write com.apple.finder AppleShowAllFiles -bool false;
	     KillAll Finder

## 基础安装

#### 终端开启代理

mac终端默认不走代理，例如git clone 的时候就算开vpn 也会很慢，开启代理方式如下

* zsh


		openvpn () {
		  export http_proxy="http://127.0.0.1:8888"
		  export https_proxy="http://127.0.0.1:8888"
		  echo "HTTP Proxy on"
		}
		
		
		offvpn () {
		  unset http_proxy
		  unset https_proxy
		  echo "HTTP Proxy off"
		}



以后想打开代理  直接输入 openvpen,关闭输入 offvpn 即可

* 默认

可以从 Surge Mac 菜单里选择「Copy Shell Export Command」，然后粘贴到终端来打开代理


#### xcode

直接在AppStore安装即可，

命令行工具：`xcode-select --install`

#### brew

~~[GitHubAddress](https://github.com/Homebrew/legacy-homebrew)~~ ：被废弃

[homebrew-core](https://github.com/Homebrew/homebrew-core)

[brew](https://github.com/Homebrew/brew)


安装[brew.sh](https://brew.sh/index_zh-cn.html),往后 只要是 brew 有的软件，尽量使用 brew 安装或者更新卸载。例如：git,java,google-chrome

建议直接执行官网提供的命令,`/usr/local` 已在系统 PATH 之中，brew 无须任何配置。

如果想自定义安装地址：

	git clone https://github.com/Homebrew/legacy-homebrew.git
	
添加到环境变量

	export PATH=${PATH}:brewAddress/bin
	
执行`source .bash_profile` 使其即时生效

如果使用的是zsh，执行`source ~/.zshrc`


`brew --prefix` :确定 brew 安装位置

`brew doctor`:为了确认 brew 运行时，对系统中各个目录时候有权限等问题，可执行命令诊断


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
	brew search git  //模糊搜索 brew 支持的软件。如果不加软件名，会列出所有它支持的软件。
	
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
	
	
安装好的 wget 会被安装到 `/usr/local/Cellar/wget/`下。并且将wget命令软链接至` /usr/local/bin` 目录下。这样全局就都可以使用 wget 命令了
	
如果 brew 没有想装的软件，可以自己设置：

首先找到待安装软件的源码下载地址
	
	softwareAddress

建立自己的formula

	brew create softwareAddress

编辑formula

	上一步建立成功后，brew 会自动打开新建的formula进行编辑，也可用`brew edit softwareName` 打开formula进行编辑。

brew 自动建立的 formula 已经包含了基本的configure和make install命令，对于大部分软件，不需要进行修改，退出编辑即可。

然后直接输入`brew install softwareName`安装自定义的软件包

#### brew cask

[homebrew-cask](https://github.com/caskroom/homebrew-cask):github地址，下载软件可在里面自行搜索，没有或者版本较低的可 pull requests;

* brew install caskroom/cask/brew-cask

		brew cask install # 下载安装软件
		brew cask uninstall # 卸载软件
		brew cask search # 模糊搜索软件，如果不加软件名，就列出所有它支持的软件
		brew cask info # 显示这个软件的详细信息，如果已经用cask安装了，也会显示其安装目录信息等
		brew cask list 列出本机按照过的软件列表
		brew cask cleanup #  清除下载的缓存以及各种链接信息
		brew update && brew upgrade brew-cask # 更新cask自身


brew cask 是在 brew 的基础上一个增强的工具，用来安装Mac上的Gui程序应用包（.dmg/.pkg）, 比如qq。

它先下载解压到统一的目录中（/opt/homebrew-cask/Caskroom），省掉了自己去下载、解压、安装，

同样，卸载相当容易与干净。

然后再软链到~/Applications/目录下, 非常方便，而且还包含很多在 AppStore 里没有的常用软件。

#### the fuck

[GitHubAddress](https://github.com/nvbn/thefuck)

简单来说就是当输入命令行时，可自行提示你是不是输入某一个参数，举个例子：git stat,这个时候肯定出错，如果再输入 fuck，他会主动提示你，当然 fuck 也可以改成自己习惯的别名.

brew安装：

	brew install thefuck
	
eval $(thefuck --alias)
eval $(thefuck --alias name) // 别名


#### zsh

可选另外一种：[fish-shell](https://github.com/fish-shell/fish-shell)

zsh替代bash后，以前~/.bash_profile的配置应该写入~/.zshrc中

系统自带了 zsh,但是不是最新版的，如果想使用最新版可自行下载：

		brew install zsh
		
安装完成后，新版的zsh是安装到`/usr/local/bin`下的,修改`/etc/shells`,在最下面重启一行写入`/usr/local/bin/zsh`

改完之后执行`chsh -s /usr/local/bin/zsh` 就OK了
		
系统自带：

		chsh -s /bin/zsh
		
重启terminal


* [oh-my-zsh](https://github.com/robbyrussell/oh-my-zsh)

zsh复杂的配置使人望而却步，所以 oh-my-zsh 应运而生..

比较好的字体：[FiraCode](https://github.com/tonsky/FiraCode)


#### iTerm2

	brew cask install iTerm2
	
官方地址：[iTerm2](http://www.iterm2.com/downloads.html)

[iTerm2-Color-Schemes](https://github.com/mbadolato/iTerm2-Color-Schemes)


#### go2Shell

	brew cask install go2Shell
	
可以直接添加在 Finder 上，点击直接在 shell 打开选中的文件夹目录。

如果使用 iTerm2,不要使用 App Store版，版本过旧，不会主动跳转到 相应的文件路径


#### Vim

系统自带，但是版本较低

终端：	
	
	brew install vim --with-lua --with-override-system-vi

GUI: 	

	brew install macvim --with-lua --with-override-system-vim


		
#### repo

可选，AOSP同步需要

repo由一系列python脚本组成

	mkdir ~/bin  //创建bin目录
	PATH=~/bin:$PATH  //添加到环境变量
	curl https://storage.googleapis.com/git-repo-downloads/repo > ~/bin/repo  //安装repo
	chmod a+x ~/bin/repo  //将repo的权限改成可执行
	
这个时候你要在.bash_profile里面看下repo的环境变量是否添加成功，如果没有，请自行添加：

		export PATH=${PATH}:/Users/userName/bin
	
	
#### git

XCODE自带


git config --global user.name "user.name"
git config --global user.email "user.email"
cd ~ 

ssh-keygen：


* 用shadowsocks加速 git clone


		git config --global http.proxy 'socks5://127.0.0.1:1080' 
		git config --global https.proxy 'socks5://127.0.0.1:1080'


#### sublimetext

[sublimetext](http://www.sublimetext.com/3)

	brew cask install sublime-text
	
	
#### Alfred

[alfred](https://www.macupdate.com/app/mac/34344/alfred)

	brew cask install alfred
	
#### python

	brew install python
	
#### jdk

	brew cask install java

	
#### Flashlight

[Flashlight](https://github.com/nate-parrott/Flashlight)

扩展系统自带搜索功能
		
		
## 安装软件


[dr.unarchiver](https://itunes.apple.com/cn/app/dr.-unarchiver-rar-zip-archive/id1127253508?l=en&mt=12):解压工具<br>
[dr.cleaner](https://itunes.apple.com/cn/app/dr.cleaner-qing-li-ci-pan/id921458519?mt=12)：清理工具<br>
[licecap](http://www.cockos.com/licecap/):录制视频工具<br>
[Android File Transfer](https://www.android.com/filetransfer/):android与mac传输文件<br>
[Aria2GUI](https://github.com/yangshun1029/aria2gui):Aria2图形化界面，配合[BaiduExporter使用](https://github.com/acgotaku/BaiduExporter)<br>
[skim](http://skim-app.sourceforge.net/):阅读PDF<br>
[touchswitcher](https://hazeover.com/touchswitcher.html?ref=producthunt):自定义touchbar<br>
[macdown](http://macdown.uranusjr.com/):编辑MD文件<br>
[iina](https://github.com/lhc70000/iina):视频播放器<br>
[Duet](https://www.duetdisplay.com/cn/):利用ipad<br>
[iStat Menus](https://bjango.com/mac/istatmenus/):查看系统信息<br>
[WebStorm](http://www.jetbrains.com/webstorm/)<br>
[IDEA](http://www.jetbrains.com/idea/)<br>
[AndroidStudio](https://developer.android.com/studio/index.html?hl=zh-cn)<br>
[mumu](http://mumu.163.com/):网易出品的android模拟器,非常不错<br>
[charles](https://www.charlesproxy.com/):抓包神器<br>
[Chrome](https://www.google.com/chrome/browser/desktop/index.html)<br>
[qq](https://im.qq.com/download/):QQ<br>
[Wechat](https://weixin.qq.com/):微信<br>
[shadowsocks](https://github.com/shadowsocks):vps<br>
[Mac BiliBili客户端](https://github.com/typcn/bilibili-mac-client):bilibili客户端<br>
[Kindle](https://itunes.apple.com/us/app/kindle/id405399194?mt=12):kindle<br>
[wangyiMusci](https://music.163.com/):网易云音乐<br>
[Evernote](https://www.yinxiang.com/):印象笔记<br>
[kcptun_xclient](https://github.com/dfdragon/kcptun_xclient):kcptun Mac客户端<br>
[xunlei](http://www.xunlei.com/):迅雷<br>
[steam](http://store.steampowered.com/):steam<br>
[calibre](https://calibre-ebook.com/):转换Kindle文件格式<br>
[youdao](https://www.youdao.com/):有道词典<br>
[Dash](https://kapeli.com/dash):查阅Api<br>







