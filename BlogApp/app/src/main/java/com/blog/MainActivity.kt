package com.blog

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.blog.manager.BlogJsoupManager
import com.blog.net.Net
import com.blog.net.NetModel
import com.blog.net.NetView
import com.blog.widget.LoadMoreRecyclerView
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnItemClickListener
import com.xadapter.listener.OnXBindListener
import io.reactivex.jsoup.network.manager.RxJsoupNetWork
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener
import org.jsoup.nodes.Document


class MainActivity : AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener,
        NetView<List<NetModel>>,
        OnItemClickListener<NetModel>,
        OnXBindListener<NetModel> {


    private lateinit var recyclerView: LoadMoreRecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private var page: Int = 1

    private var mAdapter: XRecyclerViewAdapter<NetModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "主页"
        recyclerView = findViewById(R.id.recyclerView)
        refreshLayout = findViewById(R.id.refresh_layout)

        mAdapter = XRecyclerViewAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setLoadingMore(object : LoadMoreRecyclerView.LoadMoreListener {
            override fun onLoadMore() {
                if (refreshLayout.isRefreshing) return
                startNetWorkRequest(Net.BASE_URL + "/page" + page)
            }
        })
        recyclerView.adapter =
                mAdapter!!
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item_list_blog)
                        .onXBind(this)
                        .setOnItemClickListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { this.onRefresh() }
    }

    override fun onRefresh() {
        page = 1
        startNetWorkRequest(Net.BASE_URL)
    }


    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(model: List<NetModel>) {
        if (page == 1) {
            mAdapter!!.removeAll()
        }
        mAdapter!!.addAllData(model)
        page++
    }

    override fun netWorkError(error: Throwable) {
        Snackbar.make(refreshLayout, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }


    override fun onXBind(holder: XViewHolder, position: Int, entity: NetModel) {
        holder.setTextView(R.id.blog_list_title, entity.title)
        holder.setTextView(R.id.blog_list_little_title, entity.littleTitle)
    }

    override fun onItemClick(view: View, position: Int, entity: NetModel) {
        val intent = Intent(view.context, DetailActivity().javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("title", entity.title)
        intent.putExtra("detailUrl", entity.detailUrl)
        startActivity(intent)
    }

    private fun startNetWorkRequest(url: String) {
        RxJsoupNetWork.getInstance().cancel(javaClass.simpleName)
        RxJsoupNetWork.getInstance().getApi(javaClass.simpleName, url, object : RxJsoupNetWorkListener<List<NetModel>> {
            override fun onNetWorkSuccess(t: List<NetModel>) = netWorkSuccess(t)
            override fun onNetWorkStart() = showProgress()
            override fun onNetWorkError(e: Throwable) {
                hideProgress()
                netWorkError(e)
            }

            override fun onNetWorkComplete() = hideProgress()
            override fun getT(document: Document): List<NetModel> = BlogJsoupManager().getList(document)
        })
    }


    override fun onDestroy() {
        RxJsoupNetWork.getInstance().cancelAll()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.tag) {
            val intent = Intent(applicationContext, TagActivity().javaClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
