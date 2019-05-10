---
layout:     post
title:      "浅谈retrofit2封装"
subtitle:   "简单介绍下封装retrofit2"
date:       2017-9-19
tags:
    - retrofit2
---

## Bean

符合 `REST` 的 `json` 格式，如下图所示

    {
        "status": 0,
        "message": "message",
        "data":[]
    }
    
    {
        "status": 0,
        "message": "message",
        "data":{}
    }
    
根据`status`返回的状态码得知接口错误或者成功，`message`如果请求成功为空，错误的话返回错误信息。而`data`，网络请求成功则会返回数据，如果失败返回空

如果每个`Bean`都这样去写

	public class Bean {
	    public int status;
	    public String message;
	    public Data data;
	}
	
很烦吧，虽然有`GsonFormat`这类的`json`生成插件，但是如果是一个有节操的程序员，你看到每个`Bean`都这样有重复代码，心里肯定会不舒服，就会想着如何封装
，这个时候就轮到了泛型出场了

	public class BaseBean<T> {
	    public int status;
	    public String message;
	    public T data;
	}
	
这个时候只需要去继承这个`BaseBean`，让基类去处理通用的`json`数据

    public class DataBean extends BaseBean<DataBean>{
        
    }
    
其实如果用`retrofit2`是可以省略掉继承这一步，稍后会做出说明

## service

    @GET("url")
    Call<BaseBean<DataBean>> call();
    
这样去写即可不用去继承`BaseBean`，而`retrofit2 `会内部处理数据，自行转换
，而`Callback`则可以封装统一处理一下



	public class SimpleCall<T> implements Callback<T> {
	
	
	    @Override
	    public void onResponse(Call<T> call, Response<T> response) {
	        T body = response.body();
	
	        if (body instanceof BaseBean) {
	            int status = ((BaseBean) body).getStatus();
	            String message = ((BaseBean) body).getMessage();
	        }
	    }
	
	    @Override
	    public void onFailure(Call<T> call, Throwable t) {
	    }
	}

其实也可以这样写  `T extends BaseBean<T>` ,但是这样写的话每个Bean类都要继承`BaseBean`

但是如果只想得到返回的`json`字符串呢？虽然这样很方便，但是需求是千变万化的


    @GET("url")
    Call<ResponseBody> call();
    
可以直接使用`ResponseBody `，在回调里面`body.string()`返回的就是我们需要的`json`字符串

但是如果使用`ResponseBody`,`json`就需要自行解析了，这里用`gson`作为示例，


在`success`的回调中`new Gson().fromJson(json,class);`


例如这样

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                		new Gson().fromJson(response.body().string(),class)
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


难道就这样每个回调都`fromJson`? 这样也太low了吧，就这样吧 来用泛型封装一下


	public abstract class SimpleCallback<E> implements Callback<ResponseBody> {
	
	    private Class<E> aClass;
	
	    public SimpleCallback(Class<E> aClass) {
	        this.aClass = aClass;
	    }
	
	    @Override
	    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
	        String string = response.body().string();
	        E e = new Gson().fromJson(string, aClass);
	        onSuccess(e);
	    }
	
	    @Override
	    public void onFailure(Call<ResponseBody> call, Throwable t) {
	
	    }
	
	    protected abstract void onSuccess(E e);
	}

至于为什么要在构造中传入`class`，是因为java的泛型擦除问题，使用`new Gson().fromJson(string, new TypeToken<E>(){}.getType());`并不能解决问题，得到的是`com.google.gson.internal.LinkedTreeMap`,而不是想要的`class`对象，会出现类型转换异常


## Rxjava


如果使用`RxJava`，`retrofit2 `使用起来也会很简单，

    @POST("url")
    Observable<BaseBean<SimpleBean>> testUrl();


    observable
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith();
              
这样就会完成一个网络请求

如果使用中统一处理回调，使用泛型，例如这样

    protected void startNetWorkRequestBody(@NonNull Observable<BaseBean<M>> observable, String tag) {
        RxNetWork.getInstance().getApi(tag, observable
                .lift(new NetBodyOperatorImpl<M, BaseBean<M>>()), this);
    }
    
可以使用操作符处理，例如`lift`，两个泛型参数，第一个是返回的数据。第二个是传入的数据，这里推荐使用`lift`操作符，而不是`map`操作符，因为阅读源码会发现`Function`返回的数据不能为`null`，但是就有些接口，如果状态码不对的话，`data`就是`null`，所以使用`lift`在内部操作会更为方便一些

也可以在使用`map`中如果状态码正确，`data`为`null`的情况下抛出异常，通过在异常回调中区分，但是不建议这样做，至于原因嘛，强迫症，错误就是错误，成功就是成功，干自己该干的事，不越界

##  后记

如果使用 RxJava ，作者在之前简单封装了一个网络请求库，很轻量，只是简单的封装了一下

[RxNetWork](https://github.com/7449/RxNetWork)


具体使用 ：


	  Disposable api = RxNetWork
	          .getInstance()
	          .getApi(tag,RxNetWork.observable(),
			          new RxNetWorkListener<List<ListModel>>() {
			              @Override
			              public void onNetWorkStart() {
			
			              }
			
			              @Override
			              public void onNetWorkError(Throwable e) {
			
			              }
			
			              @Override
			              public void onNetWorkComplete() {
			
			              }
			
			              @Override
			              public void onNetWorkSuccess(List<ListModel> data) {
			
			              }
	            
	          });


由于过于简单，作者在使用过程中也会根据项目的需求自行封装一层，例如统一处理网络回调，具体可见
[codeKK-Android](https://github.com/7449/codeKK-Android)

至于如何获取`json`字符串，这里只是介绍了`ResponseBody `,但是还有其他方法获取，例如自定义`converter`或者`interceptor`
都可以实现拦截并获取字符串

作者的需求是，`java`层调用接口获取数据给`web`层，由于`web`层之前就已经写好了网络请求，所以作者直接返回字符串，但是要在`java`层中进行缓存，下载，之类的需求，因此作者也要处理`Bean`类

刚开始通过`Bean`类传数据给`web`，由于接口过多且有的比较复杂，而且`Bean`只要少字段，`web`得到的数据就不准确

所以由于时间问题，不能通过这个方法去传数据，这个时候就直接使用`ResponseBody `,直接拦截`json`字符串给`web`，但是作者需要处理`Bean`类，因为封装问题，不幸的遇到了泛型擦除问题，因此这个解决办法也否定掉

最后通过自定义`converter `解决掉了问题

以`GsonConverter`为例


只需要修改一处地方，为`BaseBean`新建了一个字段`body`，在返回数据之前，获取`json`字符串，赋值给`Body`，在需要发送数据的地方直接 `bean.getBody()` 即可

这只是一个暂时的解决办法，等以后`Bean`类完善，到时候即可切换到第一种办法

	final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
	    private final TypeAdapter<T> adapter;
	
	    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
	        this.adapter = adapter;
	    }
	
	    @Override
	    public T convert(ResponseBody value) throws IOException {
	        try {
	            T t = adapter.fromJson(value.string());
	            if (t instanceof BaseBean) {
	                ((BaseBean) t).setBody(value.string());
	            }
	            return t;
	        } finally {
	            value.close();
	        }
	    }
	}

