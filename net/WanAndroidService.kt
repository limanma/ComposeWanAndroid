package com.example.composewanandroid.net

import com.example.composewanandroid.model.ArticleResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WanAndroidService {
    @GET("article/list/{index}/json")
    suspend fun articleResp(@Path("index") index: Int): ArticleResp
}