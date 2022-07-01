package com.example.composewanandroid.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitManager {
    fun api() = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WanAndroidService::class.java)
}