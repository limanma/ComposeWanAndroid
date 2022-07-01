package com.example.composewanandroid.model

data class ArticleResp(
    val `data`: ArticleData,
    val errorCode: Int,
    val errorMsg: String
)

data class ArticleData(
    val curPage: Int,
    val datas: MutableList<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Article(
    val author: String,
    val desc: String,
    val id: Int,
    val link: String,
    val niceShareDate: String,
    val shareUser: String,
    val title: String,
)

data class Tag(
    val name: String,
    val url: String
)