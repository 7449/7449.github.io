---
layout:     post
title:      "Android_RxBus"
subtitle:   "基于RxJava的RxBus"
date:       2016-10-5
author:     "y"
header-mask: 0.3
header-img: "img/rxjava.jpg"
catalog: true
tags:
    - android
    - Rx系列
---


## 17.5.8 UpData

[RxBus.java](https://github.com/7449/RxNetWork/blob/master/RxNetWorkLibrary/src/main/java/io/reactivex/network/bus/RxBus.java)

	public class RxBus {
	
	    private final ArrayMap<Object, Subject<Object, Object>> rxMap;
	    private CompositeSubscription compositeSubscription;
	
	    private static final String TAG = "RxBus";
	
	    private RxBus() {
	        rxMap = new ArrayMap<>();
	        compositeSubscription = new CompositeSubscription();
	    }
	
	    public static RxBus getInstance() {
	        return RxBusHolder.rxBus;
	    }
	
	    private static class RxBusHolder {
	        private static final RxBus rxBus = new RxBus();
	    }
	
	    /**
	     * 发送一个带tag,但不携带信息的消息
	     *
	     * @param tag 标志
	     */
	    public void send(@NonNull Object tag) {
	        send(tag, tag);
	    }
	
	    /**
	     * 发送一个带tag,携带信息的消息
	     *
	     * @param tag     标志
	     * @param message 内容
	     */
	    public void send(@NonNull Object tag,
	                     @NonNull Object message) {
	        if (RxUtils.isEmpty(rxMap, tag)) {
	            LogI.i(TAG, "tag:" + tag + "  message:" + message);
	            rxMap.get(tag).onNext(message);
	        }
	    }
	
	    /**
	     * 接受消息
	     *
	     * @param tag      标志
	     * @param service  类型
	     * @param callBack 回调
	     */
	    public <T> Subscription toSubscription(@NonNull final Object tag,
	                                           @NonNull Class<T> service,
	                                           @NonNull final RxBusCallBack<T> callBack) {
	        Subject<Object, Object> subject = rxMap.get(tag);
	        if (RxUtils.isEmpty(subject)) {
	            subject = new SerializedSubject<>(PublishSubject.create());
	            rxMap.put(tag, subject);
	        }
	        Subscription subscribe = subject
	                .ofType(service)
	                .subscribeOn(Schedulers.io())
	                .observeOn(AndroidSchedulers.mainThread())
	                .subscribe(
	                        new RxBusSubscriber<T>() {
	                            @Override
	                            public void onError(Throwable e) {
	                                super.onError(e);
	                                callBack.onError(e);
	                            }
	
	                            @Override
	                            public void onNext(T t) {
	                                super.onNext(t);
	                                callBack.onNext(t);
	                            }
	                        });
	        compositeSubscription.add(subscribe);
	        return subscribe;
	
	    }
	
	
	    /**
	     * 取消订阅
	     *
	     * @param tag          标志
	     * @param subscription 当前tag关联的subscription
	     */
	    public void unregister(@NonNull Object tag, @NonNull Subscription subscription) {
	        compositeSubscription.remove(subscription);
	        if (RxUtils.isEmpty(rxMap, tag)) {
	            rxMap.remove(tag);
	        }
	    }
	
	    /**
	     * 取消所有订阅
	     */
	    public void unregisterAll() {
	        compositeSubscription.clear();
	        rxMap.clear();
	    }
	
	    public CompositeSubscription getCompositeSubscription() {
	        return compositeSubscription;
	    }
	
	    private static class RxBusSubscriber<T> extends Subscriber<T> {
	
	        @Override
	        public void onCompleted() {
	
	        }
	
	        @Override
	        public void onError(Throwable e) {
	
	        }
	
	        @Override
	        public void onNext(T t) {
	
	        }
	    }
	}
	
	
	
	public interface RxBusCallBack<T> {
	    void onNext(T data);
	
	    void onError(Throwable throwable);
	}


## RxBus 无Tag，发送一个消息，全局响应

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

## RxBus 有Tag,发送一个消息，只有指定的才能响应

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