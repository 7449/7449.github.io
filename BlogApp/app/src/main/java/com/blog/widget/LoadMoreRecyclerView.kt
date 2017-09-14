package com.blog.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

/**
 * by y on 2017/9/14.
 */

class LoadMoreRecyclerView : RecyclerView {
    private var layoutManagerType: LAYOUT_MANAGER_TYPE? = null
    private var lastPositions: IntArray? = null
    private var lastVisibleItemPosition: Int = 0
    private var loadingData: LoadMoreListener? = null

    constructor(context: Context) : super(context)


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun setLoadingMore(loadingData: LoadMoreListener) {
        this.loadingData = loadingData
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        val layoutManager = layoutManager
        if (layoutManagerType == null) {
            layoutManagerType = when (layoutManager) {
                is GridLayoutManager -> LAYOUT_MANAGER_TYPE.GRID
                is LinearLayoutManager -> LAYOUT_MANAGER_TYPE.LINEAR
                is StaggeredGridLayoutManager -> LAYOUT_MANAGER_TYPE.STAGGERED_GRID
                else -> throw RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }

        when (layoutManagerType) {
            LoadMoreRecyclerView.LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            LoadMoreRecyclerView.LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            LoadMoreRecyclerView.LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }

    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        val layoutManager = layoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        if (visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && loadingData != null) {
            loadingData!!.onLoadMore()
        }
    }

    private fun findMax(lastPositions: IntArray): Int = lastPositions.max() ?: lastPositions[0]

    private enum class LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }

}