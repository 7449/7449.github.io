package com.blog

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.MenuItem
import android.view.View
import com.blog.manager.BlogJsoupManager
import com.blog.net.Net
import com.blog.net.NetView
import com.blog.net.TagModel
import com.blog.widget.LoadMoreRecyclerView
import com.xadapter.OnItemClickListener
import com.xadapter.adapter.multi.MultiAdapter
import com.xadapter.adapter.multi.MultiCallBack
import com.xadapter.adapter.multi.XMultiAdapterListener
import com.xadapter.holder.XViewHolder
import io.reactivex.jsoup.network.manager.RxJsoupNetWork
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener
import org.jsoup.nodes.Document
import java.util.*


class TagActivity : AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener,
        NetView<List<TagModel>>,
        XMultiAdapterListener<TagModel>,
        OnItemClickListener<TagModel> {


    private var recyclerView: LoadMoreRecyclerView? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private var mAdapter: MultiAdapter<TagModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)
        title = "Tag"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.recyclerView) as LoadMoreRecyclerView
        refreshLayout = findViewById(R.id.refresh_layout) as SwipeRefreshLayout
        refreshLayout!!.isEnabled = false
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        val list = ArrayList<TagModel>()
        mAdapter = MultiAdapter(list)
        recyclerView!!.adapter = mAdapter!!
                .setXMultiAdapterListener(this)
                .setOnItemClickListener(this)
        refreshLayout!!.setOnRefreshListener(this)
        refreshLayout!!.post { this.onRefresh() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        startNetWorkRequest(Net.BASE_URL + "tags/")
    }

    override fun showProgress() {
        refreshLayout!!.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout!!.isRefreshing = false
    }

    override fun netWorkSuccess(model: List<TagModel>) {
        mAdapter!!.addAll(model)
    }

    override fun netWorkError(error: Throwable) {
        Snackbar.make(refreshLayout!!, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    override fun multiLayoutId(viewItemType: Int): Int = when (viewItemType) {
        1 -> R.layout.item_tag_line
        else -> R.layout.item_tag_item
    }

    override fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int = 0

    override fun onXMultiBind(holder: XViewHolder, t: TagModel, itemViewType: Int, position: Int) {
        when (itemViewType) {
            MultiCallBack.TYPE_ITEM -> {
                holder.setTextView(R.id.item_tag_title_item, t.title)
                holder.setTextView(R.id.item_tag_little_title, t.littleTitle)
            }
            1 -> {
                holder.setTextView(R.id.item_tag_title, t.littleTitle)
            }
        }
    }

    override fun onItemClick(view: View, position: Int, info: TagModel) {
        val intent = Intent(view.context, DetailActivity().javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("title", info.title)
        intent.putExtra("detailUrl", info.detailUrl)
        startActivity(intent)
    }

    override fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean = itemViewType != MultiCallBack.TYPE_ITEM

    private fun startNetWorkRequest(url: String) {
        RxJsoupNetWork.getInstance().getApi(javaClass.simpleName, url, object : RxJsoupNetWorkListener<List<TagModel>> {
            override fun onNetWorkSuccess(t: List<TagModel>) = netWorkSuccess(t)
            override fun onNetWorkStart() = showProgress()
            override fun onNetWorkError(e: Throwable) {
                hideProgress()
                netWorkError(e)
            }

            override fun onNetWorkComplete() = hideProgress()
            override fun getT(document: Document): List<TagModel> = BlogJsoupManager().getTag(document)
        })
    }

    override fun onDestroy() {
        RxJsoupNetWork.getInstance().cancel(javaClass.simpleName)
        super.onDestroy()
    }
}
