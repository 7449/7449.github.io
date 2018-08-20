---
layout:     post
title:      "解析各种json"
subtitle:   "针对不同后台的json数据格式进行处理"
date:       2017-12-14
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - json
    - gson
---



## 前言

主要是针对`gson`如何解析各种各样的`json`格式

## json

> 这里以一个简单的json格式举例

    {
        "status": 0,
        "message": "message",
        "data":[]
    }
    
很简单， `[]` 就是 `JsonArray` , `{}` 就是 `JsonObject`, 以 `key`--`value`的格式存在

像上面的普通网络接口返回格式，`data` 里面就是需要的数据，如果状态码不对的话，可以返回空数据，这里的空数据指的是返回为空，而不是返回为`Null`

返回`Null`，简直就是蠢货的思维，这里是一个需要注意的要点

## 字段

这里简单说一下，java的规范是，驼峰式命名，当然接口的`key`不可能都是这样的，如果使用的是`gson`的话，可以使用`SerializedName`去解决命名问题

## data


再来说说`data`，`data`的类型是不确定的话，可以使用java的泛型去处理数据类型，个人比较建议的是，`data`统一都是`{}`,就算是`[]`,那也应该在`data`的下一层，这样封装起来比较省事一点

当然这个也是想想，如何返回还是需要后台的配合，可以返回不同的数据类型，但是如果状态不对返回`Null`，那也太蠢了


有时候`data`接受到会是如下格式：


	{
	    "1":[
	
	    ],
	    "2":[
	
	    ],
	    "3":[
	
	    ]
	}

`key`是动态的，这种格式对`js`之类的语言解析起来毫无压力，但是如果放在`java`中来解析，非常恶心人,这里有两个解决办法


 第一种就是让后台改格式，这时候需要考验沟通能力(建议)
 
 第二种就是自己解析，`Map<key,value>`去循环解析数据，其实这个还算体验比较好的
 
 
## 动态格式


如果`data`中有个字段`status`，在`A`情况下是`{}`,但是在`B`情况下是`[]`，这就非常恶心人了


这里说下使用`gson`如果解析这种动态格式的方法



#### 自定义JsonDeserializer

	public class Deserizlizer implements JsonDeserializer<String> {
	
	    @Override
	    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	
	        JsonObject asJsonObject = json.getAsJsonObject();
	
	        JsonElement status = asJsonObject.get("status");
	
	        if (status.isJsonArray()) {
	            // array
	        } else {
	            // object
	        }
	
	        return "";
	    }
	}
	
	
这里建议的是，Bean类统一写 `ArrayList`，如果是`object`，就只有一组数据而已，这样也好处理一些



## 结尾

当然，最好的结果就是再也不要碰上这种恶心人的格式了


