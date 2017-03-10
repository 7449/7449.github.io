---
layout:     post
title:      "Android_Studio快捷键 for Mac"
subtitle:   "IDE 快捷键"
date:       2017-03-10
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - hotKty
---

> ⌘:command, ⌃:ctrl, ⇧:shift, ⌥:alt/option, ⏎:enter/return
	
## 保存:

	⌘s
      
 * 这个在AS中是自动保存的。即使关闭Tab页后，再次打开，`⌘z`还可以撤销编辑

## 打开Generate

	⌘n
 
 * getter、setter、toString、constructor...    
 
## 类层级

	⌃h   

## 搜索class

	⌘o

 * 再按一次`⌘o`会发现，右上角的选项勾选了，搜索结果可包含非project中的class
 
## 搜索file

	⌘⇧o

 * 再按一次`⌘⇧o`，搜索结果可包含非project中的flie

## 搜索属性（成员和静态，不论是否私有）

	⌘⌥o  

 * 再按一次，搜索结果可包含非project中的class

## 自动声明变量

	⌘⌥v

## quick fix（快速解决问题）

	⌥⏎

## 查看当前类成员

	⌘F12

 *      ` ⌘i` 显示/取消息匿名类 `⌘f12` 显示继承自父类、父接口的成员

## 选择能重写(override)或实现(implement)的方法

	⌃o

## 查找方法在哪被使用

	⌥F7

## 定位到属性、方法、类等它们的声明

	F4

## 查看父类的同名方法

	⌘U

## 查看接口方法实现类

	⌘⌥B 或 ⌘⌥click

## Surround With

	⌘⌥T

 *  if、while、try-catch、synchronized 等等

## 重构面板

	⌃T

## 抽取成方法

	⌘⌥M

## 抽取为成员属性

	⌘⌥F
## 将内部变量抽取成方法的参数

	⌘⌥P

## 去除无效引用

	^⌥O

## 历史打开过的文件

	⌘E

## 查找使用情况

	⌘⌥⇧F7

## 查找与替换

	⌘F，⌘R

 * 在查找后，使用` ⌘G ` 定位到下一个text

## find in path与replace in path

	⌘⇧F，⌘⇧R

## 大小写转换

	⌘⇧U

## 重命名

	⇧F6

## 光标换行

	⌘⇧⏎

## 在当前行上添加一行，光标定位到行首

	⌘⌥⏎ 

## 复制整行

	⌘D 

## 删除整行

	⌘delete

## 移动整个方法

	⇧⌘↑|↓ 

## 上下移动光标所在行

	⌥⇧↑|↓  

## 剪切

	⌘X 

## 查看doc/文档注释

	F1

## 定位到未使用的声明

	F2 

## 在当前行上添加一行，光标定位到行首

	⌘⌥⏎ 

## 手动提示

	⌘Space


## Tips:

	  在AS中，默认在输入的时候就自动提示了，想手动提示看一下，使用 ⌘Space
	  有时参数要填哪些类型，突然忘了，这时可以，光标在方法名上用F1查看doc注释；
	  或按住⌘，鼠标悬浮在方法名上；
	  或光标移到方法左括号之前，再用⌘Space
