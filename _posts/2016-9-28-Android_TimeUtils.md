---
layout: post
title: Android_几个时间操作的方法
---
获取本地时间  

	public static String getCurrentDate() {  
	    Date d = new Date();  
	    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    return format.format(d);  
	}  
	  
时间戳转换成字符串  

	public static String getDateToString(long time) {  
	    Date d = new Date(time);  
	    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    return format.format(d);  
	}  
  
字符串转换成时间戳  

	public static long getStringToDate(String time) {  
	    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date date = new Date();  
	    try {  
	        date = format.parse(time);  
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }  
	    return date.getTime();  
	}  