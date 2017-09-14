package com.blog.manager

import com.blog.net.NetModel
import org.jsoup.nodes.Document
import java.util.*


/**
 *  by y on 2017/9/14.
 */
class BlogJsoupManager {

    fun getList(document: Document): List<NetModel> {
        val list = ArrayList<NetModel>()
        val elements = document.select("div.post-preview")
        for (element in elements) {
            val title = element.select("h2.post-title").text()
            val detailUrl = element.select("a[href]").attr("abs:href")
            val littleTitle = element.select("h3.post-subtitle").text()
            list.add(NetModel(title, littleTitle, detailUrl))
        }
        return list
    }

    fun getDetail(document: Document): NetModel {

        val model = NetModel("", "", "")

        return model
    }

}