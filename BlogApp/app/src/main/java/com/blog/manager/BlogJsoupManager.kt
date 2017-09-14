package com.blog.manager

import com.blog.net.NetModel
import com.blog.net.TagModel
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

    /**
     * 数据会抓到 上一篇，标签，tag 的 html,懒得处理了,如果以后想处理,可以使用 document.remove() 删除脏数据
     */
    fun getDetail(document: Document): NetModel = NetModel(document.select("div.row").eq(1).html(), "", "")


    fun getTag(document: Document): List<TagModel> {
        var tempPos = 0
        val list = ArrayList<TagModel>()
        val elements = document.select("div.one-tag-list")
        for (element in elements) {
            val littleTitle = element.select("span.tag-text").text()
            list.add(TagModel("", littleTitle, "", 1, -1))
            val select = element.select("div.post-preview")
            for (contentElement in select) {
                val detailUrl = contentElement.select("a[href]").attr("abs:href")
                val title = contentElement.select("h2.post-title").text()
                val contentLittleTitle = contentElement.select("h3.post-subtitle").text()
                list.add(TagModel(title, contentLittleTitle, detailUrl, -11, tempPos))
                tempPos++
            }
        }
        return list
    }
}