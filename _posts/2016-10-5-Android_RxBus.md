---
layout: post
title: Android_RxBus
---

刚开始使用的时候只是简单的使用了一下RxBus,发现一个问题就是没有添加Tag 当发送一个消息的时候整个项目全局响应，这肯定不是我想要的结果，最后在RxBus里面加一个Tag,让其只响应应该响应的地方，而其他的不会响应。


RxBus 无Tag，发送一个消息，全局响应

	public class RxBus {    
	    
	    private final Subject rxBus;    
	    
	    private RxBus() {    
	        rxBus = new SerializedSubject<>(PublishSubject.create());    
	    }    
	    
	    public static RxBus getInstance() {    
	        return RxbusHolder.rxBus;    
	    }    
	    
	    private static class RxbusHolder {    
	        private static final RxBus rxBus = new RxBus();    
	    }    
	    
	    public void sendNetWork(Object object) {   
	        rxBus.onNext(object);    
	    }    
	    
	    public <T> Observable<T> toObserverable(final Class<T> eventType) {     
	        return rxBus.ofType(eventType);    
	    }    
	}    

RxBus 有Tag,发送一个消息，只有指定的才能响应

	public class RxBus {  
	    private HashMap<Object, List<Subject>> rxMap;  
	  
	    private RxBus() {  
	        rxMap = new HashMap<>();  
	    }  
	  
	    public static RxBus getInstance() {  
	        return RxbusHolder.rxBus;  
	    }  
	  
	    private static class RxbusHolder {  
	        private static final RxBus rxBus = new RxBus();  
	    }  
	  
	    public void send(@NonNull Object tag) {  
	        send(tag, "");  
	    }  
	  
	    public void send(@NonNull Object tag, @NonNull Object object) {  
	        List<Subject> subjects = rxMap.get(tag);  
	        KLog.i(subjects);  
	        if (null != subjects && !subjects.isEmpty()) {  
	            for (Subject s : subjects) {  
	                s.onNext(object);  
	            }  
	        }  
	    }  
	  
	    public void unregister(@NonNull Object tag, @NonNull Observable observable) {  
	        List<Subject> subjects = rxMap.get(tag);  
	        if (subjects != null) {  
	            subjects.remove(observable);  
	            if (subjects.isEmpty()) {  
	                rxMap.remove(tag);  
	            }  
	        }  
	    }  
	  
	    public <T> Observable<T> toObserverable(@NonNull Object tag) {  
	        List<Subject> rxList = rxMap.get(tag);  
	        if (rxList == null) {  
	            rxList = new ArrayList<>();  
	            rxMap.put(tag, rxList);  
	        }  
	        Subject<T, T> subject = PublishSubject.create();  
	        rxList.add(subject);  
	        return subject;  
	    }  
	} 