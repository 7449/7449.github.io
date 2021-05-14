---
layout:     post
title:      "response网络封装结构体"
subtitle:   "response"
date:       2021-5-14
tags:
    - response
    - network
---

## DataResult
    
    data class DataResult<T>(
        val code: Int,
        val message: String?,
        val data: T?
    ) {
        companion object {
            inline fun <reified T> mutableLiveData(): MutableLiveData<DataWrapper<DataResult<T>>> {
                return MutableLiveData<DataWrapper<DataResult<T>>>()
            }
        }
    }
    
## DataWrapper
    
    sealed class DataWrapper<out R> {
    
        /**
         * 初始状态
         */
        data class Normal(val value: String = "") : DataWrapper<Nothing>()
    
        /**
         * 数据请求成功
         */
        data class Success<out T>(val data: T) : DataWrapper<T>()
    
        /**
         * 数据请求出错
         */
        data class Error(val exception: Exception) : DataWrapper<Nothing>() {
            // TODO
        }
    
        /**
         * 正在加载
         */
        sealed class Loading(val type: Int) : DataWrapper<Nothing>() {
    
            companion object {
                const val NOTHING = -1 //无动作
                const val NORMAL = 0 //普通加载
                const val REFRESH = 1 //下拉加载
                const val MORE = 2 //上拉加载
            }
    
            data class Nothing(val message: String = "") : Loading(NOTHING)
            data class Normal(val message: String = "") : Loading(NORMAL)
            data class Refresh(val message: String = "") : Loading(REFRESH)
            data class More(val message: String = "") : Loading(MORE)
        }
    
        /**
         * 数据为空
         */
        data class Empty(val value: String = "") : DataWrapper<Nothing>()
    
        override fun toString(): String {
            return when (this) {
                is Success<*> -> "Success[data=$data]"
                is Error -> "Error[message=${exception.message}]"
                is Empty -> "Empty[$value]"
                is Normal -> "Normal[$value]"
                is Loading.More -> "LoadingMore[$type]"
                is Loading.Normal -> "LoadingNormal[$type]"
                is Loading.Nothing -> "LoadingNothing[$type]"
                is Loading.Refresh -> "LoadingRefresh[$type]"
            }
        }
    
    }
    
## 扩展函数

    
    inline fun <reified T> MutableLiveData<DataWrapper<T>>.onChangedValue(dataWrapper: DataWrapper<T>) {
        value = dataWrapper
    }
    
    inline fun <reified T> MutableLiveData<DataWrapper<T>>.onPostChangedValue(dataWrapper: DataWrapper<T>) {
        postValue(dataWrapper)
    }
    
    fun <R> DataWrapper<R>.doActionDsl(dataWrapper: DataWrapperDsl<R>.() -> Unit) =
        DataWrapperDsl<R>().also(dataWrapper).build(this)
    
    inline fun <reified R> DataWrapper<R>.doNormalAction(noinline action: () -> Unit) =
        doAction(normal = action)
    
    inline fun <reified R> DataWrapper<R>.doEmptyAction(noinline action: () -> Unit) =
        doAction(empty = action)
    
    inline fun <reified R> DataWrapper<R>.doLoadingMoreAction(noinline action: () -> Unit) =
        doAction(loadingMore = action)
    
    inline fun <reified R> DataWrapper<R>.doLoadingNormalAction(noinline action: () -> Unit) =
        doAction(loadingNormal = action)
    
    inline fun <reified R> DataWrapper<R>.doLoadingRefreshAction(noinline action: () -> Unit) =
        doAction(loadingRefresh = action)
    
    inline fun <reified R> DataWrapper<R>.doLoadingNothingAction(noinline action: () -> Unit) =
        doAction(loadingNothing = action)
    
    inline fun <reified R> DataWrapper<R>.doSuccessAction(noinline action: (data: R) -> Unit) =
        doAction(success = action)
    
    inline fun <reified R> DataWrapper<R>.doErrorAction(noinline action: (exception: Exception) -> Unit) =
        doAction(error = action)
    
    fun <R> DataWrapper<R>.doAction(
        normal: (() -> Unit)? = null,
        empty: (() -> Unit)? = null,
        loadingMore: (() -> Unit)? = null,
        loadingNormal: (() -> Unit)? = null,
        loadingRefresh: (() -> Unit)? = null,
        loadingNothing: (() -> Unit)? = null,
        success: ((data: R) -> Unit)? = null,
        error: ((exception: Exception) -> Unit)? = null
    ) = apply {
        when (this) {
            is DataWrapper.Empty -> empty?.invoke()
            is DataWrapper.Error -> error?.invoke(exception)
            is DataWrapper.Loading.More -> loadingMore?.invoke()
            is DataWrapper.Loading.Normal -> loadingNormal?.invoke()
            is DataWrapper.Loading.Nothing -> loadingNothing?.invoke()
            is DataWrapper.Loading.Refresh -> loadingRefresh?.invoke()
            is DataWrapper.Normal -> normal?.invoke()
            is DataWrapper.Success -> success?.invoke(data)
        }
    }

## DSL
    
    class DataWrapperDsl<R> {
    
        private var normal: (() -> Unit)? = null
        private var empty: (() -> Unit)? = null
        private var loadingMore: (() -> Unit)? = null
        private var loadingNormal: (() -> Unit)? = null
        private var loadingRefresh: (() -> Unit)? = null
        private var loadingNothing: (() -> Unit)? = null
        private var success: ((R) -> Unit)? = null
        private var error: ((Exception) -> Unit)? = null
    
        fun normal(action: () -> Unit) {
            this.normal = action
        }
    
        fun empty(action: () -> Unit) {
            this.empty = action
        }
    
        fun loadingMore(action: () -> Unit) {
            this.loadingMore = action
        }
    
        fun loadingNormal(action: () -> Unit) {
            this.loadingNormal = action
        }
    
        fun loadingRefresh(action: () -> Unit) {
            this.loadingRefresh = action
        }
    
        fun loadingNothing(action: () -> Unit) {
            this.loadingNothing = action
        }
    
        fun success(action: (data: R) -> Unit) {
            this.success = action
        }
    
        fun error(action: (exception: Exception) -> Unit) {
            this.error = action
        }
    
        internal fun build(dataWrapper: DataWrapper<R>): DataWrapper<R> {
            return dataWrapper.doAction(
                normal = normal,
                empty = empty,
                loadingMore = loadingMore,
                loadingNormal = loadingNormal,
                loadingRefresh = loadingRefresh,
                loadingNothing = loadingNothing,
                success = success,
                error = error
            )
        }
    
    }

