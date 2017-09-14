package com.blog.net

/**
 *  by y on 2017/9/14.
 */
interface NetView<in T> {

    fun showProgress()

    fun hideProgress()

    fun netWorkSuccess(model: T)

    fun netWorkError(error: Throwable)
}