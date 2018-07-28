package com.blog.net

import com.xadapter.adapter.multi.MultiCallBack

/**
 *  by y on 2017/9/14.
 */
class TagModel(var title: String, var littleTitle: String, var detailUrl: String, private var type: Int, private var pos: Int = -1) : MultiCallBack {
    override val itemType: Int
        get() = type
    override val position: Int
        get() = pos
}