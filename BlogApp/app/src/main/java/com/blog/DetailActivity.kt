package com.blog

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebView
import com.blog.manager.BlogJsoupManager
import com.blog.net.NetModel
import com.blog.net.NetView
import com.blog.widget.Html
import io.reactivex.jsoup.network.manager.RxJsoupNetWork
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener
import org.jsoup.nodes.Document

class DetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, NetView<NetModel> {


    private lateinit var webView: WebView
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        webView = findViewById(R.id.webView) as WebView
        refreshLayout = findViewById(R.id.refresh_layout) as SwipeRefreshLayout
        refreshLayout.isEnabled = false
        title = intent.getStringExtra("title")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        refreshLayout.post { this.onRefresh() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        startNetWorkRequest(intent.getStringExtra("detailUrl"))
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(model: NetModel) {
        webView.loadDataWithBaseURL(null, Html.getHtml(model.title), Html.mimeType, Html.coding, null)
    }

    override fun netWorkError(error: Throwable) {
        Snackbar.make(findViewById(R.id.rootView)!!, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun startNetWorkRequest(url: String) {
        RxJsoupNetWork.getInstance().cancel(javaClass.simpleName)
        RxJsoupNetWork.getInstance().getApi(javaClass.simpleName, url, object : RxJsoupNetWorkListener<NetModel> {
            override fun onNetWorkSuccess(t: NetModel) = netWorkSuccess(t)
            override fun onNetWorkStart() = showProgress()
            override fun onNetWorkError(e: Throwable) {
                hideProgress()
                netWorkError(e)
            }

            override fun onNetWorkComplete() = hideProgress()
            override fun getT(document: Document): NetModel = BlogJsoupManager().getDetail(document)
        })
    }

    override fun onDestroy() {
        webView.destroy()
        RxJsoupNetWork.getInstance().cancel(javaClass.simpleName)
        super.onDestroy()
    }
}
