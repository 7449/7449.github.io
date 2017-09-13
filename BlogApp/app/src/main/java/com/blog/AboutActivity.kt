package com.blog

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

class AboutActivity : AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener {

    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        refreshLayout = findViewById(R.id.refresh_layout) as SwipeRefreshLayout
        recyclerView!!.setHasFixedSize(true)
        refreshLayout!!.setOnRefreshListener(this)
    }

    override fun onRefresh() {
    }

}
