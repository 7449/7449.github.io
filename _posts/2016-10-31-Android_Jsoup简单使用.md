---
layout: post
title: Android_Jsoup简单使用
---

jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。
它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。


![_config.yml]({{ site.baseurl }}/images/jsoupsimple.gif)

代码示例：[https://github.com/7449/AndroidDevelop/tree/master/JsoupSimple](https://github.com/7449/AndroidDevelop/tree/master/JsoupSimple)


>Jsoup对js动态加载抓取比较困难，如果想抓取请使用其他的工具，不建议使用Jsoup

jsoup的主要功能如下

	1. 从一个URL，文件或字符串中解析HTML；
	2. 使用DOM或CSS选择器来查找、取出数据；
	3. 可操作HTML元素、属性、文本；

注意点：
	
	某些网站会防爬虫，或者一些其他的限制，请设置jsoup_header
	例：
	Jsoup.connect(url.trim()).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");


中文版Api地址：[http://www.open-open.com/jsoup/](http://www.open-open.com/jsoup/)


例子就简单的抓取了网页的一些图片然后通过glide去加载图片，加载Url之后会获取到一个Document,然后通过遍历Document，条件筛选获取每个网页的图片url

例如豆瓣妹子的抓取规则 ：[http://www.dbmeinv.com/](http://www.dbmeinv.com/)

	
                for (Element element : document.select("div.img_single").select("a")) {
                    imageListModel = new BaseModel();
                    imageListModel.setUrl(element.select("img[class]").attr("src"));
                    imageListModel.setDetailUrl(element.select("a[class]").attr("href"));
                    list.add(imageListModel);
                }
	
这个select 方法在Document, Element,或Elements对象中都可以使用。且是上下文相关的，因此可实现指定元素的过滤，或者链式选择访问。

Select方法将返回一个Elements集合，并提供一组方法来抽取和处理结果。

###Selector选择器概述

	tagname: 通过标签查找元素，比如：a
	ns|tag: 通过标签在命名空间查找元素，比如：可以用 fb|name 语法来查找 <fb:name> 元素
	#id: 通过ID查找元素，比如：#logo
	.class: 通过class名称查找元素，比如：.masthead
	[attribute]: 利用属性查找元素，比如：[href]
	[^attr]: 利用属性名前缀来查找元素，比如：可以用[^data-] 来查找带有HTML5 Dataset属性的元素
	[attr=value]: 利用属性值来查找元素，比如：[width=500]
	[attr^=value], [attr$=value], [attr*=value]: 利用匹配属性值开头、结尾或包含属性值来查找元素，比如：[href*=/path/]
	[attr~=regex]: 利用属性值匹配正则表达式来查找元素，比如： img[src~=(?i)\.(png|jpe?g)]
	*: 这个符号将匹配所有元素

###Selector选择器组合使用

	el#id: 元素+ID，比如： div#logo
	el.class: 元素+class，比如： div.masthead
	el[attr]: 元素+class，比如： a[href]
	任意组合，比如：a[href].highlight
	ancestor child: 查找某个元素下子元素，比如：可以用.body p 查找在"body"元素下的所有 p元素
	parent > child: 查找某个父元素下的直接子元素，比如：可以用div.content > p 查找 p 元素，也可以用body > * 查找body标签下所有直接子元素
	siblingA + siblingB: 查找在A元素之前第一个同级元素B，比如：div.head + div
	siblingA ~ siblingX: 查找A元素之前的同级X元素，比如：h1 ~ p
	el, el, el:多个选择器组合，查找匹配任一选择器的唯一元素，例如：div.masthead, div.logo

###伪选择器selectors

	:lt(n): 查找哪些元素的同级索引值（它的位置在DOM树中是相对于它的父节点）小于n，比如：td:lt(3) 表示小于三列的元素
	:gt(n):查找哪些元素的同级索引值大于n，比如： div p:gt(2)表示哪些div中有包含2个以上的p元素
	:eq(n): 查找哪些元素的同级索引值与n相等，比如：form input:eq(1)表示包含一个input标签的Form元素
	:has(seletor): 查找匹配选择器包含元素的元素，比如：div:has(p)表示哪些div包含了p元素
	:not(selector): 查找与选择器不匹配的元素，比如： div:not(.logo) 表示不包含 class=logo 元素的所有 div 列表
	:contains(text): 查找包含给定文本的元素，搜索不区分大不写，比如： p:contains(jsoup)
	:containsOwn(text): 查找直接包含给定文本的元素
	:matches(regex): 查找哪些元素的文本匹配指定的正则表达式，比如：div:matches((?i)login)
	:matchesOwn(regex): 查找自身包含文本匹配指定正则表达式的元素
	注意：上述伪选择器索引是从0开始的，也就是说第一个元素索引值为0，第二个元素index为1等